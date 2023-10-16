package com.example.crewsync.domains.services;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.crewsync.common.utils.WritableFile;
import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.domains.mappers.NumberingMapper;
import com.example.crewsync.domains.mappers.OrganizationMapper;
import com.example.crewsync.domains.mappers.UserRegisterMapper;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.security.LoginUser;

/**
 * ユーザー登録処理のビジネスロジックを実行します
 */
@Service
public class UserRegisterService {

    private final OrganizationMapper organizationMapper;

    private final NumberingMapper numberingMapper;

    private final UserRegisterMapper userRegisterMapper;

    private final LoginUserMapper loginUserMapper;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegisterService(OrganizationMapper organizationMapper, NumberingMapper numberingMapper,
            UserRegisterMapper userRegisterMapper,
            LoginUserMapper loginUserMapper, PasswordEncoder passwordEncoder) {
        this.organizationMapper = organizationMapper;
        this.numberingMapper = numberingMapper;
        this.userRegisterMapper = userRegisterMapper;
        this.loginUserMapper = loginUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ユーザー登録フォームを生成します
     *
     * @param email メールアドレス
     * @return ユーザー登録フォーム
     * @throws Exception
     */
    public UserRegisterForm initUserRegisterForm(String email) throws Exception {
        UserRegisterForm userRegisterForm = new UserRegisterForm(
                loginUserMapper.identifyUser(email).orElse(new LoginUser()));

        userRegisterForm.setDeptOptions(organizationMapper.getDepartmentCd());
        userRegisterForm.setPosOptions(organizationMapper.getPositionCd());
        userRegisterForm.setRoleOptions(organizationMapper.getCode(1));
        return userRegisterForm;
    }

    /**
     * ユーザー登録フォームを生成します
     *
     * @param id ユーザーID
     * @return ユーザー登録フォーム
     * @throws Exception
     */
    public UserRegisterForm initUserRegisterForm(Long id) throws Exception {
        UserRegisterForm userRegisterForm = new UserRegisterForm(
                loginUserMapper.findUserById(id).orElse(new LoginUser()));

        userRegisterForm.setDeptOptions(organizationMapper.getDepartmentCd());
        userRegisterForm.setPosOptions(organizationMapper.getPositionCd());
        userRegisterForm.setRoleOptions(organizationMapper.getCode(1));
        return userRegisterForm;
    }

    /**
     * ユーザー登録フォームを再生成します
     *
     * @param userRegisterForm ユーザー登録フォーム
     */
    public void restoreRegisterForm(UserRegisterForm userRegisterForm) {
        userRegisterForm.setDeptOptions(organizationMapper.getDepartmentCd());
        userRegisterForm.setPosOptions(organizationMapper.getPositionCd());
        userRegisterForm.setRoleOptions(organizationMapper.getCode(1));
    }

    /**
     * ユーザー登録処理を実行します
     *
     * @param userRegisterForm ユーザー登録フォーム
     * @param loginUser        ユーザー情報
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
    public void registerUser(UserRegisterForm userRegisterForm, LoginUser loginUser) throws Exception {
        // 社員番号の発番
        if (userRegisterForm.getEmpNo() == null) {
            String availYear = new SimpleDateFormat("yyyy").format(new Date());
            String nextNo = numberingMapper.issueNumber(NumberingMapper.NUMBERING_CODE_EMPNO, availYear);
            userRegisterForm.setEmpNo(availYear + nextNo);
            numberingMapper.next(NumberingMapper.NUMBERING_CODE_EMPNO, availYear, loginUser);
        }

        // プロフィール画像の保存
        MultipartFile uploadFile = userRegisterForm.getUploadFile();
        String filePath = ProfileEditService.PROFILE_IMG_DEST + userRegisterForm.getEmpNo() + File.separator;
        if (!uploadFile.isEmpty()) {
            WritableFile writableFile = new WritableFile(uploadFile);
            writableFile.deleteAndWrite(filePath);
            // フォームオブジェクトが持つ画像情報を更新
            ImageFile imageFile = new ImageFile();
            imageFile.setFileName(filePath + uploadFile.getOriginalFilename());
            userRegisterForm.setImageFile(imageFile);
        } else {
            userRegisterForm.setImageFile(userRegisterForm.getUser().getImageFile());
        }

        // ユーザー情報テーブルの更新
        if (userRegisterForm.getEmail() == null) {
            userRegisterForm.setEmail(userRegisterForm.getUser().getEmail());
            userRegisterForm.setPassword(userRegisterForm.getUser().getPassword());
        } else {
            userRegisterForm.setPassword(passwordEncoder.encode(userRegisterForm.getPassword()));
        }
        userRegisterMapper.registerUser(userRegisterForm);

        // ユーザー権限テーブルの更新
        userRegisterForm
                .setUser(loginUserMapper.findUserById(userRegisterForm.getUser().getId()).orElse(new LoginUser()));
        // 変更前のユーザー権限を取得
        List<String> rolesBefore = userRegisterForm.getUser().getRoles().stream()
                .map(option -> new String(option.getCode())).collect(Collectors.toList());
        // 削除対象のユーザー権限を取得
        List<String> listForDelete = rolesBefore.stream().filter(p -> !userRegisterForm.getRoles().contains(p))
                .collect(Collectors.toList());
        // 新規登録対象のユーザー権限を取得
        List<String> listForGrant = userRegisterForm.getRoles().stream().filter(p -> !rolesBefore.contains(p))
                .collect(Collectors.toList());
        // 権限はく奪を実行
        listForDelete.forEach(role -> {
            userRegisterMapper.depriveAuthority(userRegisterForm.getUser(), role);
        });
        // 権限付与を実行
        listForGrant.forEach(role -> {
            userRegisterMapper.grantAuthority(userRegisterForm.getUser(), role);
        });
    }
}

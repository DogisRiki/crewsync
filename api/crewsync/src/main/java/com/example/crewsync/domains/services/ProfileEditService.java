package com.example.crewsync.domains.services;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.domains.mappers.ProfileEditMapper;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.security.LoginUser;

@Service
public class ProfileEditService {

    private final PasswordEncoder passwordEncoder;

    private final ProfileEditMapper profileEditMapper;

    private LoginUser user;

    // 仮のプロフィール画像保存先
    public static final String PROFILE_IMG_DEST = "C:\\img\\00_profile\\";

    @Autowired
    public ProfileEditService(ProfileEditMapper profileEditMapper, PasswordEncoder passwordEncoder) {
        this.profileEditMapper = profileEditMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 個人情報入力フォームを初期化します
     *
     * @param user ユーザー情報
     * @return 個人情報入力フォーム
     */
    public ProfileEditForm initPersonalInfo(LoginUser user) {
        this.user = user;
        Optional<ProfileEditForm> optionalForm = profileEditMapper.createProfileEditForm(user.getId());
        return optionalForm.orElse(new ProfileEditForm());
    }

    /**
     * ユーザーのプロフィールを更新します。
     * プロフィール画像のアップロード、パスワードの更新、プロフィールおよび個人情報の更新を行います。
     * 更新処理が全て成功しなければ例外をスローします。
     *
     * @param form 個人情報フォーム
     * @throws IllegalStateException 更新件数が2でない（プロフィールと個人情報の更新が全てできなかった）場合
     * @throws IOException           プロフィール画像の書き込みに失敗した場合
     */
    @Transactional(rollbackFor = Throwable.class)
    public void editProfile(ProfileEditForm form) throws IllegalStateException, IOException {

        MultipartFile uploadImgFile = form.getUploadFile();

        if (!uploadImgFile.isEmpty()) {
            // 画像のアップロード、削除、保存を行う
            handleProfileImage(uploadImgFile);
        }

        int count = 0;
        if (form.getPassword() != null && !form.getPassword().equals("")) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
        }
        count += profileEditMapper.updateProfile(user);
        form.setUserId(user.getId());
        count += profileEditMapper.updatePersonalInfo(form);

        // プロフィールの更新と個人情報の更新が2つとも成功でなければ例外をスローする
        if (count != 2) {
            throw new IllegalStateException();
        }
    }

    /**
     * プロフィール画像のアップロードを行います。
     * 既存の画像が存在する場合は削除し、新たな画像を保存します。
     * 保存した画像のパスはユーザーオブジェクトにセットされます。
     *
     * @param uploadImgFile アップロードする画像ファイル
     * @throws IllegalStateException ディレクトリの作成や画像の削除に失敗した場合
     * @throws IOException           画像ファイルの書き込みに失敗した場合
     */
    private void handleProfileImage(MultipartFile uploadImgFile) throws IllegalStateException, IOException {

        String imgPath = PROFILE_IMG_DEST + user.getEmpNo() + File.separator;
        File directory = new File(imgPath);

        // すでに保存されている画像があればディレクトリから削除する
        if (directory.exists()) {
            for (File target : directory.listFiles()) {
                target.delete();
            }
        } else {
            // ディレクトリがなければディレクトリを作成する
            directory.mkdirs();
        }

        // プロフィール画像保存先
        File dest = new File(imgPath + uploadImgFile.getOriginalFilename());
        // プロフィール画像をディレクトリへ書き込む
        uploadImgFile.transferTo(dest);

        // ユーザーオブジェクトへ更新後のプロフィール画像をセットする
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName(dest.getAbsolutePath());
        user.setImageFile(imageFile);
    }

}

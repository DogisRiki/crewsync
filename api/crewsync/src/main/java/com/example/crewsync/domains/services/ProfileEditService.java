package com.example.crewsync.domains.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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

    // 仮のプロフィール画像保存先
    public static final String PROFILE_IMG_DEST = "/img/00_profile/";

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
        Optional<ProfileEditForm> optionalForm = profileEditMapper.createProfileEditForm(user.getId());
        return optionalForm.orElse(new ProfileEditForm());
    }

    /**
     * ユーザー情報と個人情報を更新します
     *
     * @param form 個人情報フォーム
     * @throws Exception トランザクションエラーやIOエラーなど
     */
    @Transactional(rollbackFor = Throwable.class)
    public void editProfile(LoginUser user, ProfileEditForm form) throws Exception {

        // アップロードされた画像ファイル
        MultipartFile uploadImgFile = form.getUploadFile();
        // 本番用プロフィール画像保存先
        String absolutePath = PROFILE_IMG_DEST + user.getEmpNo() + File.separator;
        // 一時用プロフィール画像保存先
        String tmpPath = absolutePath + "tmp" + File.separator;
        // ファイルオブジェクトを保持
        File dest = null;

        try {
            // 本番用ディレクトリオブジェクト
            File directory = new File(absolutePath);
            // 一時用ディレクトリオブジェクト
            File tmpDirectory = new File(tmpPath);
            // 一時用ディレクトリが存在しなければ作成する
            if (!tmpDirectory.exists()) {
                tmpDirectory.mkdirs();
            }

            // アップロードされた画像を本番用ディレクトリへ保存
            if (!uploadImgFile.isEmpty()) {
                // 本番用ディレクトリ内のファイルを一時用ディレクトリへ移動する
                if (directory.exists()) {
                    for (File target : directory.listFiles()) {
                        if (target.isFile()) {
                            File tmpDest = new File(tmpPath + target.getName());
                            Files.move(target.toPath(), tmpDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        }
                    }
                }
                dest = new File(absolutePath + uploadImgFile.getOriginalFilename());
                uploadImgFile.transferTo(dest);

                // ユーザーオブジェクトが持つ画像情報を更新
                ImageFile imageFile = new ImageFile();
                imageFile.setFileName(absolutePath + uploadImgFile.getOriginalFilename());
                user.setImageFile(imageFile);
            }

            // DB更新件数
            int updateCount = 0;

            // ユーザ―情報更新
            if (form.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(form.getPassword()));
            }
            updateCount += profileEditMapper.updateProfile(user);

            // 個人情報更新
            form.setUserId(user.getId());
            updateCount += profileEditMapper.updatePersonalInfo(form);

            // ユーザー情報更新と個人情報更新の2つが成功しなければ例外をスローする
            if (updateCount != 2) {
                throw new Exception();
            }

            // 一時ディレクトリの内容を全削除
            for (File target : tmpDirectory.listFiles()) {
                target.delete();
            }

        } catch (Exception e) {
            // 新しい画像を削除し、一時ディレクトリから本番用ディレクトリへ既存の画像を戻す
            if (dest != null) {
                dest.delete();
            }
            // 一時用ディレクトリへ退避しておいた画像ファイルを本番用ディレクトリへ戻す
            for (File target : new File(tmpPath).listFiles()) {
                File originalDest = new File(absolutePath + target.getName());
                Files.move(target.toPath(), originalDest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            // 例外を再スローしてトランザクションをロールバック
            throw e;
        }
    }

    /**
     * プロフィール画像を取得します
     *
     * @param id ユーザーID
     * @return プロフィール画像オブジェクト
     */
    public ImageFile getProfileImg(long id) {
        return profileEditMapper.getImageFileById(id);
    }

}

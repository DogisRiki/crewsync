package com.example.crewsync.domains.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.security.LoginUser;

/**
 * プロフィール編集画面のマッパーインターフェースです
 */
@Mapper
public interface ProfileEditMapper {

    /**
     * プロフィール編集フォームを生成します
     */
    public Optional<ProfileEditForm> createProfileEditForm(long id);

    /**
     * ユーザーのプロフィールを更新します
     */
    public int updateProfile(LoginUser user);

    /**
     * ユーザーの個人情報を更新します
     */
    public int updatePersonalInfo(ProfileEditForm profileEditForm);
}

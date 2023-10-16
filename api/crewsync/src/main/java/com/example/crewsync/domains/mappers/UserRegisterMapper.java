package com.example.crewsync.domains.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.security.LoginUser;

/**
 * ユーザー登録画面のマッパーインターフェースです
 */
@Mapper
public interface UserRegisterMapper {

    /**
     * ユーザー権限を削除します
     *
     * @param user     ユーザー情報
     * @param userRole ユーザー権限
     */
    public void depriveAuthority(@Param("user") LoginUser user, @Param("role") String userRole);

    /**
     * ユーザー登録処理を実行します
     *
     * @param userRegisterForm ユーザー登録フォーム
     */
    public void registerUser(UserRegisterForm userRegisterForm);

    /**
     * ユーザー権限登録処理を実行します
     *
     * @param user     ユーザー情報
     * @param userRole ユーザー権限
     * @return 登録件数
     */
    public int grantAuthority(@Param("user") LoginUser user, @Param("role") String userRole);

    /**
     * 初期ユーザー情報を登録します
     *
     * @param user ユーザー情報
     * @return 登録件数
     */
    public int registerInitialUser(LoginUser user);
}

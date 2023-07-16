package com.example.crewsync.domains.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.auth.LoginUser;

@Mapper
public interface LoginUserMapper {

    /**
     * ユーザー情報を検索します
     *
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public LoginUser identifyUser(String email);

    /**
     * ユーザー情報を登録します
     *
     * @param user ユーザー情報
     * @return 登録件数
     */
    public int registerUser(LoginUser user);

    /**
     * ユーザー権限を登録します
     *
     * @param user ユーザー情報
     * @return 登録件数
     */
    public int registerUserRole(LoginUser user);

}

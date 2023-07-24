package com.example.crewsync.domains.mappers;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.security.LoginUser;

@Mapper
public interface LoginUserMapper {

    /**
     * ユーザー情報を検索します
     *
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public LoginUser identifyUser(String email);
}

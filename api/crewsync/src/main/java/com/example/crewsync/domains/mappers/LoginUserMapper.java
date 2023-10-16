package com.example.crewsync.domains.mappers;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.security.LoginUser;

/**
 * ログイン画面のマッパーインターフェースです
 */
@Mapper
public interface LoginUserMapper {

    /**
     * メールアドレスからユーザー情報を検索します
     *
     * @param email メールアドレス
     * @return ユーザー情報
     */
    public Optional<LoginUser> identifyUser(String email);

    /**
     * ユーザーIDからユーザーを検索します
     *
     * @param id ユーザーID
     * @return ユーザー情報
     */
    public Optional<LoginUser> findUserById(Long id);
}

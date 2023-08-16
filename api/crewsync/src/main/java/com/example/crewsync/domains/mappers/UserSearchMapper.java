package com.example.crewsync.domains.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.security.LoginUser;

/**
 * ユーザー検索画面のマッパーインターフェースです
 */
@Mapper
public interface UserSearchMapper {

    /**
     * 検索条件に基づいてユーザー一覧を検索します
     *
     * @param form 検索フォーム
     * @return ユーザー一覧
     */
    public List<LoginUser> loadUserList(UserSearchForm form);
}

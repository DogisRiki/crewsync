package com.example.crewsync.domains.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.mappers.OrganizationMapper;
import com.example.crewsync.domains.mappers.UserSearchMapper;
import com.example.crewsync.security.LoginUser;

@Service
public class UserSearchService {

    private final OrganizationMapper organizationMapper;

    private final UserSearchMapper userSearchMapper;

    @Autowired
    public UserSearchService(OrganizationMapper organizationMapper, UserSearchMapper userSearchMapper) {
        this.organizationMapper = organizationMapper;
        this.userSearchMapper = userSearchMapper;
    }

    /**
     * ユーザー情報検索フォームを生成します
     *
     * @return ユーザー情報検索フォーム
     */
    public UserSearchForm initUserSearchForm() {
        UserSearchForm form = new UserSearchForm();
        this.populateOptions(form);
        return form;
    }

    /**
     * 検索条件に基づいてユーザーを検索します
     *
     * @param form ユーザー情報検索フォーム
     * @return ユーザー一覧
     */
    public List<LoginUser> loadUserList(UserSearchForm form) {
        this.populateOptions(form);
        return userSearchMapper.loadUserList(form);
    }

    /**
     * 検索条件に基づいたユーザー検索結果の件数を取得します
     *
     * @param form ユーザー情報検索フォーム
     * @return 検索件数
     */
    public int countUser(UserSearchForm form) {
        return userSearchMapper.countUser(form);
    }

    /**
     * フォームに部署と役職を設定します
     *
     * @param form ユーザー情報検索フォーム
     */
    private void populateOptions(UserSearchForm form) {
        form.setDeptOptions(organizationMapper.getDepartmentCd());
        form.setPosOptions(organizationMapper.getPositionCd());
    }
}

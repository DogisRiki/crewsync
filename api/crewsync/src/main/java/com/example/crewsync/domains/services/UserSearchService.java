package com.example.crewsync.domains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.mappers.OrganizationMapper;

@Service
public class UserSearchService {

    private final OrganizationMapper organizationMapper;

    @Autowired
    public UserSearchService(OrganizationMapper organizationMapper) {
        this.organizationMapper = organizationMapper;
    }

    /**
     * ユーザー情報検索情報を生成します
     *
     * @return ユーザー情報検索フォーム
     */
    public UserSearchForm initUserSearchForm() {
        UserSearchForm form = new UserSearchForm();
        form.setDeptOptions(organizationMapper.getDepartmentCd());
        form.setPosOptions(organizationMapper.getPositionCd());
        return form;
    }
}

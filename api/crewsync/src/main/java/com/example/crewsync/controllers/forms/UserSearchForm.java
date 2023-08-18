package com.example.crewsync.controllers.forms;

import java.util.List;

import com.example.crewsync.domains.models.SearchOption;

import lombok.Data;

/**
 * ユーザー情報検索フォームです
 */
@Data
public class UserSearchForm {

    private String empNo;

    private String name;

    private String deptCd;

    private String posCd;

    private List<SearchOption> deptOptions;

    private List<SearchOption> posOptions;

    private int pageFrom;

    private int pageTo;
}

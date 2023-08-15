package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.domains.services.UserSearchService;

@Controller
public class UserSearchController {

    private final UserSearchService userSearchService;

    @Autowired
    public UserSearchController(UserSearchService userSearchService) {
        this.userSearchService = userSearchService;
    }

    /**
     * ユーザー検索画面初期表示処理です
     *
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/manage/users/userlist")
    public String initSearch(Model model) {
        model.addAttribute("userSearchForm", userSearchService.initUserSearchForm());
        return RouteConstants.USERLIST;
    }
}

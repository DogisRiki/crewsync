package com.example.crewsync.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.constants.MessageKeys;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.services.UserSearchService;
import com.example.crewsync.security.LoginUser;

@Controller
public class UserSearchController {

    private final UserSearchService userSearchService;

    private final NotificationMessage notificationMessage;

    @Autowired
    public UserSearchController(UserSearchService userSearchService, NotificationMessage notificationMessage) {
        this.userSearchService = userSearchService;
        this.notificationMessage = notificationMessage;
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

    /**
     * ユーザー検索処理を実行します
     *
     * @param form  検索条件フォーム
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/manage/users/search")
    public String searchUser(@ModelAttribute UserSearchForm form, Model model) {

        List<LoginUser> userList = userSearchService.loadUserList(form);

        // 該当するユーザーが1件も見つからない場合
        if (userList.isEmpty()) {
            model.addAttribute("notificationMessage", notificationMessage.builder()
                    .messageLevel(NotificationMessage.MESSAGE_LEVEL_INFO)
                    .messageCode(MessageKeys.USER_NOTFOUND)
                    .build());
        }

        model.addAttribute("userList", userList);
        model.addAttribute("userSearchForm", form);
        return RouteConstants.USERLIST;
    }
}

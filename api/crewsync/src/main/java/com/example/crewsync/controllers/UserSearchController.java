package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.SearchResult;
import com.example.crewsync.common.utils.constants.MessageKeys;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.services.UserSearchService;
import com.example.crewsync.security.LoginUser;

@Controller
@Scope("session")
public class UserSearchController {

    private final UserSearchService userSearchService;

    private final NotificationMessage notificationMessage;

    // ページネーションで検索結果を持ちまわりたい
    private UserSearchForm form;

    // 検索結果を1ページに表示する上限数
    public static final int PAGE_LIMIT = 10;

    @Autowired
    public UserSearchController(UserSearchService userSearchService, NotificationMessage notificationMessage) {
        this.userSearchService = userSearchService;
        this.notificationMessage = notificationMessage;
    }

    /**
     * ユーザー検索画面の初期表示処理を実行します
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
     * @param userSearchForm 検索条件フォーム
     * @param model          モデル
     * @return 遷移先
     */
    @GetMapping("/manage/users/search")
    public String searchUser(@ModelAttribute UserSearchForm userSearchForm, Model model) {

        this.form = userSearchForm;

        form.setPageFrom(0);
        form.setPageTo(PAGE_LIMIT);

        SearchResult<LoginUser> searchResult = new SearchResult<>(userSearchService.countUser(form), PAGE_LIMIT);
        searchResult.moveTo(1);
        searchResult.setEntities(userSearchService.loadUserList(form));

        // 該当するユーザーが1件も見つからない場合
        if (searchResult.getEntities().isEmpty()) {
            model.addAttribute("notificationMessage", notificationMessage.builder()
                    .messageLevel(NotificationMessage.MESSAGE_LEVEL_INFO)
                    .messageCode(MessageKeys.USER_NOTFOUND)
                    .build());
        }

        model.addAttribute("searchResult", searchResult);
        model.addAttribute("userSearchForm", form);
        return RouteConstants.USERLIST;
    }

    /**
     * ページネーション表示処理を実行します
     *
     * @param pageNo ページ番号
     * @param model  モデル
     * @return 遷移先
     */
    @GetMapping("/manage/users/userview")
    public String initUserPageView(@RequestParam("p") int pageNo, Model model) {

        model.addAttribute("userSearchForm", this.form);

        SearchResult<LoginUser> searchResult = new SearchResult<>(userSearchService.countUser(form), PAGE_LIMIT);

        // リクエストパラメータチェック
        if (pageNo < 1 || pageNo > searchResult.getTotalPageCount()) {
            return RouteConstants.USERLIST;
        }

        searchResult.moveTo(pageNo);
        form.setPageFrom((pageNo - 1) * PAGE_LIMIT);
        searchResult.setEntities(userSearchService.loadUserList(form));
        model.addAttribute("searchResult", searchResult);

        return RouteConstants.USERLIST;
    }
}

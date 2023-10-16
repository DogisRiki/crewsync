package com.example.crewsync.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {

    /**
     * トップページ画面初期表示処理を実行します
     *
     * @param userDetails ユーザー認証情報
     * @param model       モデル
     * @return 遷移先
     */
    @GetMapping("/")
    public String initTop(@AuthenticationPrincipal LoginUserDetails userDetails, Model model) {
        if (userDetails != null) {
            LoginUser user = userDetails.getLoginUser();
            model.addAttribute("loginUser", user);
        }
        return RouteConstants.TOP;
    }

    /**
     * ログイン画面初期表示処理を実行します
     *
     * @param session セッション情報
     * @param model   モデル
     * @return 遷移先
     */
    @GetMapping("/login")
    public String initLogin(HttpSession session, Model model) {
        String errorMessage = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (errorMessage != null) {
            log.error("ログインエラー発生 : {}", errorMessage);
            model.addAttribute("loginError", errorMessage);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        return RouteConstants.LOGIN;
    }
}

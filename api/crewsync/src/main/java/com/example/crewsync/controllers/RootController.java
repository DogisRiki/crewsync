package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.crewsync.common.utils.exceptions.DuplicateEmailException;
import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.services.UserRegisterService;
import com.example.crewsync.security.LoginUser;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RootController {

    private final UserRegisterService userRegisterService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RootController(UserRegisterService userRegisterService, PasswordEncoder passwordEncoder) {
        this.userRegisterService = userRegisterService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String root() {
        return "index";
    }

    /**
     * ログイン画面初期表示処理です
     *
     * @param session セッション
     * @param model   モデル
     * @return 遷移先
     */
    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        String errorMessage = (String) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (errorMessage != null) {
            log.error("ログインエラー発生 : {}", errorMessage);
            model.addAttribute("loginError", errorMessage);
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        return "login";
    }

    /**
     * ユーザー登録画面初期表示処理です
     *
     * @param model モデル
     * @return 遷移先
     */
    @GetMapping("/register")
    public String signup(Model model) {
        if (!model.containsAttribute("userRegisterForm")) {
            model.addAttribute("userRegisterForm", new UserRegisterForm());
        }
        return "signup";
    }

    /**
     * ユーザー登録処理を実行します
     *
     * @param form          入力内容
     * @param result        バリデーション結果
     * @param model         モデル
     * @param redirectAttrs リダイレクト先
     * @return 遷移先
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserRegisterForm form, BindingResult result, Model model,
            RedirectAttributes redirectAttrs) {
        // バリデーションチェック
        if (result.hasErrors()) {
            redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterForm", result);
            redirectAttrs.addFlashAttribute("userRegisterForm", form);
            return "redirect:/register";
        }
        // メールアドレスの重複チェック
        try {
            userRegisterService.isDupplicateEmail(form.getEmail());
        } catch (DuplicateEmailException e) {
            redirectAttrs.addFlashAttribute("emailDupplicateError", e.getMessage());
            redirectAttrs.addFlashAttribute("userRegisterForm", form);
            return "redirect:/register";
        }
        // 入力内容詰め替え
        LoginUser user = new LoginUser();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRegisterService.registerUser(user);
        return "redirect:/login";
    }
}

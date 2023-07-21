package com.example.crewsync.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.services.UserRegisterService;
import com.example.crewsync.security.LoginUser;

import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/register")
    public String signup() {
        return "signup";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterForm form) {
        LoginUser user = new LoginUser();
        user.setEmail(form.getEmail());
        user.setUsername(form.getUsername());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRegisterService.registerUser(user);
        return "redirect:/login";
    }
}

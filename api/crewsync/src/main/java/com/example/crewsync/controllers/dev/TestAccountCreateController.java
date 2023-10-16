package com.example.crewsync.controllers.dev;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

/**
 * 仮のアカウント作成用コントローラです
 */
@Controller
public class TestAccountCreateController {

    private final PasswordEncoder passwordEncoder;

    private final DataSource dataSource;

    @Autowired
    public TestAccountCreateController(PasswordEncoder passwordEncoder, DataSource dataSource) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
    }

    @GetMapping("/dev")
    public String initDev() {
        return "redirect:dev/create-account";
    }

    @GetMapping("/dev/create-account")
    public String initCreateAccount() {
        return "dev/create-account";
    }

    @PostMapping("/dev/create-account")
    public String createAccount(
            @RequestParam String email,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String emp_no,
            @RequestParam String dept_cd,
            @RequestParam String pos_cd,
            @RequestParam String role,
            Model model) {
        try (Connection connection = dataSource.getConnection()) {
            String encodedPassword = passwordEncoder.encode(password);
            String sql = "INSERT INTO users (email, avf, name, password, emp_no, dept_cd, pos_cd) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, email);
                preparedStatement.setDate(2, new java.sql.Date(new Date().getTime()));
                preparedStatement.setString(3, name);
                preparedStatement.setString(4, encodedPassword);
                preparedStatement.setString(5, emp_no);
                preparedStatement.setString(6, dept_cd);
                preparedStatement.setString(7, pos_cd);
                preparedStatement.executeUpdate();

                // user_idの取得
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long userId = generatedKeys.getLong(1);

                        // 権限の挿入
                        String roleSql = "INSERT INTO user_roles (user_id, role, delflg) VALUES (?, ?, ?)";
                        try (PreparedStatement roleStatement = connection.prepareStatement(roleSql)) {
                            roleStatement.setLong(1, userId);
                            roleStatement.setString(2, role);
                            roleStatement.setString(3, "0");
                            roleStatement.executeUpdate();
                        }
                    }
                }

                model.addAttribute("message", "アカウントが作成されました");
            }
        } catch (Exception e) {
            model.addAttribute("error", "アカウントの作成に失敗しました");
        }
        return "dev/create-account";
    }

}

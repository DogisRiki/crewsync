package com.example.crewsync.controllers.forms;

import lombok.Data;

/**
 * ユーザー情報登録フォームです
 */
@Data
public class UserRegisterForm {

    private String email;

    private String emailConfirm;

    private String username;

    private String password;

    private String passwordConfirm;

}

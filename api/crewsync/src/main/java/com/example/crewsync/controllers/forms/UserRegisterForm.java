package com.example.crewsync.controllers.forms;

import java.io.Serializable;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報登録フォームです
 */
@Data
public class UserRegisterForm implements Serializable {

    private static final long serialVersionUID = -4292831594774687625L;

    @Size(max = 128, message = "{userRegisterForm.email.Size}")
    @Pattern(regexp="^[\\w]+@crewsync\\.jp$", message = "{userRegisterForm.email.Pattern}")
    private String email;

    @Size(max = 128)
    private String emailConfirm;

    @Size(max = 64, message = "{userRegisterForm.username.Size}")
    private String username;

    @Size(min = 8, max = 20, message = "{userRegisterForm.password.Size}")
    private String password;

    @Size(min = 8, max = 20)
    private String passwordConfirm;

    @AssertTrue
    public boolean isEmailConfirmed() {
        if (email == null && emailConfirm == null) {
            return true;
        }
        return email.equals(emailConfirm);
    }

    @AssertTrue
    public boolean isPasswordConfirmed() {
        if (password == null && passwordConfirm == null) {
            return true;
        }
        return password.equals(passwordConfirm);
    }

}

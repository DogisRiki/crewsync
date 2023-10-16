package com.example.crewsync.controllers.forms;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import com.example.crewsync.controllers.validators.annotations.ConfirmedPassword;
import com.example.crewsync.controllers.validators.interfaces.PasswordConfirmation;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * プロフィール編集画面フォームです
 */
@ConfirmedPassword
@Data
public class ProfileEditForm implements Serializable, PasswordConfirmation {

    private static final long serialVersionUID = 7306338206635346619L;

    private long userId;

    private MultipartFile uploadFile;

    @Size(min = 8, max = 20, message = "{profileEditForm.password.Size}")
    private String password;

    private String passwordConfirm;

    @Pattern(regexp = "^\\d{7}$", message = "{profileEditForm.zipcode.Pattern}")
    private String zipcode;

    @Size(max = 8, message = "{profileEditForm.pref.Size}")
    private String pref;

    @Size(max = 64, message = "{profileEditForm.city.Size}")
    private String city;

    @Size(max = 64, message = "{profileEditForm.bldg.Size}")
    private String bldg;

    @Pattern(regexp = "^0\\d{9,10}$", message = "{profileEditForm.phoneNo}")
    private String phoneNo;

    @Pattern(regexp = "^0[789]0\\d{8}$", message = "{profileEditForm.mobilePhoneNo}")
    private String mobilePhoneNo;

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }
}

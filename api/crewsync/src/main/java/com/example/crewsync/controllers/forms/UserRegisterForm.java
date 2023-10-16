package com.example.crewsync.controllers.forms;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.example.crewsync.controllers.validators.annotations.ConfirmedPassword;
import com.example.crewsync.controllers.validators.annotations.UniqueEmail;
import com.example.crewsync.controllers.validators.interfaces.PasswordConfirmation;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.models.SearchOption;
import com.example.crewsync.security.LoginUser;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * ユーザー情報登録入力フォームです
 */
@Data
@ConfirmedPassword
public class UserRegisterForm implements Serializable, PasswordConfirmation {

    private static final long serialVersionUID = -4292831594774687625L;

    @UniqueEmail
    @Size(max = 128, message = "{userRegisterForm.email.Size}")
    @Pattern(regexp = "^[\\w]+@crewsync\\.jp$", message = "{userRegisterForm.email.Pattern}")
    private String email;

    @Size(max = 64, message = "{userRegisterForm.username.Size}")
    private String username;

    @Size(min = 8, max = 20, message = "{userRegisterForm.password.Size}")
    private String password;

    @Size(min = 8, max = 20)
    private String passwordConfirm;

    private String empNo;

    private MultipartFile uploadFile;

    private ImageFile imageFile;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date avf;

    private String deptCd;

    private String posCd;

    private List<String> roles;

    private List<SearchOption> deptOptions;

    private List<SearchOption> posOptions;

    private List<SearchOption> roleOptions;

    private LoginUser user;

    public UserRegisterForm() {
        this(new LoginUser());
    }

    // LoginUserオブジェクトからUserRegisterFormオブジェクトを生成する
    public UserRegisterForm(LoginUser user) {
        this.user = user;
        this.empNo = user.getEmpNo();
        this.imageFile = (user.getImageFile() != null) ? user.getImageFile() : new ImageFile();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avf = user.getAvf();
        this.deptCd = user.getDeptName();
        this.posCd = user.getPosCd();
        if (user.getRoles() != null) {
            this.roles = user.getRoles().stream()
                    .map(o -> o.getCode())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getPasswordConfirm() {
        return this.passwordConfirm;
    }

}

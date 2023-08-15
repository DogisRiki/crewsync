package com.example.crewsync.controllers.forms;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * プロフィール編集画面フォームです
 */
public class ProfileEditForm implements Serializable {

    private static final long serialVersionUID = 7306338206635346619L;

    private long userId;

    private MultipartFile uploadFile;

    @Size(min = 8, max = 20, message = "{ProfileEditForm.password.Size}")
    private String password;

    private String passwordConfirmed;

    @Pattern(regexp = "^\\d{7}$", message = "{ProfileEditForm.zipcode.Pattern}")
    private String zipcode;

    @Size(max = 8, message = "{ProfileEditForm.pref.Size}")
    private String pref;

    @Size(max = 64, message = "{ProfileEditForm.city.Size}")
    private String city;

    @Size(max = 64, message = "{ProfileEditForm.bldg.Size}")
    private String bldg;

    @Pattern(regexp = "^0\\d{9,10}$", message = "{ProfileEditForm.phoneNo}")
    private String phoneNo;

    @Pattern(regexp = "^0[789]0\\d{8}$", message = "{ProfileEditForm.mobilePhoneNo}")
    private String mobilePhoneNo;

    @AssertTrue(message = "{AssertTrue.ProfileEditForm.passwordConfirmed}")
    public boolean isPasswordConfirmed() {
        return ObjectUtils.nullSafeEquals(password, passwordConfirmed);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirmed;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirmed = passwordConfirm;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPref() {
        return pref;
    }

    public void setPref(String pref) {
        this.pref = pref;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBldg() {
        return bldg;
    }

    public void setBldg(String bldg) {
        this.bldg = bldg;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getMobilePhoneNo() {
        return mobilePhoneNo;
    }

    public void setMobilePhoneNo(String mobilePhoneNo) {
        this.mobilePhoneNo = mobilePhoneNo;
    }

    public MultipartFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(MultipartFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

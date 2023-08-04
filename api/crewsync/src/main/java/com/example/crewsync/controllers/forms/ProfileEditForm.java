package com.example.crewsync.controllers.forms;

import java.io.Serializable;

import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Size;

/**
 * プロフィール編集画面入力フォームです
 */
public class ProfileEditForm implements Serializable {

    private static final long serialVersionUID = 7306338206635346619L;

    private long userId;

    private MultipartFile uploadFile;

    @Size(min = 8, max = 20)
    private String password;

    @Size(min = 8, max = 20)
    private String passwordConfirmed;

    @Size(min = 7, max = 7)
    private String zipcode;

    @Size(max = 8)
    private String pref;

    @Size(max = 64)
    private String city;

    @Size(max = 64)
    private String bldg;

    @Size(max = 32)
    private String phoneNo;

    @Size(max = 32)
    private String mobilePhoneNo;

    @AssertTrue
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

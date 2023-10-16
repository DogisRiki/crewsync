package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.domains.mappers.ProfileEditMapper;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.security.LoginUser;

@MybatisTest
@ActiveProfiles("test")
public class ProfileEditMapperUnitTest {

    @Autowired
    private ProfileEditMapper profileEditMapper;

    @TestConfiguration
    static class passwordEncoderConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("createProfileEditForm()")
    public void test001_createProfileEditForm() {
        long id = 120L;
        Optional<ProfileEditForm> result = profileEditMapper.createProfileEditForm(id);
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("updateProfile()")
    public void test002_updateProfile() {
        LoginUser user = new LoginUser();
        user.setId(1L);
        user.setPassword(passwordEncoder.encode("password"));
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName("/img/00_profile/12345/admin.jpg");
        user.setImageFile(imageFile);
        int result = profileEditMapper.updateProfile(user);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("updatePersonalInfo()")
    public void test003_updatePersonalInfo() {
        ProfileEditForm form = new ProfileEditForm();
        form.setUserId(120L);
        form.setZipcode("2002000");
        form.setPref("東京都");
        form.setCity("千代田区千代田");
        form.setBldg("1-1");
        form.setPhoneNo("0311112222");
        form.setMobilePhoneNo("08011112222");
        int result = profileEditMapper.updatePersonalInfo(form);
        assertEquals(1, result);
    }

    @Test
    @DisplayName("getImageFileById()")
    public void test004_getImageFileById() {
        long id = 1L;
        ImageFile result = profileEditMapper.getImageFileById(id);
        assertNotNull(result);
        assertEquals("/img/00_profile/00000000/admin.jpg", result.getFileName());
    }
}

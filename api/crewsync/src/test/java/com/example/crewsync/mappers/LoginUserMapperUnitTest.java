package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.security.LoginUser;

@MybatisTest
@ActiveProfiles("test")
public class LoginUserMapperUnitTest {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Test
    @DisplayName("ユーザー情報が存在する場合、正しくを取得できる")
    public void identifyUser_001() {
        Optional<LoginUser> result = loginUserMapper.identifyUser("admin@crewsync.jp");
        // Optionalがnullでないことを検査
        assertNotNull(result);
        // Optionalが値を持っていることを検査
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("ユーザー情報が存在しない場合、OptionalがNullになる")
    public void identifyUser_002() {
        Optional<LoginUser> result = loginUserMapper.identifyUser("notfound@crewsync.jp");
        // Optionalがnullであることを検査
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("ユーザー情報が存在する場合、正しくを取得できる")
    public void findUserById_001() {
        Optional<LoginUser> result = loginUserMapper.findUserById(1L);
        // Optionalがnullでないことを検査
        assertNotNull(result);
        // Optionalが値を持っていることを検査
        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("ユーザー情報が存在しない場合、OptionalがNullになる")
    public void findUserById_002() {
        Optional<LoginUser> result = loginUserMapper.findUserById(100L);
        // Optionalがnullであることを検査
        assertTrue(result.isEmpty());
    }
}

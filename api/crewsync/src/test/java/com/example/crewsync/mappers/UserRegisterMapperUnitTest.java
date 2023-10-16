package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.domains.mappers.UserRegisterMapper;
import com.example.crewsync.security.LoginUser;

@MybatisTest
@ActiveProfiles("test")
public class UserRegisterMapperUnitTest {

    @Autowired
    private UserRegisterMapper userRegisterMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    @DisplayName("ユーザー権限が正常に削除できる")
    public void test_depriveAuthority() {
        LoginUser user = new LoginUser();
        user.setId(1L);
        String role = "03";

        // 削除実行
        userRegisterMapper.depriveAuthority(user, role);

        // 削除されていることを確認
        Integer deleteCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user_roles WHERE user_id = ? AND role = ?",
                Integer.class, user.getId(), role);
        assertEquals(0, deleteCount);
    }

    @Test
    @DisplayName("ユーザー権限が正常に登録できる")
    public void test_grantAuthority() {
        LoginUser user = new LoginUser();
        user.setId(1L);
        String role = "02";

        // 登録実行
        int registerCount = userRegisterMapper.grantAuthority(user, role);

        // 登録されたことを確認
        assertEquals(registerCount, 1);
    }

    @Test
    @DisplayName("初期ユーザーが正常に登録できる")
    public void test_registerInitialUser() {
        LoginUser user = new LoginUser();
        user.setEmail("initial@crewsync.jp");
        user.setUsername("初期ユーザー登録テスト");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmpNo("10000000");

        // 登録実行
        int registerCount = userRegisterMapper.registerInitialUser(user);

        // 登録されたことを確認
        assertEquals(registerCount, 1);
    }
}

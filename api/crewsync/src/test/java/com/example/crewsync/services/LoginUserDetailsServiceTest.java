package com.example.crewsync.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.models.SearchOption;
import com.example.crewsync.domains.services.LoginUserDetailsService;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

public class LoginUserDetailsServiceTest {

    private LoginUserDetailsService loginUserDetailsService;

    private LoginUserMapper loginUserMapper;

    @BeforeEach
    void setUp() {
        loginUserMapper = mock(LoginUserMapper.class);
        loginUserDetailsService = new LoginUserDetailsService(loginUserMapper);
    }

    @Test
    @DisplayName("存在するユーザー名でLoginUserDetailsが正常に取得できる")
    void test001_loadUserByUsername() {
        // Given
        LoginUser loginUser = new LoginUser();
        loginUser.setId(1);
        String email = "test@example.com";
        loginUser.setEmail(email);
        loginUser.setAvf(new Date());
        loginUser.setUsername("TestUser");
        loginUser.setPassword("password");
        loginUser.setLocked(false);
        loginUser.setExpired(false);
        SearchOption searchOption = new SearchOption();
        searchOption.setName("ROLE_USER");
        searchOption.setCode("01");
        List<SearchOption> roles = List.of(searchOption);
        loginUser.setRoles(roles);
        loginUser.setEmpNo("1");
        loginUser.setDeptName("一般");
        loginUser.setPosName("一般");
        ImageFile imageFile = new ImageFile();
        imageFile.setFileName("/img/img.png");
        loginUser.setImageFile(imageFile);

        // Stub
        when(loginUserMapper.identifyUser(email)).thenReturn(Optional.ofNullable(loginUser));

        // When
        LoginUserDetails loginUserDetails = (LoginUserDetails) loginUserDetailsService.loadUserByUsername(email);

        // Then
        assertAll(
                () -> assertNotNull(loginUserDetails.getLoginUser()),
                () -> assertEquals(loginUser, loginUserDetails.getLoginUser()),
                () -> assertEquals(
                        loginUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode()))
                                .collect(Collectors
                                        .toList()),
                        loginUserDetails.getAuthorities()),
                () -> assertEquals(loginUser.getPassword(), loginUserDetails.getPassword()),
                () -> assertEquals(loginUser.getUsername(), loginUserDetails.getUsername()),
                () -> assertTrue(loginUserDetails.isAccountNonExpired()),
                () -> assertTrue(loginUserDetails.isAccountNonLocked()),
                () -> assertTrue(loginUserDetails.isCredentialsNonExpired()),
                () -> assertTrue(loginUserDetails.isEnabled()));
    }

    @Test
    @DisplayName("存在しないユーザー名だとUsernameNotFoundExceptionをスロー")
    void tesy002_loadUserByUsername() {
        // Given
        String email = "test@example.com";
        LoginUser loginUser = new LoginUser();
        loginUser.setEmail(email);

        // Stub
        when(loginUserMapper.identifyUser(email)).thenReturn(Optional.ofNullable(loginUser));

        // When & Then
        assertThrows(UsernameNotFoundException.class, () -> {
            loginUserDetailsService.loadUserByUsername("notfound@example.com");
        });
    }
}

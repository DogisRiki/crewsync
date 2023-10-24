package com.example.crewsync.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.WebAttributes;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.annotations.TestWithAdmin;
import com.example.crewsync.controllers.annotations.TestWithAnonymous;
import com.example.crewsync.controllers.annotations.TestWithManager;
import com.example.crewsync.controllers.annotations.TestWithUser;
import com.example.crewsync.domains.services.LoginUserDetailsService;
import com.example.crewsync.security.LoginAuthenticationFailureHandler;
import com.example.crewsync.security.SecurityConfig;

import jakarta.servlet.http.HttpSession;

@WebMvcTest(controllers = LoginController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        LoginUserDetailsService.class, SecurityConfig.class, LoginAuthenticationFailureHandler.class }))
@AutoConfigureMybatis
@TestPropertySource(properties = { "spring.thymeleaf.prefix=classpath:/templates/" })
public class LoginControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HttpSession session;

    @Nested
    class testInitLogin {
        @Test
        @DisplayName("/loginにリクエストするとログインビューが返却される")
        void test001_initLogin() throws Exception {
            mockMvc.perform(get("/login"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.LOGIN));
        }

        @Test
        @DisplayName("エラーメッセージがセッションにある場合、モデルにエラーメッセージが追加される")
        void test002_initLogin() throws Exception {
            String errorMessage = "ログイン失敗";
            when(session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION)).thenReturn(errorMessage);

            mockMvc.perform(get("/login").sessionAttr(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.LOGIN))
                    .andExpect(model().attribute("loginError", errorMessage));
        }
    }

    @Nested
    class testInitTop {
        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        void withAdmin() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.TOP))
                    .andExpect(model().attributeExists("loginUser"));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        void withManager() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.TOP))
                    .andExpect(model().attributeExists("loginUser"));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        void withUser() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.TOP))
                    .andExpect(model().attributeExists("loginUser"));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはリダイレクトされる")
        void withAnonymous() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }
    }

}

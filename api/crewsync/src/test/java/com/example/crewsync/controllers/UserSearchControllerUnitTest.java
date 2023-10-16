package com.example.crewsync.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.SearchResult;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.annotations.TestWithAdmin;
import com.example.crewsync.controllers.annotations.TestWithAnonymous;
import com.example.crewsync.controllers.annotations.TestWithManager;
import com.example.crewsync.controllers.annotations.TestWithUser;
import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.services.LoginUserDetailsService;
import com.example.crewsync.domains.services.UserSearchService;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.SecurityConfig;

@WebMvcTest(controllers = UserSearchController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        LoginUserDetailsService.class, SecurityConfig.class }))
@AutoConfigureMybatis
@TestPropertySource(properties = { "spring.thymeleaf.prefix=classpath:/templates/" })
public class UserSearchControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserSearchService userSearchService;

    @MockBean
    private NotificationMessage notificationMessage;

    @MockBean
    private NotificationMessage.NotificationMessageBuilder notificationMessageBuilder;

    @Nested
    class testInitSerch {

        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        public void withAdmin() throws Exception {
            UserSearchForm userSearchForm = new UserSearchForm();
            when(userSearchService.initUserSearchForm()).thenReturn(userSearchForm);
            mockMvc.perform(get("/manage/users/userlist"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attribute("userSearchForm", userSearchForm));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        public void withManager() throws Exception {
            UserSearchForm userSearchForm = new UserSearchForm();
            when(userSearchService.initUserSearchForm()).thenReturn(userSearchForm);
            mockMvc.perform(get("/manage/users/userlist"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attribute("userSearchForm", userSearchForm));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        public void withUser() throws Exception {
            UserSearchForm userSearchForm = new UserSearchForm();
            when(userSearchService.initUserSearchForm()).thenReturn(userSearchForm);
            mockMvc.perform(get("/manage/users/userlist"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attribute("userSearchForm", userSearchForm));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはリダイレクトされる")
        public void withAnonymous() throws Exception {
            mockMvc.perform(get("/manage/users/userlist"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }
    }

    @Nested
    class testSearchUser {

        @Test
        @TestWithAdmin
        @DisplayName("ADMINは検索可能")
        public void withAdmin() throws Exception {
            UserSearchForm form = new UserSearchForm();
            SearchResult<LoginUser> searchResult = new SearchResult<>(0, UserSearchController.PAGE_LIMIT);
            LoginUser loginUser = new LoginUser();
            loginUser.setEmail("admin@crewsync.jp");
            List<LoginUser> loginUserList = Arrays.asList(loginUser);
            searchResult.setEntities(loginUserList);

            when(userSearchService.countUser(any())).thenReturn(1);
            when(userSearchService.loadUserList(any())).thenReturn(loginUserList);

            mockMvc.perform(get("/manage/users/search").flashAttr("userSearchForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attributeExists("searchResult"))
                    .andExpect(model().attribute("userSearchForm", form));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERは検索可能")
        public void withManager() throws Exception {
            UserSearchForm form = new UserSearchForm();
            SearchResult<LoginUser> searchResult = new SearchResult<>(0, UserSearchController.PAGE_LIMIT);
            LoginUser loginUser = new LoginUser();
            loginUser.setEmail("manager@crewsync.jp");
            List<LoginUser> loginUserList = Arrays.asList(loginUser);
            searchResult.setEntities(loginUserList);

            when(userSearchService.countUser(any())).thenReturn(1);
            when(userSearchService.loadUserList(any())).thenReturn(loginUserList);

            mockMvc.perform(get("/manage/users/search").flashAttr("userSearchForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attributeExists("searchResult"))
                    .andExpect(model().attribute("userSearchForm", form));
        }

        @Test
        @TestWithUser
        @DisplayName("USERは検索可能")
        public void withUser() throws Exception {
            UserSearchForm form = new UserSearchForm();
            SearchResult<LoginUser> searchResult = new SearchResult<>(0, UserSearchController.PAGE_LIMIT);
            LoginUser loginUser = new LoginUser();
            loginUser.setEmail("user@crewsync.jp");
            List<LoginUser> loginUserList = Arrays.asList(loginUser);
            searchResult.setEntities(loginUserList);

            when(userSearchService.countUser(any())).thenReturn(1);
            when(userSearchService.loadUserList(any())).thenReturn(loginUserList);

            mockMvc.perform(get("/manage/users/search").flashAttr("userSearchForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attributeExists("searchResult"))
                    .andExpect(model().attribute("userSearchForm", form));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはリダイレクトされる")
        public void withAnonymous() throws Exception {
            mockMvc.perform(get("/manage/users/search"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }

        @Test
        @TestWithAdmin
        @DisplayName("検索結果が0件の場合はモデルにメッセージが追加される")
        public void resultIsEmpty() throws Exception {
            UserSearchForm form = new UserSearchForm();

            when(userSearchService.countUser(any())).thenReturn(0);
            when(userSearchService.loadUserList(any())).thenReturn(new ArrayList<>());

            // メッセージ
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(get("/manage/users/search").flashAttr("userSearchForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USERLIST))
                    .andExpect(model().attribute("notificationMessage", notificationMessage))
                    .andExpect(model().attributeExists("searchResult"))
                    .andExpect(model().attribute("userSearchForm", form));
        }
    }
}

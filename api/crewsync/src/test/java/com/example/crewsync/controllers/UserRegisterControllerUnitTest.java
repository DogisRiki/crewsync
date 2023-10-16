package com.example.crewsync.controllers;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.example.crewsync.common.utils.NotificationMessage;
import com.example.crewsync.common.utils.constants.MessageKeys;
import com.example.crewsync.common.utils.constants.RouteConstants;
import com.example.crewsync.controllers.annotations.TestWithAdmin;
import com.example.crewsync.controllers.annotations.TestWithAnonymous;
import com.example.crewsync.controllers.annotations.TestWithManager;
import com.example.crewsync.controllers.annotations.TestWithUser;
import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.services.LoginUserDetailsService;
import com.example.crewsync.domains.services.UserRegisterService;
import com.example.crewsync.security.SecurityConfig;

@WebMvcTest(controllers = UserRegisterController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        LoginUserDetailsService.class, SecurityConfig.class }))
@AutoConfigureMybatis
@TestPropertySource(properties = { "spring.thymeleaf.prefix=classpath:/templates/" })
public class UserRegisterControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegisterService userRegisterService;

    @MockBean
    private NotificationMessage notificationMessage;

    @MockBean
    private NotificationMessage.NotificationMessageBuilder notificationMessageBuilder;

    @Nested
    class testInitUserRegister {
        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        public void withAdmin() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            Long id = 1L;
            when(userRegisterService.initUserRegisterForm(id)).thenReturn(form);
            mockMvc.perform(get("/manage/users/register")
                    .param("id", id.toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        public void withManager() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            Long id = 1L;
            when(userRegisterService.initUserRegisterForm(id)).thenReturn(form);
            mockMvc.perform(get("/manage/users/register")
                    .param("id", id.toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        public void withUser() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            Long id = 1L;
            when(userRegisterService.initUserRegisterForm(id)).thenReturn(form);
            mockMvc.perform(get("/manage/users/register")
                    .param("id", id.toString()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはリダイレクトされる")
        public void withAnonymous() throws Exception {
            mockMvc.perform(get("/manage/users/register"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }
    }

    @Nested
    class testOnRegisterUser {

        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        public void withAdmin() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            doNothing().when(userRegisterService).registerUser(any(), any());
            when(userRegisterService.initUserRegisterForm(anyLong())).thenReturn(form);
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/manage/users/register/submit")
                    .with(csrf())
                    .flashAttr("userRegisterForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        public void withManager() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            doNothing().when(userRegisterService).registerUser(any(), any());
            when(userRegisterService.initUserRegisterForm(anyLong())).thenReturn(form);
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/manage/users/register/submit")
                    .with(csrf())
                    .flashAttr("userRegisterForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        public void withUser() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            doNothing().when(userRegisterService).registerUser(any(), any());
            when(userRegisterService.initUserRegisterForm(anyLong())).thenReturn(form);
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/manage/users/register/submit")
                    .with(csrf())
                    .flashAttr("userRegisterForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはエラー発生")
        public void withAnonymous() throws Exception {
            mockMvc.perform(post("/manage/users/register/submit"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class testOnRegisterUserException {
        @Test
        @TestWithAdmin
        @DisplayName("バインディングエラーが存在する場合")
        public void bindingErrorCase() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            doNothing().when(userRegisterService).restoreRegisterForm(form);
            when(userRegisterService.initUserRegisterForm(anyLong())).thenReturn(form);
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/manage/users/register/submit")
                    .with(csrf())
                    .flashAttr("userRegisterForm", form)
                    .flashAttr(BindingResult.MODEL_KEY_PREFIX + "userRegisterForm", createBindingResultWithErrors()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm", form));
        }

        private BindingResult createBindingResultWithErrors() {
            BindingResult bindingResult = new BeanPropertyBindingResult(new UserRegisterForm(), "userRegisterForm");
            bindingResult.addError(new ObjectError("userRegisterForm", "Error message"));
            return bindingResult;
        }

        @Test
        @TestWithAdmin
        @DisplayName("Exceptionが発生する場合")
        public void exceptionCase() throws Exception {
            UserRegisterForm form = new UserRegisterForm();
            doThrow(new RuntimeException("Unexpected Error")).when(userRegisterService).registerUser(any(), any());
            when(userRegisterService.initUserRegisterForm(anyLong())).thenReturn(form);
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(NotificationMessage.MESSAGE_LEVEL_ERROR))
                    .thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(MessageKeys.NOTIFY_ERROR))
                    .thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/manage/users/register/submit")
                    .with(csrf())
                    .flashAttr("userRegisterForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.USER_REGISTER))
                    .andExpect(model().attribute("userRegisterForm",
                            hasProperty("imageFile",
                                    hasProperty("fileName",
                                            is("crewsync/src/main/resources/static/img/anonymous.png")))))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }
    }

}

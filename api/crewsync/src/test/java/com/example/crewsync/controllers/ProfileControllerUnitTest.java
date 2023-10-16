package com.example.crewsync.controllers;

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
import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.services.LoginUserDetailsService;
import com.example.crewsync.domains.services.ProfileEditService;
import com.example.crewsync.security.SecurityConfig;

@WebMvcTest(controllers = ProfileController.class, includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        LoginUserDetailsService.class, SecurityConfig.class }))
@AutoConfigureMybatis
@TestPropertySource(properties = { "spring.thymeleaf.prefix=classpath:/templates/" })
public class ProfileControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileEditService profileEditService;

    @MockBean
    private NotificationMessage notificationMessage;

    @MockBean
    private NotificationMessage.NotificationMessageBuilder notificationMessageBuilder;

    @Nested
    class testInitProfile {
        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        public void withAdmin() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            when(profileEditService.initPersonalInfo(any())).thenReturn(form);
            mockMvc.perform(get("/profile"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("profileEditForm", form));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        public void withManager() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            when(profileEditService.initPersonalInfo(any())).thenReturn(form);
            mockMvc.perform(get("/profile"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("profileEditForm", form));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        public void withUser() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            when(profileEditService.initPersonalInfo(any())).thenReturn(form);
            mockMvc.perform(get("/profile"))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("profileEditForm", form));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはリダイレクトされる")
        public void withAnonymous() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            when(profileEditService.initPersonalInfo(any())).thenReturn(form);
            mockMvc.perform(get("/profile"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrlPattern("**/login"));
        }
    }

    @Nested
    class testOnEditProfile {

        @Test
        @TestWithAdmin
        @DisplayName("ADMINはアクセス可能")
        public void withAdmin() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            doNothing().when(profileEditService).editProfile(any(), any());
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/profile/edit")
                    .with(csrf())
                    .flashAttr("profileEditForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithManager
        @DisplayName("MANAGERはアクセス可能")
        public void withManager() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            doNothing().when(profileEditService).editProfile(any(), any());
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/profile/edit")
                    .with(csrf())
                    .flashAttr("profileEditForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithUser
        @DisplayName("USERはアクセス可能")
        public void withUser() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            doNothing().when(profileEditService).editProfile(any(), any());
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/profile/edit")
                    .with(csrf())
                    .flashAttr("profileEditForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }

        @Test
        @TestWithAnonymous
        @DisplayName("匿名ユーザーはエラー発生")
        public void withAnonymous() throws Exception {
            mockMvc.perform(post("/profile"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class testOnEditProfilerException {
        @Test
        @TestWithAdmin
        @DisplayName("バインディングエラーが存在する場合")
        public void bindingErrorCase() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            doNothing().when(profileEditService).editProfile(any(), any());
            when(profileEditService.getProfileImg(anyLong())).thenReturn(new ImageFile());
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(anyInt())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(anyString())).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/profile/edit")
                    .with(csrf())
                    .flashAttr("profileEditForm", form)
                    .flashAttr(BindingResult.MODEL_KEY_PREFIX + "profileEditForm", createBindingResultWithErrors()))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("profileEditForm", form));
        }

        private BindingResult createBindingResultWithErrors() {
            BindingResult bindingResult = new BeanPropertyBindingResult(new ProfileEditForm(), "profileEditForm");
            bindingResult.addError(new ObjectError("profileEditForm", "Error message"));
            return bindingResult;
        }

        @Test
        @TestWithAdmin
        @DisplayName("Exceptionが発生する場合")
        public void exceptionCase() throws Exception {
            ProfileEditForm form = new ProfileEditForm();
            doThrow(new RuntimeException("Unexpected Error")).when(profileEditService).editProfile(any(), any());
            when(profileEditService.getProfileImg(anyLong())).thenReturn(new ImageFile());
            when(notificationMessage.builder()).thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageLevel(NotificationMessage.MESSAGE_LEVEL_ERROR))
                    .thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.messageCode(MessageKeys.NOTIFY_ERROR))
                    .thenReturn(notificationMessageBuilder);
            when(notificationMessageBuilder.build()).thenReturn(notificationMessage);

            mockMvc.perform(post("/profile/edit")
                    .with(csrf())
                    .flashAttr("profileEditForm", form))
                    .andExpect(status().isOk())
                    .andExpect(view().name(RouteConstants.PROFILE))
                    .andExpect(model().attribute("notificationMessage", notificationMessage));
        }
    }
}

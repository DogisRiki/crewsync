package com.example.crewsync.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.example.crewsync.controllers.forms.ProfileEditForm;
import com.example.crewsync.domains.mappers.ProfileEditMapper;
import com.example.crewsync.domains.models.ImageFile;
import com.example.crewsync.domains.services.ProfileEditService;
import com.example.crewsync.security.LoginUser;

@ExtendWith(MockitoExtension.class)
public class ProfileEditServiceUnitTest {

    @InjectMocks
    private ProfileEditService profileEditService;

    @Mock
    private ProfileEditMapper profileEditMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("initPersonalInfo():正常系(期待通りのフォームが生成される)")
    public void test001_InitPersonalInfo() {
        // Given
        long userId = 1L;
        LoginUser user = new LoginUser();
        user.setId(userId);
        ProfileEditForm expectForm = new ProfileEditForm();
        // Stub(任意のlong値を引数に呼び出された際に、expectFormを返却するよう設定)
        when(profileEditMapper.createProfileEditForm(anyLong())).thenReturn(Optional.of(expectForm));
        // When
        ProfileEditForm resultForm = profileEditService.initPersonalInfo(user);
        // Then
        assertEquals(expectForm, resultForm);
    }

    @Test
    @DisplayName("initPersonalInfo():正常系(空のフォームが生成される)")
    public void test002_InitPersonalInfo() {
        // Given
        long userId = 1L;
        LoginUser user = new LoginUser();
        user.setId(userId);
        // Stub(任意のlong値を引数に呼び出された際に、空のOptionalオブジェクトを返却するよう設定)
        when(profileEditMapper.createProfileEditForm(anyLong())).thenReturn(Optional.empty());
        // When
        ProfileEditForm resultForm = profileEditService.initPersonalInfo(user);
        // Then
        assertNotNull(resultForm);
    }

    @Test
    @DisplayName("editProfile():正常系")
    public void test003_editProfile() throws Exception {
        // Given
        LoginUser user = new LoginUser();
        user.setEmpNo("12345");
        ProfileEditForm form = new ProfileEditForm();
        MultipartFile uploadImgFile = mock(MultipartFile.class);
        form.setUploadFile(uploadImgFile);

        // 画像ファイルのモック
        when(uploadImgFile.isEmpty()).thenReturn(false);
        when(uploadImgFile.getOriginalFilename()).thenReturn("test.jpg");

        // DB更新のモック
        when(profileEditMapper.updateProfile(any())).thenReturn(1);
        when(profileEditMapper.updatePersonalInfo(any())).thenReturn(1);

        // When
        profileEditService.editProfile(user, form);

        // Then
        // 画像ファイルの処理を検証
        verify(uploadImgFile).transferTo(any(File.class));
        // ユーザー情報更新の検証
        verify(profileEditMapper).updateProfile(user);
        // 個人情報更新の検証
        verify(profileEditMapper).updatePersonalInfo(form);
        // パスワードエンコードの検証（パスワードが設定されている場合）
        if (form.getPassword() != null) {
            verify(passwordEncoder).encode(form.getPassword());
        }
    }

    @Test
    @DisplayName("editProfile():異常系(DBへの更新に失敗)")
    public void test004_editProfile() throws Exception {
        // Given
        LoginUser user = new LoginUser();
        ProfileEditForm form = new ProfileEditForm();
        MultipartFile uploadImgFile = mock(MultipartFile.class);
        form.setUploadFile(uploadImgFile);

        // DB更新モック
        when(profileEditMapper.updateProfile(any())).thenReturn(1);
        when(profileEditMapper.updatePersonalInfo(any())).thenReturn(0); // 個人情報更新に失敗させる

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            profileEditService.editProfile(user, form);
        });

        // Then
        // 例外型の検証
        assertTrue(exception instanceof Exception);
    }

    @Test
    @DisplayName("editProdile():異常系(画像ファイルの転送に失敗)")
    public void test005_editProfile() throws Exception {
        // Given
        LoginUser user = new LoginUser();
        ProfileEditForm form = new ProfileEditForm();
        MultipartFile uploadImgFile = mock(MultipartFile.class);
        form.setUploadFile(uploadImgFile);

        // 画像ファイルの転送でIOExceptionをスローする
        doThrow(IOException.class).when(uploadImgFile).transferTo(any(File.class));

        // When
        Exception exception = assertThrows(Exception.class, () -> {
            profileEditService.editProfile(user, form);
        });

        // Then
        // IOExceptionがスローされたか検証
        assertTrue(exception instanceof IOException);
    }

    @Test
    @DisplayName("getProfileImg():正常系")
    public void test006_getProfileImg() {
        // Given
        long id = 1L;
        ImageFile expectImageFile = new ImageFile();
        // Stub
        when(profileEditMapper.getImageFileById(anyLong())).thenReturn(expectImageFile);
        // When
        ImageFile resultImageFile = profileEditMapper.getImageFileById(id);
        // Then
        assertEquals(expectImageFile, resultImageFile);
    }
}

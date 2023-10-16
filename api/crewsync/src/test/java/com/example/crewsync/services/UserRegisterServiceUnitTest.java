package com.example.crewsync.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crewsync.controllers.forms.UserRegisterForm;
import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.domains.mappers.OrganizationMapper;
import com.example.crewsync.domains.models.SearchOption;
import com.example.crewsync.domains.services.UserRegisterService;
import com.example.crewsync.security.LoginUser;

@ExtendWith(MockitoExtension.class)
public class UserRegisterServiceUnitTest {

    @InjectMocks
    private UserRegisterService userRegisterService;

    @Mock
    private OrganizationMapper organizationMapper;

    @Mock
    private LoginUserMapper loginUserMapper;

    @Test
    @DisplayName("")
    public void test_initUserRegisterForm() throws Exception {

        // OrganizationMapperの挙動を定義
        SearchOption deptSearchOption = new SearchOption();
        deptSearchOption.setCode("0000000");
        deptSearchOption.setName("システム管理");
        when(organizationMapper.getDepartmentCd()).thenReturn(List.of(deptSearchOption));

        SearchOption posSearchOption = new SearchOption();
        posSearchOption.setCode("0000");
        posSearchOption.setName("管理者");
        when(organizationMapper.getPositionCd()).thenReturn(List.of(posSearchOption));

        SearchOption roleSearchOption = new SearchOption();
        roleSearchOption.setCode("03");
        roleSearchOption.setName("ROLE_ADMIN");
        when(organizationMapper.getCode(anyLong())).thenReturn(List.of(roleSearchOption));

        // LoginUserMapperの挙動を定義
        when(loginUserMapper.identifyUser(anyString())).thenReturn(Optional.of(new LoginUser()));

        // Service実行
        UserRegisterForm userRegisterForm = userRegisterService.initUserRegisterForm("admin@crewsync.jp");

        // 検査
        assertAll(
                () -> {
                    assertEquals(1, userRegisterForm.getDeptOptions().size());
                    SearchOption deptOption = userRegisterForm.getDeptOptions().get(0);
                    assertEquals("0000000", deptOption.getCode());
                    assertEquals("システム管理", deptOption.getName());
                },
                () -> {
                    assertEquals(1, userRegisterForm.getPosOptions().size());
                    SearchOption posOption = userRegisterForm.getPosOptions().get(0);
                    assertEquals("0000", posOption.getCode());
                    assertEquals("管理者", posOption.getName());
                },
                () -> {
                    assertEquals(1, userRegisterForm.getRoleOptions().size());
                    SearchOption roleOption = userRegisterForm.getRoleOptions().get(0);
                    assertEquals("03", roleOption.getCode());
                    assertEquals("ROLE_ADMIN", roleOption.getName());
                });
    }

}

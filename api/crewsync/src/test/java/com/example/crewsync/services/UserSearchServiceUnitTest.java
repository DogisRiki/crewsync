package com.example.crewsync.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.mappers.OrganizationMapper;
import com.example.crewsync.domains.mappers.UserSearchMapper;
import com.example.crewsync.domains.models.SearchOption;
import com.example.crewsync.domains.services.UserSearchService;
import com.example.crewsync.security.LoginUser;

@ExtendWith(MockitoExtension.class)
public class UserSearchServiceUnitTest {

    @InjectMocks
    private UserSearchService userSearchService;

    @Mock
    private OrganizationMapper organizationMapper;

    @Mock
    private UserSearchMapper userSearchMapper;

    @Test
    @DisplayName("initUserSearchForm():正常にフォームが生成される")
    public void test001_initUserSearchForm() {
        // Given
        SearchOption deptOption = new SearchOption();
        deptOption.setCode("0000000");
        deptOption.setName("システム管理");

        SearchOption posOption = new SearchOption();
        posOption.setCode("0000");
        posOption.setName("管理者");

        List<SearchOption> deptOptions = Collections.singletonList(deptOption);
        List<SearchOption> posOptions = Collections.singletonList(posOption);

        // Stub
        when(organizationMapper.getDepartmentCd()).thenReturn(deptOptions);
        when(organizationMapper.getPositionCd()).thenReturn(posOptions);

        // When
        UserSearchForm form = userSearchService.initUserSearchForm();

        // Then
        assertNotNull(form);
        assertEquals(deptOptions, form.getDeptOptions());
        assertEquals(posOptions, form.getPosOptions());

        // Verify
        verify(organizationMapper).getDepartmentCd();
        verify(organizationMapper).getPositionCd();
    }

    @Test
    @DisplayName("initUserSearchForm():空のフォームが生成される")
    public void test002_initUserSearchForm() {
        // Given
        SearchOption deptOption = new SearchOption();
        SearchOption posOption = new SearchOption();

        List<SearchOption> deptOptions = Collections.singletonList(deptOption);
        List<SearchOption> posOptions = Collections.singletonList(posOption);

        // Stub
        when(organizationMapper.getDepartmentCd()).thenReturn(deptOptions);
        when(organizationMapper.getPositionCd()).thenReturn(posOptions);

        // When
        UserSearchForm form = userSearchService.initUserSearchForm();

        // Then
        assertNotNull(form);
        assertEquals(deptOptions, form.getDeptOptions());
        assertEquals(posOptions, form.getPosOptions());

        // Verify interactions
        verify(organizationMapper).getDepartmentCd();
        verify(organizationMapper).getPositionCd();
    }

    @Test
    @DisplayName("loadUserList():正常にユーザーリストが取得できる")
    public void test003_loadUserList() {
        // Given
        UserSearchForm form = new UserSearchForm();
        SearchOption deptOption = new SearchOption();
        SearchOption posOption = new SearchOption();
        List<SearchOption> deptOptions = Collections.singletonList(deptOption);
        List<SearchOption> posOptions = Collections.singletonList(posOption);

        LoginUser user = new LoginUser();
        List<LoginUser> expectedUserList = Collections.singletonList(user);

        // Stub
        when(organizationMapper.getDepartmentCd()).thenReturn(deptOptions);
        when(organizationMapper.getPositionCd()).thenReturn(posOptions);
        when(userSearchMapper.loadUserList(form)).thenReturn(expectedUserList);

        // When
        List<LoginUser> actualUserList = userSearchService.loadUserList(form);

        // Then
        assertNotNull(actualUserList);
        assertEquals(expectedUserList.size(), actualUserList.size());
        assertEquals(expectedUserList, actualUserList);

        // Verify
        verify(organizationMapper).getDepartmentCd();
        verify(organizationMapper).getPositionCd();
        verify(userSearchMapper).loadUserList(form);
    }

    @Test
    @DisplayName("loadUserList():検索結果0件の場合")
    public void test004_loadUserList() {
        // Given
        UserSearchForm form = new UserSearchForm();
        SearchOption deptOption = new SearchOption();
        SearchOption posOption = new SearchOption();
        List<SearchOption> deptOptions = Collections.singletonList(deptOption);
        List<SearchOption> posOptions = Collections.singletonList(posOption);

        List<LoginUser> expectedUserList = Collections.emptyList(); // 空のリスト

        // Stub
        when(organizationMapper.getDepartmentCd()).thenReturn(deptOptions);
        when(organizationMapper.getPositionCd()).thenReturn(posOptions);
        when(userSearchMapper.loadUserList(form)).thenReturn(expectedUserList); // 空のリストを返す

        // When
        List<LoginUser> actualUserList = userSearchService.loadUserList(form);

        // Then
        assertNotNull(actualUserList);
        assertTrue(actualUserList.isEmpty()); // 結果が空であることを確認
        assertEquals(expectedUserList, actualUserList);

        // Verify
        verify(organizationMapper).getDepartmentCd();
        verify(organizationMapper).getPositionCd();
        verify(userSearchMapper).loadUserList(form);
    }

    @Test
    @DisplayName("countUser()")
    public void test005_countUser_oneResult() {
        // Given
        UserSearchForm form = new UserSearchForm();
        int expectedCount = 1;

        // Stub
        when(userSearchMapper.countUser(form)).thenReturn(expectedCount);

        // When
        int actualCount = userSearchService.countUser(form);

        // Then
        assertEquals(expectedCount, actualCount);

        // Verify
        verify(userSearchMapper).countUser(form);
    }
}

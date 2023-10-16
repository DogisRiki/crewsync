package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.controllers.forms.UserSearchForm;
import com.example.crewsync.domains.mappers.UserSearchMapper;
import com.example.crewsync.security.LoginUser;

@MybatisTest
@ActiveProfiles("test")
public class UserSearchMapperUnitTest {

    @Autowired
    private UserSearchMapper userSearchMapper;

    @Test
    @DisplayName("loadUserList()")
    public void test001_loadUserList() {
        // Given:ユーザーをセット
        LoginUser expectUser = new LoginUser();
        expectUser.setEmail("admin@crewsync.jp");
        expectUser.setEmpNo("00000000");
        expectUser.setUsername("admin");
        expectUser.setDeptName("システム管理");
        expectUser.setPosName("管理者");
        List<LoginUser> expectUserList = Arrays.asList(expectUser);
        // Given:ユーザーをセット
        LoginUser expectUser2 = new LoginUser();
        expectUser2.setEmail("manager@crewsync.jp");
        expectUser2.setEmpNo("00000001");
        expectUser2.setUsername("manager");
        expectUser2.setDeptName("営業部");
        expectUser2.setPosName("部長");
        List<LoginUser> expectUserList2 = Arrays.asList(expectUser2);
        // Given:ユーザーをセット
        LoginUser expectUser3 = new LoginUser();
        expectUser3.setEmail("manager2@crewsync.jp");
        expectUser3.setEmpNo("00000002");
        expectUser3.setUsername("manager2");
        expectUser3.setDeptName("経理部");
        expectUser3.setPosName("課長");
        List<LoginUser> expectUserList3 = Arrays.asList(expectUser3);
        // Given:ユーザーをセット
        LoginUser expectUser4 = new LoginUser();
        expectUser4.setEmail("user@crewsync.jp");
        expectUser4.setEmpNo("00000003");
        expectUser4.setUsername("user");
        expectUser4.setDeptName("人事部");
        expectUser4.setPosName("一般");
        List<LoginUser> expectUserList4 = Arrays.asList(expectUser4);
        // Given:検索条件をセット(社員名)
        UserSearchForm expectForm = new UserSearchForm();
        expectForm.setName("admin");
        expectForm.setPageFrom(0);
        expectForm.setPageTo(10);
        // Given:検索条件をセット(社員番号)
        UserSearchForm expectForm2 = new UserSearchForm();
        expectForm2.setEmpNo("00000001");
        expectForm2.setPageFrom(0);
        expectForm2.setPageTo(10);
        // Given:検索条件をセット(部署)
        UserSearchForm expectForm3 = new UserSearchForm();
        expectForm3.setDeptCd("0000002");
        expectForm3.setPageFrom(0);
        expectForm3.setPageTo(10);
        // Given:検索条件をセット(所属)
        UserSearchForm expectForm4 = new UserSearchForm();
        expectForm4.setPosCd("0004");
        expectForm4.setPageFrom(0);
        expectForm4.setPageTo(10);
        // When
        List<LoginUser> resultUserList = userSearchMapper.loadUserList(expectForm);
        List<LoginUser> resultUserList2 = userSearchMapper.loadUserList(expectForm2);
        List<LoginUser> resultUserList3 = userSearchMapper.loadUserList(expectForm3);
        List<LoginUser> resultUserList4 = userSearchMapper.loadUserList(expectForm4);
        // Then
        assertEquals(expectUserList, resultUserList);
        assertEquals(expectUserList2, resultUserList2);
        assertEquals(expectUserList3, resultUserList3);
        assertEquals(expectUserList4, resultUserList4);
    }

    @Test
    @DisplayName("loadUserList():該当しない検索条件")
    public void test002_loadUserList() {
        // Given
        UserSearchForm expectForm = new UserSearchForm();
        expectForm.setName("notfound");
        // When
        List<LoginUser> resultUserList = userSearchMapper.loadUserList(expectForm);
        // Then
        assertTrue(resultUserList.isEmpty(), "List is Empty");
    }

    @Test
    @DisplayName("loadUserList():検索条件指定なし")
    public void test003_loadUserList() {
        // Given
        UserSearchForm expectForm = new UserSearchForm();
        expectForm.setPageFrom(0);
        expectForm.setPageTo(10);
        // When
        List<LoginUser> resultUserList = userSearchMapper.loadUserList(expectForm);
        // Then
        assertEquals(4, resultUserList.size());
    }

    @Test
    @DisplayName("countUser():検索条件指定なし")
    public void test004_countUser() {
        // Given
        UserSearchForm expectForm = new UserSearchForm();
        expectForm.setPageFrom(0);
        expectForm.setPageTo(10);
        // When
        int count = userSearchMapper.countUser(expectForm);
        // Then
        assertEquals(4, count);
    }

    @Test
    @DisplayName("countUser():検索条件指定あり")
    public void test005_countUser() {
        // Given
        UserSearchForm expectForm = new UserSearchForm();
        expectForm.setPageFrom(0);
        expectForm.setPageTo(10);
        expectForm.setName("user");
        // When
        int count = userSearchMapper.countUser(expectForm);
        // Then
        assertEquals(1, count);
    }

}

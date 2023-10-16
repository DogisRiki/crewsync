package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.domains.mappers.OrganizationMapper;
import com.example.crewsync.domains.models.SearchOption;

@MybatisTest
@ActiveProfiles("test")
public class OrganizationMapperUnitTest {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Test
    @DisplayName("getDepartmentCd()")
    public void test001_getDepartmentCd() {
        // Given
        SearchOption so1 = new SearchOption();
        so1.setCode("0000000");
        so1.setName("システム管理");
        SearchOption so2 = new SearchOption();
        so2.setCode("0000001");
        so2.setName("営業部");
        SearchOption so3 = new SearchOption();
        so3.setCode("0000002");
        so3.setName("経理部");
        SearchOption so4 = new SearchOption();
        so4.setCode("0000003");
        so4.setName("人事部");
        SearchOption so5 = new SearchOption();
        so5.setCode("0000004");
        so5.setName("総務部");
        // When
        List<SearchOption> expectList = Arrays.asList(so1, so2, so3, so4, so5);
        List<SearchOption> actualList = organizationMapper.getDepartmentCd();
        // Then
        assertEquals(expectList, actualList);
    }

    @Test
    @DisplayName("getPositionCd()")
    public void test002_getPositionCd() {
        // Given
        SearchOption so1 = new SearchOption();
        so1.setCode("0000");
        so1.setName("管理者");
        SearchOption so2 = new SearchOption();
        so2.setCode("0001");
        so2.setName("部長");
        SearchOption so3 = new SearchOption();
        so3.setCode("0002");
        so3.setName("課長");
        SearchOption so4 = new SearchOption();
        so4.setCode("0003");
        so4.setName("係長");
        SearchOption so5 = new SearchOption();
        so5.setCode("0004");
        so5.setName("一般");
        // When
        List<SearchOption> expectList = Arrays.asList(so1, so2, so3, so4, so5);
        List<SearchOption> actualList = organizationMapper.getPositionCd();
        // Then
        assertEquals(expectList, actualList);
    }

    @Test
    @DisplayName("getCode()")
    public void test003_getCode() {
        // Given
        SearchOption so1 = new SearchOption();
        so1.setCode("01");
        so1.setName("ROLE_USER");
        SearchOption so2 = new SearchOption();
        so2.setCode("02");
        so2.setName("ROLE_MANAGER");
        SearchOption so3 = new SearchOption();
        so3.setCode("03");
        so3.setName("ROLE_ADMIN");
        // When
        List<SearchOption> expectList = Arrays.asList(so1, so2, so3);
        List<SearchOption> actualList = organizationMapper.getCode(1);
        // Then
        assertEquals(expectList, actualList);
    }

}

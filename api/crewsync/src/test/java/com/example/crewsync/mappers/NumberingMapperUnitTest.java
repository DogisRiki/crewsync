package com.example.crewsync.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.example.crewsync.domains.mappers.NumberingMapper;
import com.example.crewsync.security.LoginUser;

@MybatisTest
@ActiveProfiles("test")
public class NumberingMapperUnitTest {

    @Autowired
    private NumberingMapper numberingMapper;

    @Mock
    private LoginUser loginUser;

    @Test
    @DisplayName("正しく発番ができる")
    public void test001_issueNumber() {
        String issuedNumber = numberingMapper.issueNumber("E00", "2023");
        assertEquals("0001", issuedNumber);
    }

    @Test
    @DisplayName("次番号の確保ができる")
    public void test002_next() {
        when(loginUser.getId()).thenReturn(1L);
        numberingMapper.next("E00", "2023", loginUser);
        String issuedNumber = numberingMapper.issueNumber("E00", "2023");
        assertEquals("0002", issuedNumber);
    }
}

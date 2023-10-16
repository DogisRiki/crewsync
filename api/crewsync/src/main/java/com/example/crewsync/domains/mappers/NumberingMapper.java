package com.example.crewsync.domains.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.crewsync.security.LoginUser;

/**
 * 番号管理台帳のマッパーインターフェースです
 */
@Mapper
public interface NumberingMapper {

    // 発番の先頭固定値
    public static final String NUMBERING_CODE_EMPNO = "E00";

    /**
     * 指定された番号コードと利用年に基づいて次の番号を発行します
     *
     * @param numberingCode 番号コード
     * @param availYear     利用年
     * @return 指定された条件に基づいてフォーマットされた次の番号を返します
     */
    public String issueNumber(@Param("numberingCode") String numberingCode, @Param("availYear") String availYear);

    /**
     * 指定された番号コードと利用年に基づいて番号管理台帳を更新し、次の番号を準備します
     *
     * @param numberingCode 番号コード
     * @param availYear     利用年
     * @param user          更新を行うユーザーの情報を含むLoginUserオブジェクト
     */
    public void next(@Param("numberingCode") String numberingCode, @Param("availYear") String availYear,
            @Param("user") LoginUser user);
}

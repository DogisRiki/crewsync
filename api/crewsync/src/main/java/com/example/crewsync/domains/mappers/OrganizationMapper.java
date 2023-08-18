package com.example.crewsync.domains.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.crewsync.domains.models.SearchOption;

/**
 * ユーザー検索画面プルダウン初期化のマッパーインターフェースです
 */
@Mapper
public interface OrganizationMapper {

    /**
     * 部署一覧を取得します
     *
     * @return 部署一覧
     */
    public List<SearchOption> getDepartmentCd();

    /**
     * 役職一覧を取得します
     *
     * @return 所属一覧
     */
    public List<SearchOption> getPositionCd();

}

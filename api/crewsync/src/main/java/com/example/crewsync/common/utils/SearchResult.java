package com.example.crewsync.common.utils;

import lombok.Data;
import java.util.List;

@Data
public class SearchResult<E> {

    private int pageFrom;

    private int pageTo;

    // 現在のページ数
    private int currentPage;

    // 1ページあたりに表示する件数
    private int recordPerPage;

    // ページの総数
    private int totalPageCount;

    // 検索結果の総数
    private int totalRecordCount;

    // 検索結果を格納
    private List<E> entities;

    /**
     * 外部から取得する必要のあるプロパティなのでコンストラクタとして定義します
     *
     * @param totalRecordCount ページ数の総数
     * @param recordPerPage    1ページあたりに表示する件数
     */
    public SearchResult(int totalRecordCount, int recordPerPage) {
        this.totalRecordCount = totalRecordCount;
        this.recordPerPage = recordPerPage;
        this.totalPageCount = (this.totalRecordCount / this.recordPerPage)
                + (this.totalRecordCount % this.recordPerPage > 0 ? 1 : 0);
    }

    /**
     * 指定されたページに移動し、ページネーションの範囲を設定します
     * <p>
     * このメソッドは、中央部分のページ範囲(例: "1 ... 456 ... 10" の "456")を設定します
     *
     * @param page 移動するページ番号
     */
    public void moveTo(int page) {
        // 現在のページを設定
        this.currentPage = page;
        // 中央部分のページ範囲の開始ページを設定(最小値は2)
        this.pageFrom = Math.max(this.currentPage - 1, 2);
        // 中央部分のページ範囲の終了ページを設定
        this.pageTo = Math.min(this.pageFrom + 2, this.totalPageCount - 1);
        // 開始ページを再設定
        this.pageFrom = Math.max(this.pageTo - 2, 2);
    }

}

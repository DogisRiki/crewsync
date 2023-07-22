package com.example.crewsync.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * ユーザー情報を保持するモデルクラスです
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements Serializable {

    // シリアライザー
    private static final long serialVersionUID = -4292831594774687625L;

    // ユーザーID
    private long id;

    // メールアドレス
    private String email;

    // 世代管理用の日付
    private Date avf;

    // ユーザー名
    private String username;

    // パスワード
    private String password;

    // アカウントがロックされているか否か
    private boolean locked;

    // アカウントの有効期限
    private boolean expired;

    // ロール
    private List<String> roles;

}

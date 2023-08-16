package com.example.crewsync.common.utils.constants;

/**
 * エラーメッセージキー定数クラスです
 */
public final class MessageKeys {

    // 実行後の通知メッセージ
    public static final String NOTIFY_INFO = "";
    public static final String NOTIFY_SUCCESS = "message.success";
    public static final String NOTIFY_WARN = "";
    public static final String NOTIFY_ERROR = "message.failure";

    // ログインエラー
    public static final String BAD_CREDENTIALS = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    public static final String DISABLED = "AbstractUserDetailsAuthenticationProvider.disabled";
    public static final String EXPIRED = "AbstractUserDetailsAuthenticationProvider.expired";
    public static final String LOCKED = "AbstractUserDetailsAuthenticationProvider.locked";
    public static final String UNKNOWN = "AbstractUserDetailsAuthenticationProvider.unknown";

    // ユーザー検索エラー
    public static final String USER_NOTFOUND = "message.usernotfound";

    // ユーザー登録エラー
    public static final String DUPLICATE_EMAIL = "Error.DuplicateEmailException";

    /**
     * インスタンス化をしないようにする
     */
    private MessageKeys() {
    }
}

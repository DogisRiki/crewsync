package com.example.crewsync.common.utils.constants;

/**
 * エラーメッセージキー定数クラスです
 */
public final class ErrorMessageKeys {

    // ログインエラー
    public static final String BAD_CREDENTIALS = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    public static final String DISABLED = "AbstractUserDetailsAuthenticationProvider.disabled";
    public static final String EXPIRED = "AbstractUserDetailsAuthenticationProvider.expired";
    public static final String LOCKED = "AbstractUserDetailsAuthenticationProvider.locked";
    public static final String UNKNOWN = "AbstractUserDetailsAuthenticationProvider.unknown";

    // ユーザー登録エラー
    public static final String DUPLICATE_EMAIL = "Error.DuplicateEmailException";

    /**
     * インスタンス化をしないようにする
     */
    private ErrorMessageKeys() {
    }
}

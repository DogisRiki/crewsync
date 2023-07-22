package com.example.crewsync.common.utils.constraints;

/**
 * エラーメッセージキー定数クラスです
 */
public final class ErrorMessageKeys {

    public static final String BAD_CREDENTIALS = "AbstractUserDetailsAuthenticationProvider.badCredentials";
    public static final String DISABLED = "AbstractUserDetailsAuthenticationProvider.disabled";
    public static final String EXPIRED = "AbstractUserDetailsAuthenticationProvider.expired";
    public static final String LOCKED = "AbstractUserDetailsAuthenticationProvider.locked";
    public static final String UNKNOWN = "AbstractUserDetailsAuthenticationProvider.unknown";

    /**
     * インスタンス化をしないようにする
     */
    private ErrorMessageKeys() {
    }
}

package com.example.crewsync.security;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.example.crewsync.common.utils.constraints.ErrorMessageKeys;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン失敗時のカスタム認証ハンドラーです
 *
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final String redirectUrl;

    private final MessageSource messageSource;

    public LoginAuthenticationFailureHandler(String redirectUrl, MessageSource messageSource) {
        this.redirectUrl = redirectUrl;
        this.messageSource = messageSource;
    }

    /**
     * 認証が失敗したときに呼び出されます
     *
     * @param request   認証が失敗した際のリクエスト
     * @param response  認証が失敗した際のレスポンス
     * @param exception 発生した認証エラーの例外
     * @throws IOException      リクエストを指定されたURLに転送する際に、I/Oエラーが発生した場合
     * @throws ServletException リクエストを指定されたURLに転送する際に、サーブレットエラーが発生した場合
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception)
            throws IOException, ServletException {

        String errorMessageKey;

        switch (exception.getClass().getSimpleName()) {
            case "BadCredentialsException":
                errorMessageKey = ErrorMessageKeys.BAD_CREDENTIALS;
                break;
            case "DisabledException":
                errorMessageKey = ErrorMessageKeys.DISABLED;
                break;
            case "AccountExpiredException":
                errorMessageKey = ErrorMessageKeys.EXPIRED;
                break;
            case "LockedException":
                errorMessageKey = ErrorMessageKeys.LOCKED;
                break;
            default:
                errorMessageKey = ErrorMessageKeys.UNKNOWN;
                break;
        }

        String errorMessage = messageSource.getMessage(errorMessageKey, null, request.getLocale());
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, errorMessage);
        }

        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, this.redirectUrl);
    }
}

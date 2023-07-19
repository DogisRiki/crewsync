package com.example.crewsync.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * ログイン失敗時のカスタム認証失敗ハンドラーです
 *
 */
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    // リダイレクト先URL
    private final String redirectUrl;

    public LoginAuthenticationFailureHandler(String redirectUrl) {
        this.redirectUrl = redirectUrl;
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
        HttpSession session = request.getSession(false);
        if (session != null) {
            request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        }
        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, this.redirectUrl);
    }

}

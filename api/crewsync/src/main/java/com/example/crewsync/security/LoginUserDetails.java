package com.example.crewsync.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ユーザ詳細情報を表現するUserDetailsインターフェースを実装します
 */
public class LoginUserDetails implements UserDetails {

    // ログインユーザーインスタンス
    private final LoginUser loginUser;

    // 権限リスト
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * ユーザーのロールをGrantedAuthorityとして表現し、リストとして保持します
     *
     * @param loginUser ログインユーザーインスタンス
     */
    public LoginUserDetails(LoginUser loginUser) {
        this.loginUser = loginUser;
        this.authorities = loginUser.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * ユーザーの権限リストを返します
     *
     * @return 権限リスト
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * ハッシュ化されたパスワードを返します
     *
     * @return パスワード
     */
    @Override
    public String getPassword() {
        return loginUser.getPassword();
    }

    /**
     * ユーザー名を返します
     *
     * @return ユーザー名
     */
    @Override
    public String getUsername() {
        return loginUser.getUsername();
    }

    /**
     * アカウントが期限切れでないかチェックします
     *
     * @return 期限切れでない場合はtrue
     */
    @Override
    public boolean isAccountNonExpired() {
        return !loginUser.isExpired();
    }

    /**
     * アカウントがロックされていないかチェックします
     *
     * @return ロックされていない場合はtrue
     */
    @Override
    public boolean isAccountNonLocked() {
        return !loginUser.isLocked();
    }

    /**
     * 資格情報（パスワード）が期限切れでないかチェックします
     *
     * @return 期限切れでない場合はtrue
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * ユーザが有効かどうかを返します
     *
     * @return ユーザが有効な場合はtrue
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}

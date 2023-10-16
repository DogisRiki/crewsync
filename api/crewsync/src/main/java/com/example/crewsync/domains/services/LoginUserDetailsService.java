package com.example.crewsync.domains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.crewsync.common.aspects.annotations.LogRequired;
import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

/**
 * UserDetailsServiceを実装したサービスクラスです
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginUserMapper loginUserMapper;

    @Autowired
    public LoginUserDetailsService(LoginUserMapper loginUserMapper) {
        this.loginUserMapper = loginUserMapper;
    }

    /**
     * ユーザー情報をDBから検索します
     *
     * @param email メールアドレス
     * @return LoginUserDetailsオブジェクト
     * @throws UsernameNotFoundException 指定されたメールアドレスを持つユーザーが見つからない場合
     */
    @Override
    @LogRequired
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginUser loginUser = loginUserMapper.identifyUser(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        return new LoginUserDetails(loginUser);
    }
}

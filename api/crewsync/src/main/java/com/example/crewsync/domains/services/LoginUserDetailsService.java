package com.example.crewsync.domains.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.security.LoginUser;
import com.example.crewsync.security.LoginUserDetails;

/**
 * UserDetailsServiceを実装します
 */
@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private final LoginUserMapper loginUserMapper;

    public LoginUserDetailsService(LoginUserMapper loginUserMapper) {
        this.loginUserMapper = loginUserMapper;
    }

    /**
     * ユーザー情報をDBから検索します
     *
     * @param username ユーザー名
     * @return LoginUserDetailsオブジェクト
     * @throws UsernameNotFoundException 指定されたユーザー名を持つユーザーが見つからない場合
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = Optional.ofNullable(loginUserMapper.identifyUser(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new LoginUserDetails(loginUser);
    }
}

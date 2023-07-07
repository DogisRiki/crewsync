package com.example.crewsync.domains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.crewsync.domains.mappers.LoginUserMapper;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserMapper.identifyUser(username);
    }
}

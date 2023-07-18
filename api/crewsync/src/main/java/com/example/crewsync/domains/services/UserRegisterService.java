package com.example.crewsync.domains.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.security.LoginUser;

/**
 * ユーザー登録サービスクラスです
 */
@Service
public class UserRegisterService {

    @Autowired
    private final LoginUserMapper loginUserMapper;

    public UserRegisterService(LoginUserMapper loginUserMapper) {
        this.loginUserMapper = loginUserMapper;
    }

    /**
     * ユーザー情報を登録します
     *
     * @param user ユーザー情報
     * @return 登録件数
     */
    @Transactional
    public void registerUser(LoginUser user) {
        if (loginUserMapper.registerUser(user) != 1 || loginUserMapper.registerUserRole(user) != 1) {
            throw new RuntimeException();
        }
    }
}

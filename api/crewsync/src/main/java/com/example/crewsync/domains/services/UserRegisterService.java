package com.example.crewsync.domains.services;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crewsync.common.utils.exceptions.DuplicateEmailException;
import com.example.crewsync.domains.mappers.LoginUserMapper;
import com.example.crewsync.security.LoginUser;

/**
 * ユーザー登録サービスクラスです
 */
@Service
public class UserRegisterService {

    @Autowired
    private final LoginUserMapper loginUserMapper;

    @Autowired
    private final MessageSource messageSource;

    public UserRegisterService(LoginUserMapper loginUserMapper, MessageSource messageSource) {
        this.loginUserMapper = loginUserMapper;
        this.messageSource = messageSource;
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

    /**
     * メールアドレスの重複チェックをします
     *
     * @param email メールアドレス
     * @throws EmailAlreadyExistsException メールアドレスが既に存在する場合
     */
    public void isDupplicateEmail(String email) {
        LoginUser user = loginUserMapper.identifyUser(email);
        if (user != null) {
            throw new DuplicateEmailException(
                    messageSource.getMessage("Error.DuplicateEmailException", null, Locale.JAPANESE));
        }
    }
}

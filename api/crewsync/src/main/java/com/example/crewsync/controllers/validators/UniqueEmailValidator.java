package com.example.crewsync.controllers.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.crewsync.controllers.validators.annotations.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * メールアドレス重複チェックのカスタムバリデーションです
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            userDetailsService.loadUserByUsername(value);
            return false;
        } catch (UsernameNotFoundException ex) {
            // UsernameNotFoundExceptionがスローされた場合バリデーション検査に引っかかったといえるのでtrueを返す
            return true;
        }
    }
}

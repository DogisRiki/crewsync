package com.example.crewsync.controllers.validators;

import java.util.Objects;

import com.example.crewsync.controllers.validators.annotations.ConfirmedPassword;
import com.example.crewsync.controllers.validators.interfaces.PasswordConfirmation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmedPasswordValidator implements ConstraintValidator<ConfirmedPassword, PasswordConfirmation> {

    @Override
    public boolean isValid(PasswordConfirmation value, ConstraintValidatorContext context) {
        if (Objects.equals(value.getPassword(), value.getPasswordConfirm())) {
            return true;
        } else {
            context.buildConstraintViolationWithTemplate("{Error.passwordConfirmed}")
                    .addPropertyNode("passwordConfirm")
                    .addConstraintViolation();
            return false;
        }
    }
}

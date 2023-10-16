package com.example.crewsync.controllers.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.crewsync.controllers.validators.ConfirmedPasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ConfirmedPasswordValidator.class)
public @interface ConfirmedPassword {

    String message() default "{Error.passwordConfirmed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

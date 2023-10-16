package com.example.crewsync.controllers.annotations;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Test
@WithUserDetails(userDetailsServiceBeanName = "loginUserDetailsService", value = "manager@crewsync.jp")
public @interface TestWithManager {
}

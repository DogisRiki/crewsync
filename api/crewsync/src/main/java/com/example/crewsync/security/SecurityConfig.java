package com.example.crewsync.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security設定クラスです
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private MessageSource messageSource;

    /**
     * SecurityFilterChainをDIコンテナに登録します
     *
     * @param http http HttpSecurityのインスタンス
     * @return SecurityFilterChain
     * @throws Exception Exception エラーが発生した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .failureHandler(new LoginAuthenticationFailureHandler("/login", messageSource))
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        // .requestMatchers("/").permitAll()
                        .anyRequest().authenticated());
        return http.build();
    }

    /**
     * PasswordEncoderをDIコンテナに登録します
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

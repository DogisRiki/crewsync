package com.example.crewsync.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * アプリケーション全体の設定クラスです。
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {

    /**
     * ロケール情報を解決するためのBeanを生成します
     * デフォルトのロケールを日本語に設定します
     *
     * @return ロケール情報を保持するオブジェクト
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.JAPANESE); // デフォルトのロケールを日本語に設定
        return sessionLocaleResolver;
    }

    /**
     * メッセージリソースを管理するためのBeanを生成します
     * メッセージリソースファイルのエンコーディングをUTF-8に設定します
     *
     * @return メッセージリソースを管理するオブジェクト
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages"); // messages.propertiesとmessages_ja.propertiesを読み込む
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * ロケール情報の変更を検知するインターセプタのBeanを生成します
     * リクエストパラメータ'lang'の変更を検知します
     *
     * @return ロケール情報の変更を検知するインターセプタ
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    /**
     * インターセプタを登録します
     * 生成したロケール情報の変更を検知するインターセプタを登録します
     *
     * @param registry インターセプタを管理するレジストリ
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

package com.example.crewsync.common.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * HTTPリクエストの共通処理です
 */
@ControllerAdvice
public class CommonAdvice {

    /**
     * HTTPリクエストの空文字をNULLに変換します
     */
    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}

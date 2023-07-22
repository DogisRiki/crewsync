package com.example.crewsync.common.utils.exceptions;

/**
 * メールアドレスの重複例外クラスです
 */
public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}

package com.example.crewsync.common.utils.exceptions;

/**
 * メールアドレスの重複例外クラスです
 */
public class DuplicateEmailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateEmailException(String message) {
        super(message);
    }
}

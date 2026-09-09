package com.callitaday.monsterhunter.exception;

/**
 * SQL 중복 예외
 */
public class DuplicatedException extends AddException {
    public DuplicatedException() {

    }

    public DuplicatedException(String message) {
        super(message);
    }
}

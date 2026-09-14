package com.callitaday.monsterhunter.exception;

/**
 * SQL 추가 예외
 */
public class AddException extends Exception {
    public  AddException() {
    }

    public AddException(String message) {
        super(message);
    }
}

package com.callitaday.monsterhunter.exception;

/**
 * SQL 수정 예외
 */
public class ModifyException extends Exception {
    public  ModifyException() {
    }

    public ModifyException(String message) {
        super(message);
    }
}

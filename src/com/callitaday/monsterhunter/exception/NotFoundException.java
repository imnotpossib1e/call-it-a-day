package com.callitaday.monsterhunter.exception;

/**
 * SQL 검색 결과 없음 예외
 */
public class NotFoundException extends Exception {
    public NotFoundException(){

    }
    public NotFoundException(String message) {
        super(message);
    }
}

package com.callitaday.monsterhunter.dto;


public class UserDto {

    private int userId;      // 유저ID (PK) userNo 는 어떨지...
    private String id;       // 아이디 (VARCHAR(20))
    private int password;    // 비밀번호

    // 기본 생성자
    public UserDto() {
    }

    // 회원가입 시 사용할 생성자 (userId는 DB가 자동 생성하는 경우가 많아서 제외)
    public UserDto(String id, int password) {
        this.id = id;
        this.password = password;
    }

    // 전체 필드를 받는 생성자
    public UserDto(int userId, String id, int password) {
        this.userId = userId;
        this.id = id;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", id='" + id + '\'' +
                ", password=" + password +
                '}';
    }
}
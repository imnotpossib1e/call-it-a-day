package com.callitaday.monsterhunter.dto;

public class UserDto {

	private int userId; // 유저 번호 PK, increment
	private String id; // 아이디 (VARCHAR(10))
	private int password; // 비밀번호 숫자 4자리

	// 기본 생성자
	public UserDto() {
	}

	// 회원가입 시 사용할 생성자
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
		return "UserDto[" + "userId=" + userId + ", id=" + id + ", password=" + password + "]";
	}
}
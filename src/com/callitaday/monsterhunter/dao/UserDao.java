package com.callitaday.monsterhunter.dao;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.exception.AddException;

public interface UserDao {

	// 회원가입 추상클래스
	int insertUser(UserDto userDto) throws SQLException, AddException;

	// 로그인 추상클래스
	UserDto login(String id, int password) throws SQLException;

	// 아이디 중복 체크 (없으면 null) 추상클래스
	UserDto selectById(String id) throws SQLException;

}
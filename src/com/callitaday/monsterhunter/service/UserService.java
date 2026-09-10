package com.callitaday.monsterhunter.service;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.NotFoundException;

public interface UserService {

	// ----- 회원가입 -----
	int insertUser(UserDto userDto) throws SQLException, AddException;

	// ----- 로그인 -----
	UserDto login(String id, int password) throws SQLException, NotFoundException;

	// 아이디 중복 체크 (없으면 null)
	UserDto selectById(String id) throws SQLException;
}

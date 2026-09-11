package com.callitaday.monsterhunter.controller;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.UserServiceImpl;
import com.callitaday.monsterhunter.view.EndView;
import com.callitaday.monsterhunter.view.FailView;

public class UserController {
	private static UserServiceImpl userService = new UserServiceImpl();

	// ----- 회원가입 -----
	public static void insertUser(UserDto userDto) {
		try {
			userService.insertUser(userDto);
			EndView.printMessage("회원가입이 완료 되었습니다.");
		} catch (SQLException e) { // DB 오류발생
			FailView.errorMessage("DB 처리 오류 : " + e.getMessage());
		} catch (AddException a) {
			FailView.errorMessage("회원가입 등록 오류 : " + a.getMessage());
		}

	}

	public static UserDto login(String id, int password) {
		UserDto userDto = null;
		try {
			userDto = userService.login(id, password);
			EndView.printMessage("전장에 입장하였습니다.");
		} catch (SQLException e) {
			FailView.errorMessage("DB 처리 오류 : " + e.getMessage());
		} catch (NotFoundException n) {
			FailView.errorMessage("로그인 정보 오류 : " + n.getMessage());
		}
		return userDto;

	}

	public static UserDto selectById(String id) {
		try {
			return userService.selectById(id);
		} catch (SQLException e) {
			System.out.println("DB 처리 오류: " + e.getMessage());
			return null;
		}

	}
}

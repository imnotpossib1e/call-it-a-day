package com.callitaday.monsterhunter.service;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dao.UserDao;
import com.callitaday.monsterhunter.dao.UserDaoImpl;
import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.session.SessionSet;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();

//----- 회원가입 -----
	@Override
	public int insertUser(UserDto userDto) throws SQLException, AddException {
		// 중복이 아니면 저장
		int result = userDao.insertUser(userDto);
		// result가 0이라는건 = 무슨 이유에서인지 저장이 안 됐다는 뜻
		if (result == 0) {
			throw new SQLException("회원가입에 실패하였습니다.");
		}
		return result;
	}

//----- 로그인 -----
	public UserDto login(String id, int password) throws SQLException, NotFoundException {
		UserDto userDto = userDao.login(id, password);
		if (userDto == null) {
			throw new NotFoundException(" 비밀번호가 일치하지 않습니다.");
		}

		SessionSet sessionSet = SessionSet.getInstance();// 세션셋 얻어오고
		sessionSet.setList(userDto); // 인증된사용자를 SessionSet에 저장한다.
		return userDto;
	}

//----- 아이디 중복체크 -----	
	@Override
	public UserDto selectById(String id) throws SQLException {
		return userDao.selectById(id);

	}

}

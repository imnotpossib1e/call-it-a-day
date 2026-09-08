package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.UserDto;

public interface UserDao {

    // 회원가입
    int insertUser(UserDto userDto);

    // 로그인
    UserDto login(String id, int password);
}
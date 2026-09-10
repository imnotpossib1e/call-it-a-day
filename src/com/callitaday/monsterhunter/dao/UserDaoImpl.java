package com.callitaday.monsterhunter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.util.DbManager;

// UserDao 를 구현한 클래스
public class UserDaoImpl implements UserDao {

	// ---------- 회원가입 메소드----------
	@Override
	public int insertUser(UserDto userDto) throws SQLException, AddException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		Properties queryProFile = DbManager.getQueryProFile();
		String sql = queryProFile.getProperty("joinQuery");

		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, userDto.getId());
			ps.setInt(2, userDto.getPassword());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AddException(" -- 회원가입 중 오류가 발생했습니다. -- ");
		} finally {
			DbManager.close(con, ps, null);
		}
		return result;
	}

	// ---------- 로그인 메소드----------
	@Override
	public UserDto login(String id, int password) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDto userDto = null;

		Properties queryProFile = DbManager.getQueryProFile();
		String sql = queryProFile.getProperty("loginQuery");
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				userDto = new UserDto(rs.getInt("user_id"), rs.getString("id"), rs.getInt("password"));
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return userDto;
	}

	// ---------- 아이디 중복체크 메소드----------
	@Override
	public UserDto selectById(String id) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDto userDto = null;

		Properties queryProFile = DbManager.getQueryProFile();
		String sql = queryProFile.getProperty("checkQuery");

		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				userDto = new UserDto(rs.getInt("user_id"), rs.getString("id"), rs.getInt("password"));
			}
		} finally {
			DbManager.close(con, ps, rs);
		}
		return userDto;
	}
}

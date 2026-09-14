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
	CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
	Properties pro = DbManager.getQueryProfile();

	// ---------- 회원가입 메소드----------
	@Override
	public int insertUser(UserDto userDto) throws SQLException, AddException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;

		String sql = pro.getProperty("joinQuery");

		try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, userDto.getId());
			ps.setInt(2, userDto.getPassword());

			result = ps.executeUpdate();
			if (result == 0) {
				con.rollback();
				throw new AddException("유저 생성에 실패했습니다.");
			}

			rs = ps.getGeneratedKeys();
			int userId = -1;
			if(rs.next()) {
				userId = rs.getInt(1);
			}

			// user_insert 성공 시 character_info insert 실행
			int re = characterInfoDao.insertCharacterInfo(con, userId);
			if(re == 0){
				con.rollback();
				throw new AddException("캐릭터 정보 생성에 실패했습니다.");
			}
			con.commit();
		}finally {
			DbManager.dbClose(con, ps);
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

		String sql = pro.getProperty("loginQuery");
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, password);

			rs = ps.executeQuery();

			if (rs.next()) {
				userDto = new UserDto(rs.getInt("user_id"), rs.getString("id"));
			}
		} finally {
			DbManager.dbClose(con, ps, rs);
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

		String sql = pro.getProperty("checkQuery");

		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			if (rs.next()) {
				userDto = new UserDto(rs.getInt("user_id"), rs.getString("id"), rs.getInt("password"));
			}
		} finally {
			DbManager.dbClose(con, ps, rs);
		}
		return userDto;
	}
}

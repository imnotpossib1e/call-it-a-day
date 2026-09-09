package com.callitaday.monsterhunter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.util.DbManager;

public class StageDaoImpl implements StageDao {

	@Override
	public StageDto enemyInfoForFight(int stageId) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from stage where stage_id = ?";
		StageDto st = new StageDto();
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, stageId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				st = new StageDto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5));
				st.setItemdto(getRewardItem(con, rs.getInt(6)));
			}
		} finally {
			DbManager.dbClose(null, ps, null);
		}
		
		return st;
	}

	@Override
	public ItemDto getRewardItem(Connection con, int itemId) throws SQLException {
		// TODO Auto-generated method stub
		con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select item_id, item_name from item where stage_id = ?";
		ItemDto idto = new ItemDto();
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				idto = new ItemDto(rs.getInt(1), rs.getString(2));
			}
		} finally {
			DbManager.dbClose(null, ps, null);
		}
		
		return idto;
	}

	@Override
	public int useItem(int userId, int itemId) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update inventory "
				+ "set quantity = quantity - 1 "
				+ "where user_id = ? and item_id = ? "
				+ "and quantity > 0";
		
		int result = 0;
		
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userId);
			ps.setInt(2, itemId);
			
			result = ps.executeUpdate();
			
		} finally {
			DbManager.dbClose(con, ps);
		}

		return result;
	}

}

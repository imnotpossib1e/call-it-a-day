package com.callitaday.monsterhunter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.util.DbManager;

public class StageDaoImpl implements StageDao {

	/**
	 * 전투를 위한 적 정보 조회
	 */
	@Override
	public StageDto enemyInfoForFight(int stageId) throws SQLException {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select * from stage where stage_id = ?";
		StageDto st = null;
		try {
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, stageId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				st = new StageDto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
						rs.getInt(5));
				
				ItemDto badge = getRewardItem(con, 100 + stageId);

				if (badge == null) {
				    throw new SQLException("스테이지 증표를 찾을 수 없습니다.");
				}

				st.setItemdto(badge);
			}
		} finally {
			DbManager.dbClose(con, ps, rs);
		}
		
		return st;
	}

	/**
	 * 보상 증표 정보 조회
	 */
	@Override
	public ItemDto getRewardItem(Connection con, int itemId) throws SQLException {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select item_id, item_name from item where item_id = ? and item_type = '증표'";
		ItemDto idto = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemId);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				idto = new ItemDto(rs.getInt(1), rs.getString(2));
			}
		} finally {
			DbManager.dbClose(null, ps, rs);
		}
		
		return idto;
	}

	/**
	 * 아이템 사용
	 */
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
        	DbManager.dbClose(con, ps, null);
        }
        
        return result;
	}
	
	/**
	 * 스테이지 클리어 여부 확인
	 */
	 @Override
	 public boolean isStageCleared(int userId, int stageId) throws SQLException {

	        Connection con = null;

	        try {
	            con = DbManager.getConnection();

	            return isStageCleared(con, userId, stageId);

	        } finally {
	        	DbManager.dbClose(con, null, null);
	        }
	    }
	 /**
	  * 증표 보유 여부로 스테이지 클리어 여부 확인
	  */
	 private boolean isStageCleared(Connection con, int userId, int stageId) throws SQLException {

	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        
	        int badge = 100 + stageId;
	        

	        String sql = "select 1 "
	                + "from inventory v "
	                + "join item i on v.item_id = i.item_id "
	                + "where v.user_id = ? "
	                + "and v.item_id = ? "
	                + "and i.item_type = '증표' "
	                + "and v.quantity > 0";

	        try {
	            ps = con.prepareStatement(sql);
	            ps.setInt(1, userId);
	            ps.setInt(2, badge);

	            rs = ps.executeQuery();

	            return rs.next();

	        } finally {
	        	DbManager.dbClose(null, ps, rs);
	        }
	    }

	 /**
	  * 스테이지 클리어 저장
	  */
	 @Override
	 public int saveBattle(CharactorInfoDto character) throws SQLException {
		 Connection con = null;
		 PreparedStatement ps = null;
		 String sql = "update charactor_info "
		            + "set hp = ?, mp = ?, stage_id = ?, coin = ? "
		            + "where user_id = ?";
		 
		 int result = 0;
	        try {
	            con = DbManager.getConnection();
	            ps = con.prepareStatement(sql);
	            
	            ps.setInt(1, character.getHp());
	            ps.setInt(2, character.getMp());
	            ps.setInt(3, character.getStage_id());
	            ps.setInt(4, character.getCoin());
	            ps.setInt(5, character.getUserId());
	            	            
	            result = ps.executeUpdate();
	            
	        } finally {
	        	DbManager.dbClose(con, ps);
	        }
	        
	        return result;
	    }
	 
	 @Override
	 public int addRewardItem(int userId, int stageId) throws SQLException{
		    Connection con = null;
		    PreparedStatement ps = null;

		    int badge = 100 + stageId;

		    String sql = "insert into inventory "
		    		+ "(user_id, quantity, is_equipped, item_id) "
		            + "values (?, 1, 'F', ?)";

		    int result = 0;
		    
		    try{
		        con = DbManager.getConnection();
		        ps = con.prepareStatement(sql);

		        ps.setInt(1, userId);
		        ps.setInt(2, badge);

		        result = ps.executeUpdate();
		        
		    } finally {
		    	DbManager.dbClose(con, ps, null);
		    }
		    
		    return result;
	 }
 
}

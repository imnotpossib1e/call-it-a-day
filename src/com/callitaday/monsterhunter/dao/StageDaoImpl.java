package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.util.DbManager;
import java.util.ArrayList;
import java.util.List;

public class StageDaoImpl implements StageDao {
	private final CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
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
						rs.getInt(5), rs.getInt(6));
				
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
	 public int saveBattle(CharacterInfoDto character, boolean victory) throws SQLException {
		 Connection con = null;
		 PreparedStatement ps = null;
		 String sql = "update character_info "
		            + "set hp = ?, mp = ?, stage_id = ? "
		            + "where user_id = ?";

		 int result = 0;
		 int stageClear=0;
		 if(victory){
			 // 승리한 경우 stage+1
			 stageClear=1;
		 }

		 try {
			con = DbManager.getConnection();
			con.setAutoCommit(false);

			ps = con.prepareStatement(sql);

			ps.setInt(1, character.getHp());
			ps.setInt(2, character.getMp());
			ps.setInt(3, character.getStage_id()+stageClear);
			ps.setInt(4, character.getUserId());

			result = ps.executeUpdate();
			if(result == 0){
				con.rollback();
				throw new SQLException("전투 결과 저장에 실패했습니다.");
			}
			// 승리한 경우 ATK, DEF 수치 변경
			if(victory){
				int re = characterInfoDao.updateCharacterByUserId(con, character.getUserId());

				if(re == 0){
					con.rollback();
					throw new SQLException();
				}
			}

		 } finally {
		 	DbManager.dbClose(con, ps);
		 }
		 return result;
	 }


	/**
	 * 스테이지 클리어 보상
	 *
	 * 1. 코인, 증표
	 * @param userId
	 * @param stageId
	 * @return
	 * @throws SQLException
	 */
	@Override
	 public int addRewardItem(int userId, int stageId) throws SQLException, NotFoundException, AddException, ModifyException{
		    Connection con = null;
		    PreparedStatement ps = null;

		    int badge = 100 + stageId;
			// 보상 코인 난수 지정
//			int coin = 100 * ((int)(Math.random() * 9) +1);
			int coin = 100*stageId;


		// 증표
		    String sql = "insert into inventory "
		    		+ "(user_id, quantity, is_equipped, item_id) "
		            + "values (?, 1, 'F', ?)";

		    int result = 0;

		    try{
		        con = DbManager.getConnection();
				con.setAutoCommit(false);

				ps = con.prepareStatement(sql);
		        ps.setInt(1, userId);
		        ps.setInt(2, badge);

				// 리워드 코인 불러오기
				int rewardCoin = this.enemyInfoForFight(stageId).getRewardCoin();


				// 증표 인벤토리에 추가
		        result = ps.executeUpdate();

				// 증표 인벤토리 추가 실패
				if(result == 0){
					con.rollback();
					throw new AddException("증표 증정에 실패했습니다.");
				}else{
					// 코인 추가 로직 구현
					int re = this.addCoin(con, userId, rewardCoin);
					if(re == 0){
						con.rollback();
						throw new ModifyException("코인 증정에 실패했습니다.");
					}
				}
				con.commit();
		    }
			finally {
		    	DbManager.dbClose(con, ps);
		    }
		    
		    return result;
	 }

	/**
	 * 전체 스테이지 조회
	 */
	@Override
	public List<StageDto> selectAllStage() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "select * from stage";

		List<StageDto> list = new ArrayList<StageDto>();

		try{
			con = DbManager.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while(rs.next()){
				list.add(new StageDto(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
				                      rs.getInt(5), rs.getInt(6)));
				ItemDto badge = getRewardItem(con, 100 + rs.getInt(1));
			}
		}finally {
			DbManager.dbClose(con, ps, rs);
		}

		return list;
	}

	/**
	 * 코인 추가 로직
	 */
	/**
	 * 코인 추가 로직
	 *
	 * @param userId
	 * @param coin
	 */
	@Override
	public int addCoin(Connection con, int userId, int coin) throws  SQLException {
		PreparedStatement ps = null;

		String sql = "UPDATE character_info set coin= coin+? where user_id = ?";
		int result = 0;
		try{
			ps = con.prepareStatement(sql);
			ps.setInt(1, coin);
			ps.setInt(2, userId);
			result = ps.executeUpdate();

		}finally {
			DbManager.dbClose(null, ps);
		}
		return result;
	}
}

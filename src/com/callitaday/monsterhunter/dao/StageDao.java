package com.callitaday.monsterhunter.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;

public interface StageDao {
	
	/**
	 * 전투에 쓰일 적 정보 가져오기
	 */
	public StageDto enemyInfoForFight (int stageId) throws SQLException;
	
	/**
	 * 보상 아이템 조회
	 */
	public ItemDto getRewardItem (Connection con, int itemId) throws SQLException;
	
	/**
	 * 전투에서 아이템 사용
	 */
	public int useItem (int userId, int itemId) throws SQLException;
}

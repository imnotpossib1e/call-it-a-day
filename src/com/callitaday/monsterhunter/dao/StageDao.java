package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import java.util.List;

public interface StageDao {
	
	/**
	 * 전투에 쓰일 적 정보 가져오기
	 */
	StageDto enemyInfoForFight(int stageId) throws SQLException;
	
	/**
	 * 보상 아이템 조회
	 */
	ItemDto getRewardItem(Connection con, int itemId) throws SQLException;
	
	/**
	 * 전투에서 아이템 사용
	 */
	int useItem (int userId, int itemId) throws SQLException;
	
	/**
	 * 스테이지 클리어 여부 확인
	 */
	boolean isStageCleared(int userId, int stageId) throws SQLException;
	
	/**
	 * 스테이지 클리어 저장
	 */
	int saveBattle(CharacterInfoDto user) throws SQLException;
	
	/**
	 * 클리어 보상 증표 지급
	 */
	int addRewardItem(int userId, int stageId) throws SQLException, NotFoundException, AddException, ModifyException;

	/**
	 * 전체 스테이지 조회
	 */
	List<StageDto> selectAllStage() throws SQLException;

	/**
	 * 코인 추가 로직
	 */
	int addCoin(Connection con, int userId, int coin) throws ModifyException, SQLException;
}

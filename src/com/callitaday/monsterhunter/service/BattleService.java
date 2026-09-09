package com.callitaday.monsterhunter.service;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.StageDto;

public interface BattleService {
	/**
	 * 공격하기
	 */
	int attack() throws SQLException;
	
	/**
	 * 방어하기
	 */
	int defend() throws SQLException;
	
	/**
	 * 아이템 사용
	 */
	int item() throws SQLException;
	
	/**
	 * 스테이지 선택
	 */
	int sellectStage() throws SQLException;
	
	/**
	 * 스테이지별 클이어 여부 체크
	 */
	boolean stageCleared() throws SQLException;
	
	/**
	 * 전투 종료 후 획득 재화 조정
	 */
	StageDto randomGetReward() throws SQLException; 
	
}

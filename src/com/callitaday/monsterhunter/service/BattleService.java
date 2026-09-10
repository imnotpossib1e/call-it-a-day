package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.StageDto;

public interface BattleService {
	
	/**
	 * 전투 시작
	 */
	public StageDto startBattle(int userId) throws SQLException;
	
	/**
	 * 전투 주사위 생성
	 */
	int randomDice();
	
	/**
	 * 공격하기 (유저)
	 */
	int userAttack(int userId) throws SQLException;
	
	/**
	 * 공격하기 (적)
	 */
	int enemyAttack(int userId) throws SQLException;
	
	/**
	 * 방어하기 (유저)
	 */
	int userDefend(int userId) throws SQLException;
	
	/**
	 * 방어하기 (적)
	 */
	int enemyDefend(int userId) throws SQLException;

	/**
	 * 아이템 사용
	 */
	int userItem(int userId) throws SQLException;
	
	/**
	 * 스테이지 클리어 저장
	 */
	public void saveBattle(int userId) throws SQLException;
}

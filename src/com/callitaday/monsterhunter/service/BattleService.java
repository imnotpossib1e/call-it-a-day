package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dto.DefendDto;
import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;

public interface BattleService {
	
	/**
	 * 유저의 현재 stage_id로 전투 시작
	 */
	StageDto startBattle(int userId) throws SQLException;
	
	/**
	 * 스테이지를 직접 선택해서 전투 시작
	 */
	StageDto startBattle(int userId, int stageId) throws SQLException;
	
	/**
	 * 전투 중 객체 조회
	 */
	CharacterInfoDto getBattleUser(int userId) throws SQLException;
	
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
	DefendDto userDefend(int userId) throws SQLException;

	/**
	 * 아이템 사용
	 */
	CharacterInfoDto useItem(int userId, int itemId) throws SQLException;
	
	/**
	 * 스테이지 클리어 저장
	 */
	int saveBattle(int userId) throws SQLException, NotFoundException, AddException, ModifyException;
}

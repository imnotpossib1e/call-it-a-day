package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dao.CharactorInfoDao;
import com.callitaday.monsterhunter.dao.CharactorInfoDaoImpl;
import com.callitaday.monsterhunter.dao.StageDao;
import com.callitaday.monsterhunter.dao.StageDaoImpl;
import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.StageDto;

public class BattleServiceImpl implements BattleService{
	StageDao stageDao = new StageDaoImpl();
	CharactorInfoDao charactorInfoDao = new CharactorInfoDaoImpl();	
	private static BattleService instance = new BattleServiceImpl();
	
    public static BattleService getInstance(){
        return instance;
    }
	
	/**
	 * 유저와 적 데이터를 불러와 전투를 시작
	 */
	@Override
	public StageDto startBattle(int userId) throws SQLException {
		// TODO Auto-generated method stub
		StageDto enemyInfoForFight = charactorInfoDao.getCharactorByUserId(userId).getStageDto();
		
		return enemyInfoForFight;
	}

	/**
	 * 랜덤 다이스 생성
	 */
	@Override
	public int randomDice() {
		// TODO Auto-generated method stub
		int num = (int)(Math.random() * 10);
		return num;
	}

	/**
	 * 유저의 공격
	 */
	@Override
	public int userAttack(int userId) throws SQLException {
		// TODO Auto-generated method stub
		int userDice = randomDice();
		int enemyDice = randomDice();
		CharactorInfoDto user = charactorInfoDao.getCharactorByUserId(userId);
		int result = 0;
		
		if (userDice > enemyDice) {
			result = (user.getAtk() - startBattle(userId).getEnemyDef());
			
			if(user.getMp() >= userDice) {
				user.setMp(user.getMp() - userDice);
			} else {
				user.setHp(user.getHp() - userDice);
			}
		}

		return result;
	}

	/**
	 * 적의 공격
	 */
	@Override
	public int enemyAttack(int userId) throws SQLException {
		// TODO Auto-generated method stub
		int userDice = randomDice();
		int enemyDice = randomDice();
		int result = 0;
		
		CharactorInfoDto user = charactorInfoDao.getCharactorByUserId(userId);
		StageDto enemy = user.getStageDto();
		
		if (enemyDice > userDice ) {
			result = (enemy.getEnemyAtk() - user.getDef());
		}

		return result;
	}
	
	/**
	 * 유저의 방어
	 */
	@Override
	public int userDefend(int userId) throws SQLException {
		// TODO Auto-generated method stub
		int userDice = randomDice();
		int enemyDice = randomDice();
		int result = 0;
		
		CharactorInfoDto user = charactorInfoDao.getCharactorByUserId(userId);
		StageDto enemy = user.getStageDto();
		
		if (enemyDice > userDice) {
			result = (enemy.getEnemyAtk() - user.getDef());
		} else if (userDice == 10) {
			enemy.setEnemyHp(enemy.getEnemyHp() - (userDice - enemyDice) * 10);
		}

		return result;

	}
	
	/**
	 * 방어하기 (적)
	 */
	@Override
	public int enemyDefend(int userId) throws SQLException{
		// TODO Auto-generated method stub
		int userDice = randomDice();
		int enemyDice = randomDice();
		int result = 0;
		CharactorInfoDto user = charactorInfoDao.getCharactorByUserId(userId);
		StageDto enemy = user.getStageDto();
		
		if (userDice > enemyDice) {
			result = (user.getAtk() - enemy.getEnemyDef());
		} 
		
		return result;
	}

	@Override
	public int userItem(int userId, List<InventoryDto> itemList) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 스테이지 클리어 저장
	 */
	@Override
	public void saveBattle(int userId) throws SQLException{
		
	}
	
}

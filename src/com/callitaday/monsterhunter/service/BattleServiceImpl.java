package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dao.CharacterInfoDao;
import com.callitaday.monsterhunter.dao.CharacterInfoDaoImpl;

import com.callitaday.monsterhunter.dao.StageDao;
import com.callitaday.monsterhunter.dao.StageDaoImpl;

import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;

public class BattleServiceImpl implements BattleService{
	StageDao stageDao = new StageDaoImpl();
	CharacterInfoDao charactorInfoDao = new CharacterInfoDaoImpl();	
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
		StageDto enemyInfoForFight = charactorInfoDao.getCharacterByUserId(userId).getStageDto();
		
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
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
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
		
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
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
		
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
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
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
		StageDto enemy = user.getStageDto();
		
		if (userDice > enemyDice) {
			result = (user.getAtk() - enemy.getEnemyDef());
		} 
		
		return result;
	}

	/**
	 * 포션 아이템 사용
	 */
	@Override
	public int useItem(int userId) throws SQLException {
		// TODO Auto-generated method stub
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
		List<InventoryDto> invenList = user.getInvenlist();
		int result = 0;
		
		for(InventoryDto invenItem : invenList) {
			String ItemType = invenItem.getItemDto().getItemType();
			
			if(ItemType.equals("회복포션") || ItemType.equals("마나포션")) {
				result = invenItem.getItemDto().getItemIncrease();
			}
		}
		
		return result;
	}
	
	/**
	 * 스테이지 클리어 저장
	 */
	@Override
	public int saveBattle(int userId) throws SQLException, NotFoundException, AddException, ModifyException {
		CharacterInfoDto user = charactorInfoDao.getCharacterByUserId(userId);
		int result = stageDao.addRewardItem(userId, user.getStage_id());
		if(result == 0) {
			throw new SQLException("클리어하지 못했습니다.");
			
		} else {
			result = stageDao.saveBattle(user);
		}
				
		return result;
	}
	
}

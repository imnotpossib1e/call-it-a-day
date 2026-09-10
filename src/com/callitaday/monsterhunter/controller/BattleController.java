package com.callitaday.monsterhunter.controller;

import java.sql.SQLException;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.BattleService;
import com.callitaday.monsterhunter.service.BattleServiceImpl;
import com.callitaday.monsterhunter.service.CharacterInfoService;
import com.callitaday.monsterhunter.service.CharacterInfoServiceImpl;
import com.callitaday.monsterhunter.view.FailView;

public class BattleController {
	public static BattleService battleService = BattleServiceImpl.getInstance();
    public static CharacterInfoService characterInfoService = CharacterInfoServiceImpl.getInstance();
    
    
    public static void attack(int userId) {
    	try {
    		int userAtk = battleService.userAttack(userId);
    		CharacterInfoDto user = characterInfoService.selectCharInfoByUserId(userId);
    		int enemyHp = user.getStageDto().getEnemyHp();
    		
    		int result = enemyHp - userAtk;
    		
    		characterInfoService.selectCharInfoByUserId(userId).getStageDto().setEnemyHp(result);
    		
    		enemyHp = characterInfoService.selectCharInfoByUserId(userId).getStageDto().getEnemyHp();
    		
    		if(enemyHp <= 0) {
    			throw new SQLException("스테이지를 클리어하였습니다.");
    		} else {
    			battleService.enemyAttack(userId);
    			if(user.getHp() <= 0) {
    				throw new SQLException("패배하였습니다.");
    			}
    		}  		
    		
    	} catch (SQLException | NotFoundException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    }
    
    public static void defend(int userId) {
    	try {
    		int userDefendResult = battleService.userDefend(userId);
    		CharacterInfoDto user = characterInfoService.selectCharInfoByUserId(userId);
    		int userHp = user.getHp() - userDefendResult;
    		user.setHp(userHp);
    		
    		if(user.getHp() <= 0) {
    			throw new SQLException("패배하였습니다.");
    		}
    		
    	} catch(SQLException | NotFoundException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    	
    }
    
    public static void useItem(int userId) {
    	try {
			CharacterInfoDto user = characterInfoService.selectCharInfoByUserId(userId);
			int itemFigure = battleService.useItem(userId);
		} catch (NotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

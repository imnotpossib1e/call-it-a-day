package com.callitaday.monsterhunter.controller;

import java.sql.SQLException;

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
    		int enemyHp = characterInfoService.selectCharInfoByUserId(userId).getStageDto().getEnemyHp();
    		int userAtk = battleService.userAttack(userId);
    		
    		int result = enemyHp - userAtk;
    		
    	} catch (SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} catch (NotFoundException e) {
			// TODO Auto-generated catch block
    		FailView.errorMessage(e.getMessage());
		}
    }
}

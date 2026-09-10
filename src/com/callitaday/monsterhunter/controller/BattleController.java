package com.callitaday.monsterhunter.controller;

import java.sql.SQLException;
import java.util.Scanner;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.BattleService;
import com.callitaday.monsterhunter.service.BattleServiceImpl;
import com.callitaday.monsterhunter.service.CharacterInfoService;
import com.callitaday.monsterhunter.service.CharacterInfoServiceImpl;
import com.callitaday.monsterhunter.view.FailView;
import com.callitaday.monsterhunter.view.MenuView;

public class BattleController {
	public static final BattleService battleService = BattleServiceImpl.getInstance();
    
	/**
	 * 전투화면 진입
	 */
	public static void openBattle(int userId, Scanner sc) {
		try {
			MenuView.runBattle(userId, sc);
		} catch (Exception e) {
			FailView.errorMessage("전투 화면 지속이 불가능합니다.");
		}
	}
	
    public static boolean start(int userId) {
        try {
            battleService.startBattle(userId);
            return true;

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return false;
        }
    }
    
    public static boolean start(int userId, int stageId) {
        try {
            battleService.startBattle(userId, stageId);
            return true;

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return false;
        }
    }
    
    public static void attack(int userId) {
    	try {
    		CharacterInfoDto user = battleService.getBattleUser(userId);
    		
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
            }
    		
    		battleService.userAttack(userId);
    		
    		if (user.getHp() > 0 && user.getStageDto().getEnemyHp() > 0) {
                battleService.enemyAttack(userId);
            }
    	} catch (SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    }
    
    public static void defend(int userId) {
    	try {
    		CharacterInfoDto user = battleService.getBattleUser(userId);
    		
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
            }
    		
    		battleService.userDefend(userId);
    		
    	} catch(SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    	
    }
    
    public static void useItem(int userId, String input) {
    	try {
    		int itemId = Integer.parseInt(input.trim());
    		CharacterInfoDto user = battleService.getBattleUser(userId);
    		
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
    		}
    		
    		battleService.useItem(userId, itemId);
    		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			FailView.errorMessage(e.getMessage());
		}
    }
    
    public static CharacterInfoDto getState(int userId) {
        try {
            return battleService.getBattleUser(userId);

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return null;
        }
    }
    
    public static boolean save(int userId) {
        try {
            battleService.saveBattle(userId);
            return true;

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return false;
        }
    }
}

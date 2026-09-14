package com.callitaday.monsterhunter.controller;

import com.callitaday.monsterhunter.service.InventoryService;
import com.callitaday.monsterhunter.service.InventoryServiceImpl;
import com.callitaday.monsterhunter.view.BattleView;
import com.callitaday.monsterhunter.view.EndView;
import java.sql.SQLException;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.DefendDto;
import com.callitaday.monsterhunter.service.BattleService;
import com.callitaday.monsterhunter.service.BattleServiceImpl;
import com.callitaday.monsterhunter.view.FailView;
import java.util.Scanner;



public class BattleController {

	public static final BattleService battleService = BattleServiceImpl.getInstance();
    public static final InventoryService inventoryService = InventoryServiceImpl.getInstance();

	/**
	 * 전투화면 진입
	 */
	public static void openBattle(int userId, Scanner sc) {
		try {
			BattleView.runBattle(userId, sc);
		} catch (Exception e) {
			FailView.errorMessage("전투 화면 지속이 불가능합니다.");
		}
	}

    /**
     * 전투 시작
     * @param userId
     * @return
     */
    public static boolean start(int userId) {
        try {
            // 유저의 현재 아이디로 전투 시작
            battleService.startBattle(userId);
            return true;

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return false;
        }
    }

    /**
     * 스테이지 선택해서 전투 시작
     * @param userId
     * @param stageId
     * @return
     */
    public static boolean start(int userId, int stageId) {
        try {
            battleService.startBattle(userId, stageId);
            return true;

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return false;
        }
    }

    /**
     * 공격
     * @param userId
     */
    public static void attack(int userId) {
    	try {
            // 전투중인 유저 받아오기
    		CharacterInfoDto user = battleService.getBattleUser(userId);

            // 유저의 체력이 0 이하이거나 적의 체력이 0 이하일 경우 종료?
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
            }


            // 유저의 공격
    		int myDamage = battleService.userAttack(userId);
    		BattleView.displayMyAttack(myDamage);

            timeDelay(300);

            // 유저의 체력이 0 이상이거나 적의 체력이 0 이상인 경우
    		if (user.getHp() > 0 && user.getStageDto().getEnemyHp() > 0) {
                // 적의 공격
                int enemyDamage = battleService.enemyAttack(userId);
                BattleView.displayEnemyAttack(enemyDamage);
                timeDelay(300);
            }

    	} catch (SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    }

    private static void timeDelay(int time){
        try {
            Thread.sleep(time); // 1.0초 동안 지연
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 방어
     * @param userId
     */
    public static void defend(int userId) {
    	try {
    		CharacterInfoDto user = battleService.getBattleUser(userId);

            // 죽었을경우 리턴
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
            }

             DefendDto defendDto = battleService.userDefend(userId);
             if(defendDto.isResult()){ // 방어에 성공한 경우
                 BattleView.displayMyDefenceSuccess(defendDto.getDamage());
             }else{
                 BattleView.displayMyDefenceFail(defendDto.getDamage());
             }
            timeDelay(300);

    	} catch(SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    	
    }

    /**
     * 아이템 사용
     * @param userId
     * @param input
     */
    public static boolean useItem(int userId, String input) {
    	try {
    		CharacterInfoDto user = battleService.getBattleUser(userId);
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return false;
    		}
            int itemId = Integer.parseInt(input);
    		battleService.useItem(userId, itemId);
    		return true;
		}catch (NumberFormatException e){
            FailView.errorMessage("⚠ 아이템에 해당하는 번호를 입력해 주세요.");
            return false;
        }
        catch (SQLException  e) {
			FailView.errorMessage(e.getMessage());
            return false;
		}
    }

    /**
     * 전투중인 유저 반환
     * @param userId
     * @return
     */
    public static CharacterInfoDto getState(int userId) {
        try {
            return battleService.getBattleUser(userId);

        } catch (Exception e) {
            FailView.errorMessage(e.getMessage());
            return null;
        }
    }

    /**
     * 전투 결과 저장
     * @param userId
     * @return
     */
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

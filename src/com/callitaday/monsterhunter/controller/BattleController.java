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

            EndView.printMessage("공격합니다.");

            timeDelay();

            // 유저의 공격
    		int myDamage = battleService.userAttack(userId);
    		BattleView.attackResult("상대 HP -"+myDamage);

            timeDelay();

            EndView.printMessage("상대의 공격 차례입니다.");
            timeDelay();

            // 유저의 체력이 0 이상이거나 적의 체력이 0 이상인 경우
    		if (user.getHp() > 0 && user.getStageDto().getEnemyHp() > 0) {
                // 적의 공격
                int enemyDamage = battleService.enemyAttack(userId);
                BattleView.attackResult("내 HP -"+enemyDamage);
                timeDelay();
            }

    	} catch (SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    }

    private static void timeDelay(){
        try {
            Thread.sleep(200); // 1.0초 동안 지연
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
            EndView.printMessage("적의 공격을 방어합니다.");
            timeDelay();
            // 방어 서비스 호출
            EndView.printMessage("적의 턴입니다.");


             DefendDto defendDto = battleService.userDefend(userId);
             if(defendDto.isResult()){ // 방어에 성공한 경우
                 EndView.printMessage("방어에 성공하셨습니다. -> 데미지 반사");
                 EndView.attackResult("상대 HP -"+ defendDto.getDamage());
             }else{
                 EndView.printMessage("방어에 실패했습니다.");
                 EndView.attackResult("내 HP -" + defendDto.getDamage());
             }




            // Todo 방어 뷰 작성 (방어에성공하셨습니다)

    	} catch(SQLException e) {
    		FailView.errorMessage(e.getMessage());
    	} 
    	
    }

    /**
     * 아이템 사용
     * @param userId
     * @param itemId
     */
    public static void useItem(int userId, int itemId) {
    	try {
    		CharacterInfoDto user = battleService.getBattleUser(userId);
    		if (user.getHp() <= 0 || user.getStageDto().getEnemyHp() <= 0) {
                return;
    		}

    		battleService.useItem(userId, itemId);
    		
		} catch (SQLException  e) {
			// TODO Auto-generated catch block
			FailView.errorMessage(e.getMessage());
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

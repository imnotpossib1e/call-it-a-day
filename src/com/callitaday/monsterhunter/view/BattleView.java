package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.InventoryController;

import java.io.IOError;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.callitaday.monsterhunter.controller.BattleController;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.util.SoundManager;

public class BattleView {
    /**
     * 전투 진입
     */
    public static void battleView(int userId, Scanner sc) {

        BattleController.openBattle(userId, sc);
    }
    
    /**
     * 전투 메뉴 호출
     */
    public static void runBattle(int userId, Scanner sc){

    	if (!BattleController.start(userId)) {
            return;
        }
        // 전투중인 유저 존재 여부 받아오기
        CharacterInfoDto user = BattleController.getState(userId);
        if(user == null) return;

        int nowStage = user.getStage_id();
        EndView.printMessage("[현재 스테이지: " + nowStage + "]");
        printEnemy(nowStage); // stage별 적 이미지 출력
        SoundManager.playStageBgm(nowStage); // stage bgm
    	
    	while(true) {
    		if(BattleView.doBattle(user, sc)) return;
        	
        	String result = sc.nextLine();
        	switch (result) {
    	    	case "1":BattleView.attackView(userId);break;
    	    	case "2":EndView.defendView(userId);break;
    	    	case "3":
                    InventoryController.getInventoryByItemTypeInfo(userId);
                    System.out.print("사용할 포션 번호 > ");
                    try {
                    	int itemId = Integer.parseInt(sc.nextLine());
                    	BattleController.useItem(userId, itemId);
                    } catch (NumberFormatException e) {
                    	System.out.println("아이템 번호를 입력하시오/");
                    }
                    int itemId = Integer.parseInt(sc.nextLine());
                    BattleController.useItem(userId, itemId);
                    break;
    	    	default: System.out.println("메뉴를 다시 선택해주세요.");
        	}

    	}
    }
	
	/**
     * 전투 과정
     */
    public static boolean doBattle(CharacterInfoDto user, Scanner sc){

            boolean defeated = user.getHp() <= 0; // 패배 여부 확인
            boolean victory = user.getHp() > 0 && user.getStageDto().getEnemyHp() <= 0; // 승리 여부 확인

            /**
             * 전투 결과 저장
             */
            if (defeated || victory) {
                System.out.println(victory ? "승리했습니다!" : "패배했습니다.");

                saveBattleAndExit(user.getUserId(), sc);
                return true;
            }
    		
    		System.out.println("-------------------------------------------");
    		System.out.println("몬스터 HP: " + user.getStageDto().getEnemyHp());
    		System.out.println("-------------------------------------------");
    		System.out.println();
    		System.out.println();
    		System.out.println();
    		System.out.println("-------------------------------------------");
    		System.out.println ("내 HP: " + user.getHp()				
                    + "   내 MP: " + user.getMp()
                    );
    		System.out.println("-------------------------------------------");
    		System.out.println();
        	System.out.println("-------------------------------------------");
        	System.out.print("1. 공격하기\n");
        	System.out.print("2. 방어하기\n");
        	System.out.print("3. 아이템 사용\n");
        	System.out.println("-------------------------------------------");
        	return false;
    }
    
    /**
     * 전투 종료 및 저장
     */    
    private static void saveBattleAndExit(int userId, Scanner sc) {

        while (true) {
            if (BattleController.save(userId)) {
                System.out.println("전투 결과를 저장했습니다.");
                return;
            }

            System.out.println(
                    "저장을 완료하지 못했습니다. "
                    + "Enter를 누르면 저장을 재시도합니다."
            );

            sc.nextLine();
        }
    }
    
    /**
     * 공격 뷰
     */
    public static void attackView(int userId){
    	
        BattleController.attack(userId);
    }
    
    /**
     * 공격 성공/실패 여부 출력
     */
    public static void attackResult(String message){
        System.out.println(message);
    }
    
    /**
     * 적 몬스터 이미지 출력
     */
    private static void printEnemy(int stageId) {
    	
    	String fileName = "";
    	switch (stageId) {
    	case 1:
    		fileName = "Image/enemyMonster/villainR.txt";
    		break;
    	case 2:
    		fileName = "Image/enemyMonster/villainE.txt";
    		break;
    	case 3:
    		fileName = "Image/enemyMonster/villainD.txt";
    		break;
    	case 4:
    		fileName = "Image/enemyMonster/villainB.txt";
    		break;
    	case 5:
    		fileName = "Image/enemyMonster/villainL.txt";
    		break;
    	}
    	
    	try {
    		List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    		for (String line : lines) {
    			System.out.println(line);
    		}
    	} catch(IOException e) {
    		System.out.println("적의 이미지를 불러오지 못했습니다.");
    	}
    	
    }
}

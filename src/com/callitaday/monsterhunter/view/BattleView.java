package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.InventoryController;

import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.session.SessionSet;
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
    static SessionSet ss = SessionSet.getInstance();
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
		EndView.printMessage("");
        EndView.printMessage("[스테이지: " + nowStage + "]");
        printEnemy(nowStage); // stage별 적 이미지 출력
        SoundManager.playStageBgm(nowStage); // stage bgm
    	
    	while(true) {
    		if(BattleView.doBattle(user, sc)) return;
        	
        	String result = sc.nextLine();
        	switch (result) {
    	    	case "1":BattleView.attackView(userId);break;
    	    	case "2":EndView.defendView(userId);break;
    	    	case "3":
                    if(!InventoryController.getInventoryByItemTypeInfo(userId)){
                        break;
                    }
                    boolean validInput = true;
                    while(validInput){
                        InventoryController.getInventoryByItemTypeInfo(userId);
                        System.out.print("포션 번호 > ");
                        String itemId = sc.nextLine();

                    	if(BattleController.useItem(userId, itemId)){
                            validInput=false;
                        }else {
                            InventoryController.getInventoryByItemTypeInfo(userId);
                        }
                    }
                    break;
    	    	default: System.out.println("메뉴를 다시 선택해 주세요.");
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
            EndView.printNotice(victory ?  "전투에 승리했습니다!" : "전투에 패배했습니다.");
            if(victory){
                EndView.printVictory(user);
            }
            saveBattleAndExit(user.getUserId(), sc);
            return true;
        }

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║                                        ║ ");
        System.out.println("║"+center("몬스터", 40) +"║");
        System.out.println("║"+center("HP " + user.getStageDto().getEnemyHp(), 40) +"║");
        System.out.println("║                                        ║ ");
        System.out.println("║"+center("⚔ "+ss.getList().getId(), 40)+"║");
        System.out.println("║" + center("HP " + user.getHp() + "    MP " + user.getMp(), 40)+"║");
        System.out.println("║                                        ║ ");
        System.out.println("╚════════════════════════════════════════╝");

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║" + center("⚔ 행동 선택", 40) +"║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║"+center("1. 공격하기", 40)+"║");
        System.out.println("║"+center("2. 방어하기", 40)+"║");
        System.out.println("║"+center("3. 아이템 사용", 40)+"║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("⚔ 선택 > " );
        return false;
    }
    
    /**
     * 전투 종료 및 저장
     */    
    private static void saveBattleAndExit(int userId, Scanner sc) {

        while (true) {
            if (BattleController.save(userId)) {
                EndView.printNotice("전투 결과를 저장했습니다.");
                return;
            }

            EndView.printNotice2Line("⚠ 저장을 완료하지 못했습니다.", "Enter를 누르면 저장을 재시도합니다.");
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

    public static void displayMyAttack(int damage) {
        System.out.println("⚔ 공격합니다!");
        System.out.println("  → " + "몬스터 HP -" + damage);
    }

    public static void displayEnemyAttack(int damage) {
        System.out.println("⚡ 몬스터의 공격!");
        System.out.println("  → " + "내 HP -" + damage);
    }

    public static void displayMyDefenceSuccess(int damage) {
        System.out.println("█ 방어합니다 !");
        System.out.println("  데미지 반사 → " + "몬스터 HP -" + damage);
    }
    public static void displayMyDefenceFail(int damage) {
        System.out.println("█ 방어합니다!");
        System.out.println("  방어 실패 → "   + " HP -" + damage);
    }



    // 중앙 정렬 함수
    public static String center(String text, int width) {
        int visibleLength = getVisibleLength(text);
        int padding = (width - visibleLength) / 2;
        return " ".repeat(padding) + text +
            " ".repeat(width - padding - visibleLength);
    }


    public static int getVisibleLength(String text) {
        String cleaned = text.replaceAll("\u001B\\[[0-9;]*m", "");
        int length = 0;
        for (char c : cleaned.toCharArray()) {
            if (c >= 0xAC00 && c <= 0xD7A3) {
                length += 2;  // 한글은 2칸
            } else {
                length += 1;  // 영어는 1칸
            }
        }
        return length;
    }
}

package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.ItemController;
import com.callitaday.monsterhunter.controller.ShopController;
import com.callitaday.monsterhunter.controller.StageController;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.sun.tools.javac.Main;
import java.awt.Menu;
import java.util.Scanner;

import com.callitaday.monsterhunter.controller.BattleController;
import com.callitaday.monsterhunter.controller.InventoryController;

public class MenuView {
    private static Scanner sc = new Scanner(System.in);

    // 임시 유저 설정
    private static int userId = 1;

    public static void menu(){
        // Todo 세션 받아오기

        //
        MenuView.printLoginMenu();
        int menu = Integer.parseInt(sc.nextLine());
        switch (menu){
            case 1: // 회원가입
                MenuView.registor();
                break;
            case 2: // 로그인
                MenuView.login();
                // 임시 자동 로그인
                MenuView.printMainView(userId);
            default:
                System.exit(0);
        }
    }

    /**
     * 로그인 선택 메뉴 출력
     */
    public static void printLoginMenu(){
        System.out.println("=======1.=======");
        System.out.println("1. 가입  | 2. 로그인  |  Press Any Key : 종료");
    }

    /**
     * 메인 선택 메뉴 출력
     */
    public static void printMainView(int userId){
        while(true){
            // Todo 세션 가져오기

            System.out.println("========2=======");
            System.out.println("1. 전투  |  2. 상점  |  3. 인벤토리  |  4. 로그아웃");
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1: // 전투
                    MenuView.stageView(userId);
                    break;
                case 2: // 상점
                    MenuView.shopView(userId);
                    break;
                case 3: // 인벤토리
                    MenuView.inventoryView(userId);
                    break;
                case 4: // 로그아웃
                    logout(userId);
                    return;
                default:
                    System.out.println("메뉴를 다시 선택해주세요.");
            }
        }
    }

    /**
     * 로그인 메뉴
     */
    public static void login(){
        //성공 시 printMainView로 이동
    }

    /**
     * 로그아웃
     */
    public static void logout(int userId){

    }

    /**
     * 회원가입 메뉴
     */
    public static void registor(){

    }

    /**
     * 스테이지 선택 메뉴
     */
    public static void stageView(int userId){
        boolean validInput = true;
        // 선택지가 유효할 때 까지 반복
        while(validInput){
            StageController.selectStage(userId);
            String choice = sc.nextLine();
            switch(choice){
                case "Y":
                    battleView(userId);
                    validInput = false;
                    break;
                case "N":
                    System.out.println("메인 메뉴로 돌아갑니다.");
                    validInput = false;
                    break;
                default:
                    System.out.println("다시 입력해주세요.");
            }
        }
    }

    /**
     * 전투 진입
     */
    public static void battleView(int userId) {
        BattleController.openBattle(userId, sc);
    }
    
    /**
     * 전투 메뉴 호출
     */
    public static void runBattle(int userId, Scanner scanner){
    	
    	if (!BattleController.start(userId)) {
            return;
        }
    	
    	while(true) {
    		CharacterInfoDto user = BattleController.getState(userId);
    		if(user == null) return;
    		
    		System.out.println("-------------------------------------------");
    		System.out.println("몬스터 HP: " + user.getStageDto().getEnemyHp());
    		System.out.println("-------------------------------------------");
    		System.out.println();
    		System.out.println();
    		System.out.println();
    		System.out.println("-------------------------------------------");
    		System.out.println ("내 HP: " + user.getHp()				
                    + "내 MP: " + user.getMp()						
                    );
    		System.out.println("-------------------------------------------");
    		System.out.println();
        	System.out.println("-------------------------------------------");
        	System.out.print("|1. 공격하기									|\n");
        	System.out.print("|2. 방어하기									|\n");
        	System.out.print("|3. 아이템 사용								|\n");
        	System.out.println("-------------------------------------------");
        	
        	boolean defeated = user.getHp() <= 0;
        	boolean victory = user.getHp() > 0 && user.getStageDto().getEnemyHp() <= 0;
        	
        	if (defeated || victory) {
                System.out.println(victory ? "승리했습니다!" : "패배했습니다.");
                
                saveBattleAndExit(userId, scanner);
            	return;
        	}
        	
        	int result = Integer.parseInt(sc.nextLine());
        	switch (result) {
    	    	case 1:EndView.attackView(userId);break;
    	    	case 2:EndView.defendView(userId);break;
    	    	case 3:System.out.print("사용할 포션 번호 > ");
                BattleController.useItem(userId, scanner.nextLine());break;
    	    	default: System.out.println("메뉴를 다시 선택해주세요.");
        	}

    	}
    }
    /**
     * 전투 종료 및 저장
     */
    private static void saveBattleAndExit(int userId, Scanner scanner) {

        while (true) {
            if (BattleController.save(userId)) {
                System.out.println("전투 결과를 저장했습니다.");
                return;
            }

            System.out.println(
                    "저장을 완료하지 못했습니다. "
                    + "Enter를 누르면 저장을 재시도합니다."
            );

            scanner.nextLine();
        }
    }

    /**
     * 상점 메뉴
     */
    public static void shopView(int userId){
//        // 판매 목록 띄우기
//        ItemController.selectAllItem();
//
//        // 내가 보유한 아이템 띄우기
//
//        // 구매할 아이템, 수량 받기
//        System.out.println("메인 메뉴로 돌아가기 : Q");
//        System.out.print("구매할 아이템 번호 > ");
//        int item_id = Integer.parseInt(sc.nextLine());
//        System.out.print("구매할 아이템 수량 > ");
//        int quantity = Integer.parseInt(sc.nextLine());
//        ShopController.purchaceItem(userId, item_id, quantity);

        ItemController.selectAllItem();
        boolean validInput = true;
        // 선택지가 유효할 때 까지 반복
        while(validInput){
            // 구매할 아이템, 수량 받기
            System.out.println("메인 메뉴로 돌아가기 : Q");
            System.out.print("구매할 아이템 번호 > ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Q")) {
                System.out.println("메인 메뉴로 돌아갑니다.");
                break;
            }

            try {
                int item_id = Integer.parseInt(input);
                System.out.print("구매할 아이템 수량 > ");
                int quantity = Integer.parseInt(sc.nextLine());
                ShopController.purchaceItem(userId, item_id, quantity);
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    /**
     * 아이템 구매 메뉴
     */
    public static void shopChoiceView(int userId){

    }

    /**
     * 인벤토리 메뉴
     */
    public static void inventoryView(int userId) {
    	while(true){
            // Todo 세션 가져오기

            System.out.println("========2=======");
            System.out.println("1. 인벤토리  |  2. 나가기 ");
            int menu = Integer.parseInt(sc.nextLine());
            switch (menu){
                case 1: // 전투
                	InventoryController.getCharacterInfo(userId);
                    break;
                case 2: // 상점
                    return;
                    
                default:
                    System.out.println("메뉴를 다시 선택해주세요.");
            }
        }

    }
}

package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.ItemController;
import com.callitaday.monsterhunter.controller.ShopController;
import java.awt.Menu;
import java.util.Scanner;

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
                    MenuView.battleView(userId);
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
     * 전투 메뉴
     */
    public static void battleView(int userId){

    }

    /**
     * 상점 메뉴
     */
    public static void shopView(int userId){
        // 판매 목록 띄우기
        ItemController.selectAllItem();

        // 내가 보유한 아이템 띄우기

        // 구매할 아이템, 수량 받기
        System.out.print("구매할 아이템 번호 > ");
        int item_id = Integer.parseInt(sc.nextLine());
        System.out.print("구매할 아이템 수량 > ");
        int quantity = Integer.parseInt(sc.nextLine());
        ShopController.purchaceItem(userId, item_id, quantity);

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

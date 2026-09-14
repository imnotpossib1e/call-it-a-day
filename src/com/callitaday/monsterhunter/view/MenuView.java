package com.callitaday.monsterhunter.view;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import com.callitaday.monsterhunter.controller.BattleController;
import com.callitaday.monsterhunter.controller.InventoryController;
import com.callitaday.monsterhunter.controller.ItemController;
import com.callitaday.monsterhunter.controller.ShopController;
import com.callitaday.monsterhunter.controller.StageController;
import com.callitaday.monsterhunter.controller.UserController;
import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.session.SessionSet;
import com.callitaday.monsterhunter.util.SoundManager;

public class MenuView {

	private static Scanner sc = new Scanner(System.in);
	static SessionSet ss = SessionSet.getInstance();// 세션 기록

	public static void menu() {
		// Todo 세션 받아오기
		while (true) { // 세션 기록 추가
			MenuView.printLoginMenu();
			String menu = sc.nextLine();
			switch (menu) {
			case "1": // 회원가입
				MenuView.registor();
				break;
			case "2": // 로그인
				MenuView.login();
			default:
				EndView.printNotice("게임을 종료합니다.");
				System.exit(0);
			}
		}
	}

	/**
	 * 로그인 선택 메뉴 출력
	 */
	public static void printLoginMenu() {
		
		String fileName = "Image/Main/Main.txt";
		
    	try {
    		List<String> lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    		for (String line : lines) {
    			System.out.println(line);
    		}
    	} catch(IOException e) {
    		System.out.println("메인 이미지 로드 실패");
    	}
		System.out.println("╔════════════════════════════════════════╗");
		System.out.println("║             MONSTER HUNTER             ║");
		System.out.println("╠════════════════════════════════════════╣");
		System.out.println("║  1. 회원가입  │  2. 로그인  │  종료    ║");
		System.out.println("╚════════════════════════════════════════╝");
		System.out.print("⚔ 선택 > ");

	}

	/**
	 * 메인 선택 메뉴 출력
	 */
	public static void printMainView(int userId) {
    	
		SoundManager.playMainBgm();
		
		while (true) {
			timeDelay(500);
			System.out.println("█   █  ███  █   █  ████ █████ █████ ████     █   █ █   █ █   █ █████ █████ ████    \r\n"
					+ "██ ██░█ ░░█ ██  █░█ ░░░░ ░█░░░█░░░░░█░░░█    █░  █░█░  █░██  █░ ░█░░░█░░░░░█░░░█   \r\n"
					+ "█░█ █░█░ ░█░█░█ █░░███░░░ █░░░████░░████░░   █████░█░░ █░█░█ █░░ █░░░████░░████░░  \r\n"
					+ "█░░░█░█░░ █░█░░██░░ ░░█   █░░ █░░░░ █░░█░ ░  █░░░█░█░░ █░█░░██░░ █░░ █░░░░ █░░█░ ░ \r\n"
					+ "█░░ █░░███ ░█░░ █░████░░  █░░ █████░█░░░█░   █░░░█░░███ ░█░░ █░░ █░░ █████░█░░░█░  \r\n"
					+ " ░░  ░░ ░░░ ░░░  ░░░░░░ ░  ░░  ░░░░░ ░░  ░    ░░  ░░ ░░░ ░░░  ░░  ░░  ░░░░░ ░░  ░  \r\n"
					+ "  ░   ░  ░░░  ░   ░ ░░░░    ░   ░░░░░ ░   ░    ░   ░  ░░░  ░   ░   ░   ░░░░░ ░   ░ ");

			timeDelay(500);
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║             MONSTER HUNTER             ║");
			System.out.println("╠════════════════════════════════════════╣");
			System.out.println("║  1. 전투  │  2. 상점  │  3. 인벤토리   ║");
			System.out.println("║              4. 로그아웃               ║");
			System.out.println("╚════════════════════════════════════════╝");
			System.out.print("⚔ 선택 > ");
			String menu = sc.nextLine();
			switch (menu) {
			case "1": // 전투
				MenuView.stageView(userId);
				SoundManager.playMainBgm();
				break;
			case "2": // 상점
				MenuView.shopView(userId);
				SoundManager.playMainBgm();
				break;
			case "3": // 인벤토리
				MenuView.inventoryView(userId);
				break;
			case "4": // 로그아웃
				boolean choiceY = logout(userId);
				if (choiceY) {
					SoundManager.stopBgm();
					menu();
					return;
				}
				break;
			default:
				System.out.println("⚠ 메뉴를 다시 선택해 주세요.");
			}
		}
	}

	/**
	 * 로그인 메뉴
	 */
	public static void login() {
		System.out.println();
		String id = null;
		boolean idSuccess = false;

		for (int i = 1; i <= 3; i++) {
			System.out.print("아이디 > ");
			id = sc.nextLine();
			UserDto existUser = UserController.selectById(id);
			if (existUser != null) {
				idSuccess = true;
				break;
			}
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║      ⚠ 존재하지 않는 아이디입니다.     ║");
			System.out.println("║           다시 입력해 주세요.          ║");
			System.out.println("╚════════════════════════════════════════╝");
		}

		if (!idSuccess) {
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║           ⚠ 아이디 3회 오류            ║");
			System.out.println("║        메인화면으로 돌아갑니다.        ║");
			System.out.println("╚════════════════════════════════════════╝");
			menu();
			return;
		}

		int password = 0;
		int pwSuccess = 0;
		String passwordInput = null;

		for (int i = 1; i <= 3; i++) {
			System.out.print("비밀번호 > ");
			passwordInput = sc.nextLine();
			if (!passwordInput.matches("\\d{4}")) {
				System.out.println("╔════════════════════════════════════════╗");
				System.out.println("║       ⚠ 4자리 숫자로 입력하세요.       ║");
				System.out.println("║           다시 입력해 주세요.          ║");
				System.out.println("╚════════════════════════════════════════╝");
				continue;
			}

			password = Integer.parseInt(passwordInput);

			UserDto loginUser = UserController.login(id, password); // 먼저 UserDto로 받기
			if (loginUser != null) {
				pwSuccess = loginUser.getUserId(); // null이 아닐 때만 getUserId() 호출
				System.out.println();
				printMainView(pwSuccess);
				System.out.println();
			}
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║    ⚠ 비밀번호를 다시 입력해 주세요.    ║");
			System.out.println("╚════════════════════════════════════════╝");
		}
		System.out.println("╔════════════════════════════════════════╗");
		System.out.println("║          ⚠ 비밀번호 3회 오류           ║");
		System.out.println("║        메인화면으로 돌아갑니다.        ║");
		System.out.println("╚════════════════════════════════════════╝");
		menu();
	}

	/**
	 * 로그아웃
	 */
	public static boolean logout(int userId) {
		String choice;
		boolean out = true;

		// 선택지가 유효할 때 까지 반복
		while (out) {
			System.out.print("ℹ 로그아웃 하시겠습니까? [Y / N]  > ");
			choice = sc.nextLine();
			switch (choice) {
			case "Y", "y", "ㅛ":
				ss.setList(null);
				return true;
			case "N", "n", "ㅜ":
				return false;
			default:
				System.out.println("⚠ 다시 입력해 주세요.");
			}
		}
		return out;
	}

	/**
	 * 회원가입 메뉴
	 */
	public static void registor() {
		System.out.println("╔════════════════════════════════════════╗");
		System.out.println("║                회원가입                ║");
		System.out.println("╠════════════════════════════════════════╣");
		System.out.println("║   🔑 아이디: 10자리 이하의 영문       ║");
		System.out.println("║   🔐 비밀번호: 숫자 4자리             ║");
		System.out.println("╚════════════════════════════════════════╝");
		String id;
		UserDto existUser;

		while (true) {
			System.out.print("아이디 > ");
			id = sc.nextLine();

			if (id.length() > 10) {
				System.out.println(" ⚠ 아이디는 10자리 이하로 작성해 주세요");
				continue;
			}

			existUser = UserController.selectById(id);

			if (existUser == null) {
				System.out.println(" ✓ 사용 가능한 아이디입니다.");
				break;
			}
			System.out.println(" ⚠ 이미 존재하는 아이디입니다.");
		}
		String passwordInput;
		boolean pwSuccess = false;
		for (int i = 1; i <= 3; i++) {
			System.out.print("비밀번호 > ");
			passwordInput = sc.nextLine();
			if (passwordInput.matches("\\d{4}")) {
				pwSuccess = true;
				int password = Integer.parseInt(passwordInput);
				UserDto userDto = new UserDto(id, password);
				UserController.insertUser(userDto);
				break; // 성공했으니 반복 종료
			}
			System.out.println(" ⚠ 4자리 숫자로 입력해 주세요.");
		}
		if (!pwSuccess) {
			System.out.println("╔════════════════════════════════════════╗");
			System.out.println("║          ⚠ 비밀번호 3회 오류           ║");
			System.out.println("║        메인화면으로 돌아갑니다.        ║");
			System.out.println("╚════════════════════════════════════════╝");
		}
		menu();
	}

	/**
	 * 스테이지 선택 메뉴
	 */
	public static void stageView(int userId) {
		boolean validInput = true;
		// 선택지가 유효할 때 까지 반복
		while (validInput) {
			System.out.println();
			StageController.selectStage(userId);
			String choice = sc.nextLine();
			switch (choice) {
			case "Y", "y", "ㅛ":
				battleView(userId);
				validInput = false;
				break;
			case "N", "n", "ㅜ":
				System.out.println("ℹ 메인 메뉴로 돌아갑니다.");
				validInput = false;
				break;
			case "":
				validInput = false;
				break;
			default:
				System.out.println("⚠ 다시 입력해 주세요.");
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
     * 상점 메뉴
     */
    public static void shopView(int userId){

    	SoundManager.playShopBgm();
    	
        ItemController.selectAllItem();
        boolean validInput = true;
        // 선택지가 유효할 때 까지 반복
        while(validInput){
            // 구매할 아이템, 수량 받기
            System.out.println("메인 메뉴로 돌아가기 : Q");
            System.out.print("구매할 아이템 번호 > ");
            String input = sc.nextLine();

            if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("q") || input.equalsIgnoreCase("ㅂ")) {
                System.out.println("메인 메뉴로 돌아갑니다.");
                break;
            }

            try {
                int item_id = Integer.parseInt(input);
                if(item_id < 1 || item_id > 7){
                    System.out.println("올바른 번호를 입력해 주세요.");
                    continue;
                }
                System.out.print("구매할 아이템 수량 > ");
                int quantity = Integer.parseInt(sc.nextLine());
                ShopController.purchaceItem(userId, item_id, quantity);
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호를 입력해 주세요.");
            }
        }
    }


	/**
	 * 인벤토리 메뉴
	 */
	public static void inventoryView(int userId) {
		while (true) {
			// Todo 세션 가져오기

			InventoryController.getCharacterInfo(userId);
			System.out.println("1. 인벤토리 조회 |  2. 나가기 ");
			System.out.print("⚔ 선택 > ");
			String menu = sc.nextLine();
			switch (menu) {
			case "1": // 인벤토리 조회
				MenuView.equipView(userId);
				break;
			case "2": // 상점
				return;

			default:
				System.out.println("메뉴를 다시 선택해 주세요.");
			}
		}

	}

	public static void equipView(int userId) {
		while (true) {

			InventoryController.getInventoryInfo(userId);
			System.out.println("1. 장비 아이템 장착  |  2. 장착 중인 장비 해제  |  3. 뒤로가기 ");
			System.out.print("⚔ 선택 > ");
			String menu = sc.nextLine();
			switch (menu) {
			case "1": // 장비 장착 및 교체
				System.out.print("장착할 아이템 이름 > ");
				String equipName = sc.nextLine();
				InventoryController.equipItem(userId, equipName);
				break;
			case "2": // 장비 해제
				System.out.print("장착 해제할 아이템 이름 > ");
				String unequipName = sc.nextLine();
				InventoryController.unequipItem(userId, unequipName);
				break;
			case "3": // 뒤로가기
				return;

			default:
				System.out.println("메뉴를 다시 선택해 주세요.");
			}
		}

	}
	private static void timeDelay(int time){
		try {
			Thread.sleep(time); // 1.0초 동안 지연
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

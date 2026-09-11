package com.callitaday.monsterhunter.view;

import java.util.Scanner;

import com.callitaday.monsterhunter.controller.BattleController;
import com.callitaday.monsterhunter.controller.InventoryController;
import com.callitaday.monsterhunter.controller.ItemController;
import com.callitaday.monsterhunter.controller.ShopController;
import com.callitaday.monsterhunter.controller.StageController;
import com.callitaday.monsterhunter.controller.UserController;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.UserDto;
import com.callitaday.monsterhunter.session.SessionSet;

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
				System.out.println("안녕히가세요");
				System.exit(0);
			}
		}
	}

	/**
	 * 로그인 선택 메뉴 출력
	 */
	public static void printLoginMenu() {
		System.out.println("=======1.=======");
		System.out.println("1. 가입  | 2. 로그인  |  Press Any Key : 종료");
	}

	/**
	 * 메인 선택 메뉴 출력
	 */
	public static void printMainView(int userId) {
		while (true) {
			// Todo 세션 가져오기
			System.out.println(ss.getList().getId() + " 반갑습니다 ");

			System.out.println("========2=======");
			System.out.println("1. 전투  |  2. 상점  |  3. 인벤토리  |  4. 로그아웃");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
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
				boolean choiceY = logout(userId);
				if (choiceY) {
					menu();
					return;
				}
			default:
				System.out.println("메뉴를 다시 선택해주세요.");
			}
		}
	}

	/**
	 * 로그인 메뉴
	 */
	public static void login() {
		System.out.print("아이디를 입력하세요 ");
		String id = null;
		boolean idSuccess = false;

		for (int i = 1; i <= 3; i++) {
			id = sc.nextLine();
			UserDto existUser = UserController.selectById(id);
			if (existUser != null) {
				idSuccess = true;
				break;
			}
			System.out.print(" 존재하지 않는 아이디입니다. 다시 입력하세요 ");
		}

		if (!idSuccess) {
			System.out.println("아이디 3회 오류로 메인화면으로 돌아갑니다");
			menu();
			return;
		}

		System.out.print("비밀번호를 입력하세요 ");
		int password = 0;
		int pwSuccess = 0;
		String passwordInput = null;

		for (int i = 1; i <= 3; i++) {
			passwordInput = sc.nextLine();
			if (!passwordInput.matches("\\d{4}")) {
				System.out.print(" -- 4자리 숫자로 입력하세요. 다시 입력하세요 -- ");
				continue;
			}

			password = Integer.parseInt(passwordInput);
			pwSuccess = UserController.login(id, password).getUserId();

			if (pwSuccess != 0) {
				printMainView(pwSuccess);
				return; // 성공하면 바로 종료
			}
			System.out.print("비밀번호를 다시 입력하세요 ");
		}

		System.out.println("비밀번호 3회 오류로 메인화면으로 돌아갑니다");
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
			System.out.print("로그아웃 하시겠습니까? (Y/N) ");
			choice = sc.nextLine();
			switch (choice) {
			case "Y", "y", "ㅛ":
				ss.setList(null);
				return true;

			case "N", "n", "ㅜ":
				return false;
			default:
				System.out.println("다시 입력해주세요.");
			}
		}
		return out;
	}

	/**
	 * 회원가입 메뉴
	 */
	public static void registor() {
		System.out.println(" -- 회원가입을 진행합니다. 아이디 입력하세요 -- ");
		String id;
		UserDto existUser;

		while (true) {
			id = sc.nextLine();

			if (id.length() > 10) {
				System.out.println("아이디는 10자리 이하로 작성해주세요");
				continue;
			}

			existUser = UserController.selectById(id);

			if (existUser == null) {
				System.out.println(" -- 사용 가능한 아이디입니다.-- ");
				break;
			}
			System.out.println(" -- 이미 존재하는 아이디입니다. 다시 입력해주세요.-- ");
		}
		System.out.println(" -- 등록할 비밀번호를 입력해주세요.( 4자리 숫자 ) -- ");
		String passwordInput;
		boolean pwSuccess = false;
		for (int i = 1; i <= 3; i++) {
			passwordInput = sc.nextLine();
			if (passwordInput.matches("\\d{4}")) {
				pwSuccess = true;
				int password = Integer.parseInt(passwordInput);
				UserDto userDto = new UserDto(id, password);
				UserController.insertUser(userDto);
				break; // 성공했으니 반복 종료
			}
			System.out.println(" 비밀번호는 ( 4자리 숫자 ) 입니다!!!!! ");
		}
		if (!pwSuccess) {
			System.out.println(" -- 3회 모두 실패하여 메뉴로 돌아갑니다. -- ");
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
			StageController.selectStage(userId);
			String choice = sc.nextLine();
			switch (choice) {
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
	public static void runBattle(int userId, Scanner scanner) {

		if (!BattleController.start(userId)) {
			return;
		}

		while (true) {
			CharacterInfoDto user = BattleController.getState(userId);
			if (user == null)
				return;

			System.out.println("-------------------------------------------");
			System.out.println("몬스터 HP: " + user.getStageDto().getEnemyHp());
			System.out.println("-------------------------------------------");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("-------------------------------------------");
			System.out.println("내 HP: " + user.getHp() + "내 MP: " + user.getMp());
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
			case 1:
				EndView.attackView(userId);
				break;
			case 2:
				EndView.defendView(userId);
				break;
			case 3:
				System.out.print("사용할 포션 번호 > ");
				BattleController.useItem(userId, scanner.nextLine());
				break;
			default:
				System.out.println("메뉴를 다시 선택해주세요.");
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

			System.out.println("저장을 완료하지 못했습니다. " + "Enter를 누르면 저장을 재시도합니다.");

			scanner.nextLine();
		}
	}

	/**
	 * 상점 메뉴
	 */
	public static void shopView(int userId) {

		ItemController.selectAllItem();
		boolean validInput = true;
		// 선택지가 유효할 때 까지 반복
		while (validInput) {
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
	public static void shopChoiceView(int userId) {

	}

	/**
	 * 인벤토리 메뉴
	 */
	public static void inventoryView(int userId) {
		while (true) {
			// Todo 세션 가져오기

			System.out.println("========2=======");
			InventoryController.getCharacterInfo(userId);
			System.out.println("1. 인벤토리 조회 |  2. 나가기 ");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1: // 인벤토리 조회
				MenuView.equipView(userId);
				break;
			case 2: // 상점
				return;

			default:
				System.out.println("메뉴를 다시 선택해주세요.");
			}
		}

	}

	public static void equipView(int userId) {
		while (true) {

			System.out.println("========2=======");
			InventoryController.getInventoryInfo(userId);
			System.out.println("1. 장비 아이템 장착  |  2. 장착 중인 장비 해제  |  3. 뒤로가기 ");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1: // 장비 장착 및 교체
				String equipName = sc.nextLine();
				InventoryController.equipItem(userId, equipName);
				break;
			case 2: // 장비 해제
				String unequipName = sc.nextLine();
				InventoryController.unequipItem(userId, unequipName);
				break;
			case 3: // 뒤로가기
				return;

			default:
				System.out.println("메뉴를 다시 선택해주세요.");
			}
		}

	}

}

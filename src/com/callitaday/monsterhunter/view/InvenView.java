package com.callitaday.monsterhunter.view;

import java.util.List;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;

public class InvenView {
	// 전체 가로 길이 = 48
    // 내부 영역 = 46
    private static final int INNER_WIDTH = 46;

    // ':' 왼쪽 영역
    private static final int LABEL_WIDTH = 14;

    // ':' 오른쪽 영역
    private static final int VALUE_WIDTH = 30;
    
	public static void printCharacterInfo(CharacterInfoDto charactorInfoDto) {
		int addAtk = 0;
		int addDef = 0;
		
		if(charactorInfoDto.getEquiplist().size()>0 || charactorInfoDto.getEquiplist() != null) {
			for(ItemDto id : charactorInfoDto.getEquiplist()) {
				if("무기".equals(id.getItemType())) addAtk = id.getItemIncrease();
				else if("방어구".equals(id.getItemType())) addDef = id.getItemIncrease();
			}
		}
		
//		System.out.println(charactorInfoDto.getUserId()+"님의 캐릭터 정보");
//		System.out.println();
//		System.out.printf("%-5s %-5s %-5s %-5s %-5s %-5s%n", "체력", "마나", "공격력 + 무기", "방어력 + 방어구", "소지금", "스테이지");
//		System.out.printf("%-5d %-5d %d + %-5d %d + %-5d %-5d %-5d%n", charactorInfoDto.getHp(), charactorInfoDto.getMp(), charactorInfoDto.getAtk(), addAtk, charactorInfoDto.getDef(), addDef, charactorInfoDto.getCoin(), 0);
		
		System.out.println();

        printLine();
        printCenter("CHARACTER STATUS");
        printLine();

        printEmptyLine();

        // HP
        printValue("HP", String.valueOf(charactorInfoDto.getHp()));

        // MP
        printValue("MP", String.valueOf(charactorInfoDto.getMp()));

        printEmptyLine();

        // 전투 정보
        printText("------------ COMBAT STATUS ---------------");

        printEmptyLine();

        // 공격력
        int totalAttack =
        		charactorInfoDto.getAtk()
                + addAtk;

        printValue(
                "ATTACK",
                String.format(
                        "%d  +  %d  =  %d",
                        charactorInfoDto.getAtk(),
                        addAtk,
                        totalAttack
                )
        );

        // 장착 무기
        printEquippedItem(charactorInfoDto.getEquiplist(), "무기");

        printEmptyLine();

        // 방어력
        int totalDefense =
        		charactorInfoDto.getDef()
                + addDef;

        printValue(
                "DEFENSE",
                String.format(
                        "%d  +  %d  =  %d",
                        charactorInfoDto.getDef(),
                        addDef,
                        totalDefense
                )
        );

        // 장착 방어구
        printEquippedItem(charactorInfoDto.getEquiplist(), "방어구");

        printEmptyLine();

        // 기타 정보
        printText("------------- INFORMATION ----------------");

        printEmptyLine();

        // 소지금
        printValue(
                "GOLD",
                String.format(
                        "%,d G",
                        charactorInfoDto.getCoin()
                )
        );

        // 스테이지
        printValue(
                "STAGE",
                String.valueOf(charactorInfoDto.getStage_id())
        );

        printEmptyLine();

        printLine();

        System.out.println();
	}
	
	public static void printInventoryInfo(List<InventoryDto> InvenList) {		
		System.out.println("아이템, 타입, 회복력, 공격력 증가치, 방어력 증가치, 수량, 장착 여부, 설명");
		for(InventoryDto item : InvenList) {
			int hpIncrease = 0;
			int atkIncrease = 0;
			int defIncrease = 0;
			
			String name = item.getItemDto().getItemName();
			String type = item.getItemDto().getItemType();
			
			if (type.equals("포션")) hpIncrease = item.getItemDto().getItemIncrease();
			else if (type.equals("무기")) atkIncrease = item.getItemDto().getItemIncrease();
			else defIncrease = item.getItemDto().getItemIncrease();
			
			int qnt = item.getQuantity();
			String isEquiped = item.isEquipped() ? "장착" : " X ";
			String explain = item.getItemDto().getItemExplanation();
			System.out.println(name + ", " + type + ", " + hpIncrease + ", " + atkIncrease + ", " + defIncrease + ", " + qnt  + ", " + isEquiped  + ", " + explain);
		}
	}
	
	/**
     * 외곽선 출력
     */
    private static void printLine() {

        System.out.println(
                "+" + "-".repeat(INNER_WIDTH) + "+"
        );
    }


    /**
     * 빈 줄 출력
     */
    private static void printEmptyLine() {

        System.out.println(
                "|" + " ".repeat(INNER_WIDTH) + "|"
        );
    }


    /**
     * 가운데 정렬된 텍스트 출력
     */
    private static void printCenter(String text) {

        String result = center(text, INNER_WIDTH);

        System.out.println(
                "|" + result + "|"
        );
    }


    /**
     * 내부에 고정된 텍스트 출력
     */
    private static void printText(String text) {

        String result = rightPad(text, INNER_WIDTH);

        System.out.println(
                "|" + result + "|"
        );
    }


    /**
     * LABEL : VALUE 형태 출력
     *
     * LABEL → 가운데 정렬
     * VALUE → 오른쪽 정렬
     */
    private static void printValue(
            String label,
            String value) {

        String left = center(label, LABEL_WIDTH);

        String right = center(value, VALUE_WIDTH);

        System.out.println(
                "|" + left + ":" + right + "|"
        );
    }


    /**
     * 장착 아이템 출력
     *
     * [무기] / [방어구] → 가운데 정렬
     * itemName → 오른쪽 정렬
     */
    private static void printEquippedItem(List<ItemDto> items, String itemType) {

        if (items == null || items.isEmpty()) {
            return;
        }

        for (ItemDto item : items) {

            if (item == null) {
                continue;
            }

            if (itemType.equals(item.getItemType())) {

                printValue(
                        "[" + itemType + "]",
                        item.getItemName()
                );

                return;
            }
        }
    }


    /**
     * 문자열 가운데 정렬
     *
     * 한글은 2칸으로 계산
     */
    private static String center(
            String text,
            int width) {

        int textWidth = getDisplayWidth(text);

        if (textWidth >= width) {
            return text;
        }

        int totalPadding = width - textWidth;

        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(leftPadding)
                + text
                + " ".repeat(rightPadding);
    }


    /**
     * 문자열 오른쪽 정렬
     *
     * 한글은 2칸으로 계산
     */
    private static String leftPad(
            String text,
            int width) {

        int textWidth = getDisplayWidth(text);

        if (textWidth >= width) {
            return text;
        }

        return " ".repeat(width - textWidth)
                + text;
    }


    /**
     * 문자열 오른쪽에 공백 추가
     */
    private static String rightPad(
            String text,
            int width) {

        int textWidth = getDisplayWidth(text);

        if (textWidth >= width) {
            return text;
        }

        return text
                + " ".repeat(width - textWidth);
    }


    /**
     * 콘솔 표시 폭 계산
     *
     * 한글 → 2칸
     * ASCII → 1칸
     */
    private static int getDisplayWidth(String text) {

        int width = 0;

        for (char c : text.toCharArray()) {

            if (isKorean(c)) {
                width += 2;
            } else {
                width += 1;
            }
        }

        return width;
    }
    

    /**
     * 한글 여부
     */
    private static boolean isKorean(char c) {

        return (c >= '\u1100' && c <= '\u11FF')
                || (c >= '\u3130' && c <= '\u318F')
                || (c >= '\uAC00' && c <= '\uD7A3');
    }
}

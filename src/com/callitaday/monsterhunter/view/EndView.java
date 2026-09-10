package com.callitaday.monsterhunter.view;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import java.util.List;

public class EndView {
    public static void printMessage(String message) {
		    System.out.println(message);
    }
  

    /**
     * 아이템 전체 출력
     */
    public static void printAllItem (List<ItemDto> list) {

        int colWidth = 35; // 한 칸의 너비 지정

        // 1. 출력하고 싶은 카테고리 순서를 배열로 미리 지정합니다.
        String[] targetTypes = {"회복포션", "마나포션", "무기", "방어구"};
        for(String targetType : targetTypes){
            StringBuilder nameLine = new StringBuilder();
//            StringBuilder effectLine = new StringBuilder();
            StringBuilder priceLine = new StringBuilder();
            StringBuilder descLine = new StringBuilder();

            System.out.println("> " + targetType);

            for(ItemDto item: list){
                if(!item.getItemType().equals(targetType)){
                    continue;
                }
                String itemType = "";
                switch (item.getItemType()){
                    case "회복포션": itemType="HP"; break;
                    case "마나포션": itemType="MP"; break;
                    case "방어구" : itemType="DEF"; break;
                    case "무기" : itemType="ATK"; break;
                }

                // 데이터 누적
                nameLine.append(padRight(item.getItemName() + " " + itemType + " +" + item.getItemIncrease(), colWidth));
//                effectLine.append(padRight("효과 " + itemType+  " +" +item.getItemIncrease(), colWidth));
                priceLine.append(padRight("가격 " + item.getItemPrice(), colWidth));
                descLine.append(padRight(item.getItemExplanation(), colWidth));

            }
            // 출력
            System.out.println(nameLine.toString());
//            System.out.println(effectLine.toString());
            System.out.println(priceLine.toString());
            System.out.println(descLine.toString());
            System.out.println(); // 아이템 목록 끝난 후 빈 줄 추가
        }

    }

    /**
     * 한글은 2칸, 영어/숫자/공백은 1칸으로 계산하여 우측에 공백을 채우는 커스텀 메서드
     */
    private static String padRight(String text, int targetWidth) {
        int currentWidth = 0;

        // 글자마다 실제 콘솔 출력 너비 계산
        for (char c : text.toCharArray()) {
            if (c <= 127) {
                currentWidth += 1; // 영어, 숫자, 기본 기호 및 띄어쓰기는 1칸
            } else {
                currentWidth += 2; // 한글 등은 2칸
            }
        }

        // 목표 너비에서 현재 텍스트 너비를 뺀 만큼 공백 추가
        int padding = targetWidth - currentWidth;
        StringBuilder sb = new StringBuilder(text);
        for (int i = 0; i < padding; i++) {
            sb.append(" ");
        }

        return sb.toString();
    }
  
    public static void printCharacterInfo(CharacterInfoDto characterInfoDto) {
        int addAtk = 0;
        int addDef = 0;

        if(characterInfoDto.getEquiplist().size()>0) {
            for(ItemDto id : characterInfoDto.getEquiplist()) {
                if("무기".equals(id.getItemType())) addAtk = id.getItemIncrease();
                else if("방어구".equals(id.getItemType())) addDef = id.getItemIncrease();
            }
        }

        System.out.println(characterInfoDto.getUserId()+"님의 캐릭터 정보");
        System.out.println();
        System.out.printf("%-5s %-5s %-5s %-5s %-5s %-5s%n", "체력", "마나", "공격력 + 무기", "방어력 + 방어구", "소지금", "스테이지");
        System.out.printf("%-5d %-5d %d + %-5d %d + %-5d %-5d %-5d%n", characterInfoDto.getHp(), characterInfoDto.getMp(), characterInfoDto.getAtk(), addAtk, characterInfoDto.getDef(), addDef, characterInfoDto.getCoin(), 0);
    }


    /**
     * 스테이지 선택 뷰
     */
    public static void printStageSelect(List<StageDto> stageList,  int num){
        for(int i = 0; i<stageList.size(); i++){
            System.out.print("[STAGE " + stageList.get(i).getStageId() + "]");
            if(i<num){
                System.out.print(" - CLEAR");
            }
            System.out.print("\t\t");
        }
        System.out.println();
        System.out.print("[STAGE " + num + "] 입장 하시겠습니까? [Y / N] > ");
    }
    
    /**
     * 공격하기 뷰
     */
    public static void attackView(int userId) {
    	
    }
    /**
     * 방어하기 뷰
     */
    public static void defendView(int userId) {
    	
    }
    /**
     * 아이템 사용하기 뷰
     */
    public static void useItemView(int userId) {
    	
    }
}


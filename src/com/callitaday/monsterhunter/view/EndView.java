package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.BattleController;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import java.util.List;

public class EndView {
    public static void printMessage(String message) {
		    System.out.println(message);
    }

    public static void printNotice(String message){
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║"+BattleView.center(message, 40)+"║"  );
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    public static void printNotice2Line(String message, String message2){
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║"+BattleView.center(message, 40)+"║"  );
        System.out.println("║"+BattleView.center(message2, 40)+"║"  );
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    public static void printVictory(CharacterInfoDto characterInfoDto){
        System.out.println();
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║" + BattleView.center("⚔ 승리 보상 목록", 40) +"║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║"+BattleView.center(characterInfoDto.getStageDto().getRewardCoin() + "Coin", 40)+"║");
        System.out.println("║"+BattleView.center(characterInfoDto.getStageDto().getItemdto().getItemName(), 40)+"║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println();
    }

    /**
     * 아이템 전체 출력
     */
    public static void printAllItem (List<ItemDto> list) {

        int colWidth = 35; // 한 칸의 너비 지정

        // 1. 출력하고 싶은 카테고리 순서를 배열로 미리 지정합니다.
        String[] targetTypes = {"포션", "방어구", "무기", "쿠폰"};
        for(String targetType : targetTypes){
            StringBuilder nameLine = new StringBuilder();
//            StringBuilder effectLine = new StringBuilder();
            StringBuilder priceLine = new StringBuilder();
            StringBuilder descLine = new StringBuilder();
            StringBuilder descLine2 = new StringBuilder();
            boolean b  = false;
            System.out.println("> " + targetType);

            for(ItemDto item: list){
                boolean isTargetType = false;
                if(targetType.equals("포션")){
                    isTargetType = item.getItemType().equals("회복포션") ||
                        item.getItemType().equals("마나포션");
                } else {
                    isTargetType = item.getItemType().equals(targetType);
                }

                if(!isTargetType){
                    continue;
                }
                String itemType = "";
                switch (item.getItemType()){
                    case "회복포션": itemType="HP"; break;
                    case "마나포션": itemType="MP"; break;
                    case "방어구" : itemType="DEF"; break;
                    case "무기" : itemType="ATK"; break;
                    case "쿠폰" : itemType="POWER"; break;
                }

                // 데이터 누적
                nameLine.append(padRight(item.getItemId()+". " +item.getItemName() + " [" + itemType + " +" + item.getItemIncrease() + "]", colWidth));
//                effectLine.append(padRight("효과 " + itemType+  " +" +item.getItemIncrease(), colWidth));
                if(item.getItemType().equals("쿠폰")){
                    priceLine.append(padRight("증표 " + item.getItemPrice() + "개", colWidth));
                }else{
                    String formattedPrice = String.format("%,d COIN", item.getItemPrice());
                    priceLine.append(padRight(formattedPrice, colWidth));
                }
                if(item.getItemType().equals("무기") || item.getItemType().equals("방어구")){
                    String explanation = item.getItemExplanation();
                    if(explanation.contains("F")){
                        String[] part = explanation.split("F");
                        descLine.append(padRight(part[0].trim(), colWidth));
                        descLine2.append(padRight(part[1].trim(), colWidth));
                        b = true;

                    }
                }else if(item.getItemType().equals("쿠폰")){
                    String explanation = item.getItemExplanation();

                    if (explanation != null && explanation.contains("-")) {
                        explanation = explanation.substring(0, explanation.indexOf("-")).trim();
                    }
                    descLine.append(padRight(explanation, colWidth));
                }else{
                    descLine.append(padRight(item.getItemExplanation(), colWidth));
                } 

            }
            // 출력
            System.out.println(nameLine.toString());
//            System.out.println(effectLine.toString());
            System.out.println(priceLine.toString());
            System.out.println(descLine.toString());
            if(b){
                System.out.println(descLine2.toString());
            }
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

    /**
     * 스테이지 선택 뷰
     */
    public static void printStageSelect(List<StageDto> stageList,  int num){
        for(int i = 0; i<5; i++){
            if (i < num - 1) {
                System.out.print("\u001B[1m\u001B[32m[STAGE " + stageList.get(i).getStageId() + "] ✓ CLEAR" + "\u001B[0m" );
                System.out.print("\t\t");
            }else{
                System.out.print("[STAGE " + stageList.get(i).getStageId() + "]");
                System.out.print("\t\t\t");
            }
        }
        System.out.println();
        System.out.println();
        if(num == 6) {
        	System.out.print("모든 스테이지를 클리어하셨습니다. 뒤로 가시려면 Enter 키를 눌러주세요.");
        } else System.out.print("[STAGE " + num + "] 입장하시겠습니까? [Y / N] > ");
    }


    /**
     * 방어 뷰
     */
    public static void defendView(int userId){
        BattleController.defend(userId);
    }


}


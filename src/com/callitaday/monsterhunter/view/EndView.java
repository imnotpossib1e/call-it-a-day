package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;

public class EndView {
	public static void printMessage(String message) {
		System.out.println(message);
	}
	public static void printCharacterInfo(CharactorInfoDto charactorInfoDto) {
		int addAtk = 0;
		int addDef = 0;
		
		if(charactorInfoDto.getEquiplist().size()>0) {
			for(ItemDto id : charactorInfoDto.getEquiplist()) {
				if("무기".equals(id.getItemType())) addAtk = id.getItemIncrease();
				else if("방어구".equals(id.getItemType())) addDef = id.getItemIncrease();
			}
		}
		
		System.out.println(charactorInfoDto.getUserId()+"님의 캐릭터 정보");
		System.out.println();
		System.out.printf("%-5s %-5s %-5s %-5s %-5s %-5s%n", "체력", "마나", "공격력 + 무기", "방어력 + 방어구", "소지금", "스테이지");
		System.out.printf("%-5d %-5d %d + %-5d %d + %-5d %-5d %-5d%n", charactorInfoDto.getHp(), charactorInfoDto.getMp(), charactorInfoDto.getAtk(), addAtk, charactorInfoDto.getDef(), addDef, charactorInfoDto.getCoin(), 0);
	}
}

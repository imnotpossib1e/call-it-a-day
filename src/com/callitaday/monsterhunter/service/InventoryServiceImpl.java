package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.callitaday.monsterhunter.dao.CharactorInfoDao;
import com.callitaday.monsterhunter.dao.CharactorInfoDaoImpl;
import com.callitaday.monsterhunter.dao.InventoryDao;
import com.callitaday.monsterhunter.dao.InventoryDaoImpl;
import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.NotFoundException;

public class InventoryServiceImpl implements InventoryService{
	CharactorInfoDao cID = new CharactorInfoDaoImpl();
	InventoryDao invenD = new InventoryDaoImpl();
			
	/**
	 * 처음 캐릭터 정보 조회
	 * 
	 * -> 캐릭터 스탯에서 공격력과 방어력은 [기본스텟치 (+추가 스탯치)]로 표현
	 * -> 컨트롤에서는 CharactorInfoDto 내의 List<ItemDto> 유무를 통해 java의 view에 어떻게 넣을지 표현
	 * -> 소지 아이템은 ItemDaoImpl의 getItemInfo(userId) 재활용.
	 * */
	@Override
	public CharactorInfoDto loadCharInvenInfo(int userId) throws NotFoundException, SQLException {
		CharactorInfoDto character = cID.getCharactorByUserId(userId);
		if(character==null) {
			throw new NotFoundException("캐릭터 정보를 찾을 수 없습니다. 로그인 정보를 확인해주세요.");
		}
		List<InventoryDto> invenList = invenD.getItemInfo(userId);
		character.setInvenlist(invenList);
		if(invenList.size()>0) {
			List<ItemDto> equipList = new ArrayList<>();
			for(InventoryDto id : invenList) {
				if(id.isEquipped()) equipList.add(id.getItemDto());
			}
			character.setEquiplist(equipList);
		}
		return character;
	}
	
	/**
	 * 소지한 아이템 중 장비 아이템 장착 및 교체 로직
	 * 
	 * -> 
	 * */
	
}

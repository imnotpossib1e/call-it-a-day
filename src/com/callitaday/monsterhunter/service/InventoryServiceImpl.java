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
import com.callitaday.monsterhunter.exception.DuplicatedException;
import com.callitaday.monsterhunter.exception.NotFoundException;

public class InventoryServiceImpl implements InventoryService{
	CharactorInfoDao cID = new CharactorInfoDaoImpl();
	InventoryDao invenD = new InventoryDaoImpl();
	private static InventoryService instance = new InventoryServiceImpl();

    public static InventoryService getInstance(){
        return instance;
    }

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
	 * 아이템 이름을 입력받아 user_id와 함께 매개변수로 받고
	 * 인벤토리를 불러와서 장착된 아이템 존재 여부(F), 존재한다면 같은 타입인지 확인해서
	 * 겹치지 않는 아이템만 장착 상태(T)로 dao에서 update로 변경
	 * */
	@Override
	public void changeEquipStatement(int userId, String itemName) throws DuplicatedException, NotFoundException, SQLException {
		CharactorInfoDto character = loadCharInvenInfo(userId);
		ItemDto findID = null;
		for(InventoryDto invenD : character.getInvenlist()) {
			if(invenD.getItemDto().getItemName().equals(itemName)) findID = invenD.getItemDto();
			else throw new NotFoundException("소지하지 않은 아이템입니다.");
		}
		
		if(character.getEquiplist().size()>0) {			
			for(ItemDto id : character.getEquiplist()){
				if(id.getItemName().equals(itemName)) throw new DuplicatedException();
				// 이름이 중복되지 않고 장착된 아이템 타입(int)가 같다면 dao 로직으로 2번 update 실행(탈착)
				else if(id.getItemType()==findID.getItemType()) {
					invenD.unequipItem(userId, id.getItemId());
					invenD.equipItem(userId, findID.getItemId());
				};
			}
		} else invenD.equipItem(userId, findID.getItemId()); // 장착한 아이템이 없다면
	}
}

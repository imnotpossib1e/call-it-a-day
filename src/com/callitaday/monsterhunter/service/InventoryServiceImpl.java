package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.callitaday.monsterhunter.dao.CharacterInfoDao;
import com.callitaday.monsterhunter.dao.CharacterInfoDaoImpl;
import com.callitaday.monsterhunter.dao.InventoryDao;
import com.callitaday.monsterhunter.dao.InventoryDaoImpl;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.DuplicatedException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.SearchWrongException;

public class InventoryServiceImpl implements InventoryService{
	CharacterInfoDao cID = new CharacterInfoDaoImpl();
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
	public CharacterInfoDto loadCharInvenInfo(int userId) throws NotFoundException, SQLException {
		CharacterInfoDto character = cID.getCharacterByUserId(userId);
		if(character==null) {
			throw new NotFoundException("캐릭터 정보를 찾을 수 없습니다. 로그인 정보를 확인해주세요.");
		}
		try {
			List<InventoryDto> invenList = loadInventoryInfo(userId);
			character.setInvenlist(invenList);
			if(invenList.size()>0) {
				List<ItemDto> equipList = new ArrayList<>();
				for(InventoryDto id : invenList) {
					if(id.isEquipped()) equipList.add(id.getItemDto());
				}
				character.setEquiplist(equipList);
			}
		} catch(NotFoundException e) {
			return character;
		}
		return character;
	}
	
	/**
	 * 소지한 아이템 목록 조회
	 * */
	@Override
	public List<InventoryDto> loadInventoryInfo(int userId) throws NotFoundException, SQLException {
		List<InventoryDto> invenList = invenD.getItemInfo(userId);
		if(invenList.size() == 0) throw new NotFoundException("소지한 아이템이 없습니다.");
		return invenList;
	}

	/**
	 * 소지한 아이템 중 장비 아이템 장착 및 교체 로직
	 * 
	 * 아이템 이름을 입력받아 user_id와 함께 매개변수로 받고
	 * 인벤토리를 불러와서 장착된 아이템 존재 여부(F), 존재한다면 같은 타입인지 확인해서
	 * 겹치지 않는 아이템만 장착 상태(T)로 dao에서 update로 변경
	 * */
	@Override
	public String changeEquipStatement(int userId, String itemName) throws SearchWrongException, DuplicatedException, NotFoundException, SQLException {
		CharacterInfoDto character = loadCharInvenInfo(userId);
		ItemDto findID = null;
		String result = "장착";
		for (InventoryDto invenDto : character.getInvenlist()) {
		    if (itemName.equals(invenDto.getItemDto().getItemName())) {
		        if ("무기".equals(invenDto.getItemDto().getItemType())
		                || "방어구".equals(invenDto.getItemDto().getItemType())) {
		            findID = invenDto.getItemDto();
		            break;
		        } else {
		            throw new SearchWrongException("장비 아이템이 아닙니다.");
		        }
		    }
		}

		if (findID == null) {
		    throw new NotFoundException("소지하지 않은 아이템입니다.");
		}
		
		System.out.println(findID);
		
		if(character.getEquiplist().size()>0) {
			for(ItemDto id : character.getEquiplist()){
				if(itemName.equals(id.getItemName())) throw new DuplicatedException("이미 장착 중인 아이템입니다.");
				// 이름이 중복되지 않고 장착된 아이템 타입(int)가 같다면 dao 로직으로 2번 update 실행(탈착)
				else if(findID.getItemType().equals(id.getItemType())) {
					invenD.unequipItem(userId, id.getItemId());
					invenD.equipItem(userId, findID.getItemId());
					result = "교체";
					break;
				};
			}
			invenD.equipItem(userId, findID.getItemId()); // 장착 리스트에 같은 타입의 아이템이 없으니 입력받은 아이템 장착
		} else invenD.equipItem(userId, findID.getItemId()); // 장착한 아이템이 없다면
		return result;
	}
	
	/**
	 * 장착한 아이템 헤제
	 * 
	 * 아이템 이름을 입력받아 user_id와 함께 매개변수로 받고
	 * CharactorInfoDto 내의 equiplist가 비어있으면 NotFoundException
	 * 있다면 입력받은 아이템 이름으로 일치하는 ItemDto를 찾아
	 * InventoryDaoImpl.unequipItem()
	 * */
	@Override
	public String unequipStatement(int userId, String itemName) throws NotFoundException, SQLException {
		CharacterInfoDto character = loadCharInvenInfo(userId);
		int result = 0;
		if(character.getEquiplist().size()>0) {
			for(ItemDto id : character.getEquiplist()){
				if(itemName.equals(id.getItemName())) result = invenD.unequipItem(userId, id.getItemId());
				}
			if(result == 0) throw new NotFoundException("입력하신 아이템을 장착하고 있지 않습니다.");
			} else throw new NotFoundException("장착 중인 아이템이 없습니다."); // 장착한 아이템이 없다면
		return itemName + " 장비를 헤제합니다.";
	}

	/**
	 * 소지한 아이템 목록 중 입력받은 아이템 타입에 해당하는 아이템들만 조회
	 * */
	@Override
	public List<InventoryDto> loadInventoryByItemTypeInfo(int userId, String itemType)
			throws NotFoundException, SQLException {
		List<InventoryDto> invenList = invenD.getItemByItemTypeInfo(userId, itemType);
		if(invenList.size() == 0) throw new NotFoundException("소지한 아이템이 없습니다.");
		return invenList;
	}
}

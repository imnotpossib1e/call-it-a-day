package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.DuplicatedException;
import com.callitaday.monsterhunter.exception.NotFoundException;

public interface InventoryService {
	/**
	 * 유저의 id 및 캐릭터 정보 조회(sql의 characterInfo + inventory + item 필요, java에서 dto의 characterInfoDto 필요)
	 * -> InventoryDaoImpl.getItemInfo(user_id), CharactorInfoDaoImpl.getCharactorByUserId(user_id)를 서비스에서 조합하여 사용.
	 * */
	public CharactorInfoDto loadCharInvenInfo(int userId) throws NotFoundException, SQLException;
	
	/**
	 * 소지한 아이템 목록 조회
	 * */
	public List<InventoryDto> loadInventoryInfo(int userId) throws NotFoundException, SQLException;
	
	/**
	 * 소지한 아이템 중 장비 아이템 장착 및 교체 로직
	 * 
	 * 아이템 이름을 입력받아 user_id와 함께 매개변수로 받고
	 * 인벤토리를 불러와서 장착된 아이템 존재 여부(F), 존재한다면 같은 타입인지 확인해서
	 * 겹치지 않는 아이템만 장착 상태(T)로 dao에서 update로 변경
	 * */
	public String changeEquipStatement(int userId, String itemName) throws DuplicatedException, NotFoundException, SQLException;
}

package com.callitaday.monsterhunter.service;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.NotFoundException;

public interface InventoryService {
	/**
	 * 유저의 id 및 캐릭터 정보 조회(sql의 characterInfo + inventory + item 필요, java에서 dto의 characterInfoDto 필요)
	 * -> 작성해두신 ItemDaoImpl.getItemInfo(user_id), CharactorInfoDaoImpl.getCharactorByUserId(user_id)를 서비스에서 조합하여 사용.
	 * */
	public CharactorInfoDto loadCharInvenInfo(int userId) throws NotFoundException, SQLException;	
	
	/**
	 * 아이템 이름을 입력받아 user_id와 함께 매개변수로 받고 dao에서 update로 
	 * */
}

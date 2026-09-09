package com.callitaday.monsterhunter.dao;

import java.sql.SQLException;
import java.util.List;

import com.callitaday.monsterhunter.dto.InventoryDto;

public interface InventoryDao {
	/**
	 * 유저의 id 및 캐릭터 정보 조회(sql의 characterInfo + inventory + item 필요, java에서 dto의 characterInfoDto 필요)
	 * -> 작성해두신 ItemDaoImpl.getItemInfo(user_id),
	 * CharactorInfoDaoImpl.getCharactorByUserId(user_id)를 서비스에서 조합하여 사용.
	 * 컨트롤에서 유저 정보만 
	 * */
	public List<InventoryDto> getItemInfo(int userId) throws SQLException;
	/**
	 * 장착한 아이템 조회(sql의 inventory + item + item_type 필요)
	 * */
}

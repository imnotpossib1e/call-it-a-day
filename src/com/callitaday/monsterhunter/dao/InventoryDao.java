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
	 * 아이템 장착
	 * user_id와 item_id를 매개변수로 받아서 inventory 테이블에서 update로 변경
	 * update inventory set is_equipped = T where user_id = ? and item_id = ?
	 * */
	
	public int equipItem(int user_id, int item_id) throws SQLException;
	
	/**
	 * 아이템 탈착
	 * user_id와 item_id를 매개변수로 받아서 inventory 테이블에서 update로 변경
	 * update inventory set is_equipped = F where user_id = ? and item_id = ?
	 * */
	public int unequipItem(int user_id, int item_id) throws SQLException;


	/**
	 * 아이템 보유 수량 체크
	 */
	public InventoryDto getItemQuantity(int user_id, int item_id) throws SQLException;

}

package com.callitaday.monsterhunter.dao;

public interface InventoryDao {
	/**
	 * 유저의 id 및 캐릭터 정보 조회(sql의 characterInfo + inventory + item 필요, java에서 dto의 characterInfoDto 필요)
	 * */
	public void characterInfoLoad();
	
	/**
	 * 장착한 장비 아이템 조회(장비 이름, 스탯 변경치 / sql의 characterInfo + inventory + item 필요)
	 * */
	public void equipItemInfoLoad();
	
	/**
	 * 소지한 아이템 조회(sql의 inventory + item + item_type 필요)
	 * */
	public void existItemInfoLoad();
}

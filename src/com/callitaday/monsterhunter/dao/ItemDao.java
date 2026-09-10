package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    /**
     * 내가 보유한 아이템 조회
     *
     * Select
     *
     * @param userId
     */
    public List<InventoryDto> getItemInfo(int userId) throws SQLException;

    /**
     * 아이템 단일 조회
     */
    public ItemDto getItemByItemId(int item_id) throws SQLException;


    /**
     * 전체 아이템 조회
     */
    public List<ItemDto> getAllItemInfo() throws SQLException;
}

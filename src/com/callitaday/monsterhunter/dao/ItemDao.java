package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ItemDao {
    /**
     * 아이템 단일 조회
     */
    public ItemDto getItemByItemId(int item_id) throws SQLException;


    /**
     * 전체 아이템 조회
     */
    public List<ItemDto> getAllItemInfo() throws SQLException;
}

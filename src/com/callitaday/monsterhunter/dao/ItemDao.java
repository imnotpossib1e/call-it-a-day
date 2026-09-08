package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.ItemDto;
import java.util.List;

public interface ItemDao {

    /**
     * 플아이템 구매
     *
     * 인벤토리: Insert
     * 코인 : update
     */
    public int getItemPurchase(int itemId);

    /**
     * 내가 보유한 아이템 조회
     *
     * Select
     */
    public List<ItemDto> getItemInfo(int userId);

    /**
     * 전체 아이템 조회
     */
    public List<ItemDto> getAllItemInfo();


}

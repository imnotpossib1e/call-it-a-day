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
     * 플아이템 구매
     *
     * 인벤토리: Insert
     * 코인 : update
     */
    public int purchaseItem(int user_id, int quantity, int item_id) throws SQLException, SQLException, AddException, ModifyException, PurchaseFailException, NotFoundException;

    /**
     * 내가 보유한 아이템 조회
     *
     * Select
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


    int getTotalAmount(int itemId, int quantity) throws SQLException;

    public int updateUserCoinPay(Connection con, CharactorInfoDto charactorInfoDto, int totalAmount) throws SQLException;
}

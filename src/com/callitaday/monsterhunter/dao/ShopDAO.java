package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import java.sql.Connection;
import java.sql.SQLException;

public interface ShopDAO {
    /**
     * 플아이템 구매
     *
     * 인벤토리: Insert
     * 코인 : update
     */
    public int purchaseItem(CharacterInfoDto characterInfoDto, int quantity, int item_id, int totalAmount, InventoryDto inventoryDto) throws SQLException, SQLException, AddException, ModifyException, PurchaseFailException, NotFoundException;

    /**
     * 코인 차감
     * @param con
     * @param characterInfoDto
     * @param totalAmount
     * @return
     * @throws SQLException
     */
    public int updateUserCoinPay(Connection con, CharacterInfoDto characterInfoDto, int totalAmount) throws SQLException;
}

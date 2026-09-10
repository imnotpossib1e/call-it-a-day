package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
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
    public int purchaseItem(int user_id, int quantity, int item_id) throws SQLException, SQLException, AddException, ModifyException, PurchaseFailException, NotFoundException;

    /**
     * 총 결제 금액
     * @param itemId
     * @param quantity
     * @return
     * @throws SQLException
     */
    int getTotalAmount(int itemId, int quantity) throws SQLException;

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

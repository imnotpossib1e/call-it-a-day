package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import java.sql.SQLException;

public interface ShopService {

    /**
     * 상점 아이템 구매
     */
    public void purchaseItem(int user_id, int item_id, int quantity) throws AddException, ModifyException, PurchaseFailException, NotFoundException;

    /**
     * 상점 구매 유효성 검사
     */
    public void validatePurchase (CharacterInfoDto characterInfoDto, InventoryDto inventoryDto, int itemId, int quantity) throws PurchaseFailException, SQLException;
}

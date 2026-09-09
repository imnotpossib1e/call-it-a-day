package com.callitaday.monsterhunter.controller;

import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import com.callitaday.monsterhunter.service.ShopService;
import com.callitaday.monsterhunter.service.ShopServiceImpl;
import com.callitaday.monsterhunter.view.FailView;

public class ShopController {
    private static ShopService service = ShopServiceImpl.getInstance();

    /**
     * 아이템 구매
     */
    public static void purchaceItem(int user_id, int item_id, int quantity){
        try{
            service.purchaseItem(user_id,  item_id, quantity);
        }catch (PurchaseFailException | AddException | ModifyException | NotFoundException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

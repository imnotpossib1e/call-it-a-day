package com.callitaday.monsterhunter.controller;


import java.sql.SQLException;

import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.InventoryService;
import com.callitaday.monsterhunter.service.InventoryServiceImpl;
import com.callitaday.monsterhunter.view.FailView;

public class InventoryController {
	private static InventoryService service = InventoryServiceImpl.getInstance();

    /**
     * 아이템 구매
     */
    public static void getCharacterInfo(int user_id){
        try{
        	System.out.println(service.loadCharInvenInfo(user_id));
        }catch (NotFoundException | SQLException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

package com.callitaday.monsterhunter.controller;


import java.sql.SQLException;

import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.InventoryService;
import com.callitaday.monsterhunter.service.InventoryServiceImpl;
import com.callitaday.monsterhunter.view.EndView;
import com.callitaday.monsterhunter.view.FailView;
import com.callitaday.monsterhunter.view.InvenView;

public class InventoryController {
	private static InventoryService service = InventoryServiceImpl.getInstance();
	
	/**
	 * 캐릭터 정보 (+ 장착한 아이템 스탯 표시)
	 * */
    public static void getCharacterInfo(int user_id){
        try{
        	InvenView.printCharacterInfo(service.loadCharInvenInfo(user_id));
        }catch (NotFoundException | SQLException e){
            FailView.errorMessage(e.getMessage());
        }
    }
    
    
    /**
	 * 소지한 아이템 목록 조회
	 * */
    public static void getInventoryInfo(int user_id){
        try{
        	InvenView.printInventoryInfo(service.loadInventoryInfo(user_id));
        }catch (NotFoundException | SQLException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

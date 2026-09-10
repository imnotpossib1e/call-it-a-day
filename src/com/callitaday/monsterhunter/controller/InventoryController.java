package com.callitaday.monsterhunter.controller;


import java.sql.SQLException;

import com.callitaday.monsterhunter.exception.DuplicatedException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.SearchWrongException;
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
    
    /**
	 * 소지 아이템에서 선택한 아이템 이름을 입력받아 장착 또는 교체
	 * */
    public static void equipItem(int user_id, String itemName){
        try{
        	String result = "아이템을 " + service.changeEquipStatement(user_id, itemName) + "하였습니다.";
        	EndView.printMessage(result);
        }catch (SearchWrongException | NotFoundException | DuplicatedException | SQLException e){
            FailView.errorMessage(e.getMessage());
        }
    }
    
    /**
	 * 장착 중인 아이템 이름을 입력받아 탈착
	 * */
    public static void unequipItem(int user_id, String itemName){
        try{
        	EndView.printMessage(service.unequipStatement(user_id, itemName));
        }catch (NotFoundException | DuplicatedException | SQLException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

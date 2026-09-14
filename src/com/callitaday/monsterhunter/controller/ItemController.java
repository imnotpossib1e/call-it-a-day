package com.callitaday.monsterhunter.controller;

import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.ItemService;
import com.callitaday.monsterhunter.service.ItemServiceImpl;
import com.callitaday.monsterhunter.view.EndView;
import com.callitaday.monsterhunter.view.FailView;
import java.util.List;

public class ItemController {
    private static ItemService service = ItemServiceImpl.getInstance();

    /**
     * 전체 아이템 조회
     */
    public static void selectAllItem(){
        try{
            List<ItemDto> list = service.selectAllItem();
            EndView.printAllItem(list);
        }catch (NotFoundException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

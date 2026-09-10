package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dao.ItemDao;
import com.callitaday.monsterhunter.dao.ItemDaoImpl;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.sql.SQLException;
import java.util.List;

public class ItemServiceImpl implements ItemService{
    ItemDao itemDao = new ItemDaoImpl();
    private static ItemService instance = new ItemServiceImpl();

    public static ItemService getInstance(){
        return instance;
    }

    /**
     * 전체 아이템 목록 불러오기
     */
    @Override
    public List<ItemDto> selectAllItem() throws NotFoundException {
        List<ItemDto> list = null;
        try{
            list = itemDao.getAllItemInfo();
            if(list == null || list.size() == 0) throw new NotFoundException("아이템 목록을 불러올 수 없습니다.");
        }catch (SQLException e){
            throw new NotFoundException("아이템 목록을 불러올 수 없습니다.");
        }

        return list;
    }

    /**
     * 단일 아이템 정보 불러오기
     */
    @Override
    public ItemDto selectItemById() throws NotFoundException {
        return null;
    }
}

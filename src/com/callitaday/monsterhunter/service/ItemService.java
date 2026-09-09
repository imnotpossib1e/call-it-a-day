package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.util.List;

public interface ItemService {
    /**
     * 전체 아이템 목록 불러오기
     */
    public List<ItemDto> selectAllItem() throws NotFoundException;

    /**
     * 단일 아이템 정보 불러오기
     */
    public ItemDto selectItemById() throws NotFoundException;
}

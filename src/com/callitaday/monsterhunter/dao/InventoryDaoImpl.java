package com.callitaday.monsterhunter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.util.DbManager;

public class InventoryDaoImpl implements InventoryDao {
//	CharactorInfoDao charactorInfoDao = new CharactorInfoDaoImpl();
	/**
     * 내가 보유한 아이템 조회
     *
     * Select
     *
     * @param userId
     */
    @Override
    public List<InventoryDto> getItemInfo(int userId) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from v_user_inventory where user_id = ?";
        List<InventoryDto> list = new ArrayList<InventoryDto>();

        try{
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while(rs.next()){
                InventoryDto inventoryDto = new InventoryDto();
                inventoryDto.setUserId(rs.getInt("user_id"));
                inventoryDto.setQuantity(rs.getInt("quantity"));
                inventoryDto.setEquipped("T".equals(rs.getString("is_equipped")));
                ItemDto itemDto = new ItemDto();
                itemDto.setItemName(rs.getString("item_name"));
                itemDto.setItemIncrease(rs.getInt("item_increase"));
                itemDto.setItemExplanation(rs.getString("item_explanation"));
                itemDto.setItemType(rs.getString("item_type"));
                inventoryDto.setItemDto(itemDto);
                list.add(inventoryDto);
            }
        }
        finally {
            DbManager.dbClose(con, ps, rs);
        }

        return list;
    }

    /**
     * 아이템 보유 수량 체크
     *
     * @param user_id
     * @param item_id
     */
    @Override
    public InventoryDto getItemQuantity(int user_id, int item_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from inventory where user_id=? and item_id = ?";

        InventoryDto inventoryDto = null;

        try{
            con= DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, item_id);
            rs = ps.executeQuery();

            if(rs.next()){
                inventoryDto = new InventoryDto(rs.getInt("user_id"), rs.getInt("quantity"), "T".equals(rs.getString("is_equipped")), rs.getInt("item_id"));
            }
        }finally {
            DbManager.dbClose(con, ps, rs);
        }
        return inventoryDto;
    }
}

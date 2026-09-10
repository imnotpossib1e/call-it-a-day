package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import com.callitaday.monsterhunter.util.DbManager;
import com.mysql.cj.jdbc.exceptions.NotUpdatable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    CharactorInfoDao charactorInfoDao = new CharactorInfoDaoImpl();

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
     * 아이템 단일 조회
     *
     * @param item_id
     */
    @Override
    public ItemDto getItemByItemId(int item_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from item where item_id = ?";
        ItemDto itemDto = null;

        try{
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, item_id);
            rs = ps.executeQuery();
            if(rs.next()){
                itemDto = new ItemDto(rs.getInt("item_id"), rs.getString("item_name"), rs.getInt("item_price"), rs.getInt("item_increase"), rs.getString("item_explanation"), rs.getString("item_type"));
            }
        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return itemDto;
    }


    /**
     * 전체 아이템 조회
     */
    @Override
    public List<ItemDto> getAllItemInfo() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from item";
        List<ItemDto> list = new ArrayList<ItemDto>();

        try{
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                ItemDto itemdto = new ItemDto(
                    rs.getInt("item_id"),
                    rs.getString("item_name"),
                    rs.getInt("item_price"),
                    rs.getInt("item_increase"),
                    rs.getString("item_explanation"),
                    rs.getString("item_type") );
                list.add(itemdto);
            }
        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return list;
    }

}

package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    /**
     * 아이템 구매
     *
     * 인벤토리: Insert
     * 코인 : update
     *
     * @param itemId
     */
    @Override
    public int getItemPurchase(int itemId) {

        return 0;
    }

    /**
     * 내가 보유한 아이템 조회
     *
     * Select
     *
     * @param userId
     */
    /**
     * 내가 보유한 아이템 조회
     *
     * Select
     *
     * @param userId
     */
    @Override
    public List<ItemDto> getItemInfo(int userId) {
        return List.of();
    }

    /*
    @Override
    public List<InventoryDto> getItemInfo(int userId) {
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
                inventoryDto.setIsEquipped(rs.getInt("is_equipped"));
                ItemDto itemDto = new ItemDto();
                itemDto.setItemName(rs.getString("item_name"));
                itemDto.setItemIncrease(rs.getInt("item_increase"));
                itemDto.setItemType(rs.getInt("item_type"));
                list.add(inventoryDto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            DbManager.dbClose(con, ps, rs);
        }

        return list;
    }
    */

    /**
     * 전체 아이템 조회
     */
    @Override
    public List<ItemDto> getAllItemInfo() {
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
                    rs.getInt("item_type") );
                list.add(itemdto);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return list;
    }
}

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
     * 아이템 구매
     *
     * 인벤토리에 아이템 추가: Insert
     * 코인 감소: update
     * @param user_id
     * @param quantity
     * @param item_id
     */
    @Override
    public int purchaseItem(int user_id, int quantity, int item_id) throws SQLException, AddException, ModifyException,PurchaseFailException, NotFoundException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "insert into inventory(user_id, quantity, is_equipped, item_id) values(?, ?, 'F', ?) "
            + "on duplicate key update quantity = quantity + ?";
        int result = 0;

        try{
            con=DbManager.getConnection();
            con.setAutoCommit(false); // 자동 커밋 끄기

            ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            ps.setInt(2, quantity);
            ps.setInt(3, item_id);
            ps.setInt(4, quantity);

            // 회원 정보 찾기
            CharactorInfoDto charactorInfoDto = null;

            charactorInfoDto = charactorInfoDao.getCharactorByUserId(user_id);
            if(charactorInfoDto == null){
                con.rollback();
                throw new NotFoundException( "유저 정보를 찾을 수 없습니다.");
            }

            // 구매 총액 계산
            int totalAmount = this.getTotalAmount(item_id, quantity);

            // 인벤토리에 추가
            result = ps.executeUpdate();

            // 인벤토리 추가 실패
            if(result == 0){
                con.rollback();
                throw new AddException("인벤토리 추가에 실패했습니다.");
            }else{ // 인벤토리 추가 성공시
                // 유저의 코인보다 구매 총액이 클 때
                if(charactorInfoDto.getCoin() < totalAmount){
                    con.rollback();
                    throw new PurchaseFailException("코인 차감에 실패했습니다..");
                }

                // 코인 차감
                int re = this.updateUserCoinPay(con, charactorInfoDto, totalAmount);
                if(re == 0){
                    con.rollback();
                    throw new ModifyException("결제에 실패했습니다.");
                }

                con.commit();
            }
        }catch (SQLException e){
            throw new SQLException("DB에 문제가 발생했습니다.");
        } finally {
            DbManager.dbClose(con, ps);
        }
        return result;
    }

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
                itemDto.setItemType(rs.getInt("item_type"));
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
                itemDto = new ItemDto(rs.getInt("item_id"), rs.getString("item_name"), rs.getInt("item_price"), rs.getInt("item_increase"), rs.getInt("item_type"));
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
                    rs.getInt("item_type") );
                list.add(itemdto);
            }
        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return list;
    }

    /**
     * 총 결제 금액 계산
     */
    public int getTotalAmount(int item_id, int quantity) throws SQLException{
        int total = 0;
        ItemDto itemDto = this.getItemByItemId(item_id);
        if(itemDto == null){
            throw new SQLException("해당하는 아이템이 없습니다.");
        }

        total = quantity * itemDto.getItemPrice();

        return total;
    }

    @Override
    public int updateUserCoinPay(Connection con, CharactorInfoDto charactorInfoDto, int totalAmount) throws SQLException {
        PreparedStatement ps = null;

        int resultCoin = charactorInfoDto.getCoin() -totalAmount;

        String sql = "update charactor_info set coin=? where user_id = ? ";
        int result = 0;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, resultCoin);
            ps.setInt(2, charactorInfoDto.getUserId());
            result = ps.executeUpdate();

        }
        finally {
            DbManager.dbClose(null, ps);
        }

        return result;
    }
}

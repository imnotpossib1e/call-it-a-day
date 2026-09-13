package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopDaoImpl implements ShopDAO{
    StageDao stageDao = new StageDaoImpl(); // 아이템 사용
    private static final int COUPON_ID = 7;


    @Override
    public int purchaseItem(CharacterInfoDto characterInfoDto, int quantity, int itemId, int totalAmount, InventoryDto inventoryDto) throws SQLException, AddException, ModifyException, PurchaseFailException, NotFoundException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "insert into inventory(user_id, quantity, is_equipped, item_id) values(?, ?, 'F', ?) "
            + "on duplicate key update quantity = quantity + ?";
        int result = 0;

        try{
            con= DbManager.getConnection();
            con.setAutoCommit(false); // 자동 커밋 끄기

            ps = con.prepareStatement(sql);
            ps.setInt(1, characterInfoDto.getUserId());
            ps.setInt(2, quantity);
            ps.setInt(3, itemId);
            ps.setInt(4, quantity);


            // 인벤토리에 추가
            result = ps.executeUpdate();

            // 인벤토리 추가 실패
            if(result == 0){
                con.rollback();
                throw new AddException("인벤토리 추가에 실패했습니다.");
            }else{ // 인벤토리 추가 성공시
                if(itemId == COUPON_ID){
                    // 증표 차감
                    for(int i = 1; i<=5; i++){
                        int re = stageDao.useItem(characterInfoDto.getUserId(), 100+i);
                        if(re == 0){
                            con.rollback();
                            throw new ModifyException("구매에 실패했습니다");
                        }
                    }
                }else{ // 일반 아이템의 경우

                    // 코인 차감
                    int re = this.updateUserCoinPay(con, characterInfoDto, totalAmount);
                    if(re == 0){
                        con.rollback();
                        throw new ModifyException("결제에 실패했습니다.");
                    }
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


    @Override
    public int updateUserCoinPay(Connection con, CharacterInfoDto characterInfoDto, int totalAmount) throws SQLException {
        PreparedStatement ps = null;

        int resultCoin = characterInfoDto.getCoin() - totalAmount;

        String sql = "update character_info set coin=? where user_id = ? ";
        int result = 0;
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, resultCoin);
            ps.setInt(2, characterInfoDto.getUserId());
            result = ps.executeUpdate();

        }
        finally {
            DbManager.dbClose(null, ps);
        }

        return result;
    }
}

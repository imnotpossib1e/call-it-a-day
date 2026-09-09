package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopDaoImpl implements ShopDAO{
    CharactorInfoDao charactorInfoDao = new CharactorInfoDaoImpl();
    ItemDao itemDao = new ItemDaoImpl();


    @Override
    public int purchaseItem(int user_id, int quantity, int item_id) throws SQLException, AddException, ModifyException, PurchaseFailException, NotFoundException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "insert into inventory(user_id, quantity, is_equipped, item_id) values(?, ?, 'F', ?) "
            + "on duplicate key update quantity = quantity + ?";
        int result = 0;

        try{
            con= DbManager.getConnection();
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
     * 총 결제 금액 계산
     */
    public int getTotalAmount(int item_id, int quantity) throws SQLException{
        int total = 0;
        ItemDto itemDto = itemDao.getItemByItemId(item_id);
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

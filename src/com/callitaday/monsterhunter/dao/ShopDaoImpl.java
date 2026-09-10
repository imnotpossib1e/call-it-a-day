package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.dto.ItemDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopDaoImpl implements ShopDAO{
    CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
    ItemDao itemDao = new ItemDaoImpl();
    InventoryDao inventoryDao = new InventoryDaoImpl();
    StageDao stageDao = new StageDaoImpl(); // 아이템 사용


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
            CharacterInfoDto characterInfoDto = null;


            characterInfoDto = characterInfoDao.getCharacterByUserId(user_id);
            if(characterInfoDto == null){
                con.rollback();
                throw new NotFoundException( "유저 정보를 찾을 수 없습니다.");
            }

            // 인벤토리에 해당 아이템이 있는지 체크(quantity 체크)
            InventoryDto inventoryDto = inventoryDao.getItemQuantity(user_id, item_id);
            // 아이템 타입 체크
            ItemDto itemDto = itemDao.getItemByItemId(item_id);
            if(inventoryDto != null){
                // 무기, 방어구 타입 아이템의 수량이 1을 넘어서는 경우 에러 발생
                // 쿠폰을 하나 이상 구매하는 경우 에러 발생
                if(itemDto.getItemType().equals("무기") || itemDto.getItemType().equals("방어구")){

                    if(inventoryDto.getQuantity()+quantity > 1){
                        System.out.println(inventoryDto.getQuantity()+quantity);
                        throw new PurchaseFailException("무기와 방어구는 하나만 보유할 수 있습니다.");
                    }
                    throw new PurchaseFailException("무기와 방어구는 하나만 보유할 수 있습니다.");
                }else if(itemDto.getItemType().equals("쿠폰")){
                    if(inventoryDto.getQuantity()+quantity > 1 ){
                        System.out.println(inventoryDto.getQuantity()+quantity);
                        throw new PurchaseFailException("쿠폰은 하나만 보유할 수 있습니다.");
                    }
                }
            }else if(item_id==7 && quantity >1){
                throw new PurchaseFailException("쿠폰은 하나만 보유할 수 있습니다.");
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
                if(item_id==7){
                    int count = 0;
                    // 증표 5개 보유하는지 확인
                    for(int i = 1; i<=5; i++){
                        InventoryDto checkToken = inventoryDao.getItemQuantity(user_id, 100+i);
                        // 증표가 있는 경우
                        if(checkToken != null && checkToken.getQuantity() >= 1){
                            count++;
                        }
                    }

                    // 증표가 다 있는 경우
                    if(count == 5){
                        // 증표 차감
                        for(int i = 1; i<=5; i++){
                            int re = stageDao.useItem(user_id, 100+i);
                            if(re == 0){
                                con.rollback();
                                throw new ModifyException("구매에 실패했습니다");
                            }
                        }

                    }else{
                        // 증표가 다 없는 경우
                        con.rollback();
                        throw new PurchaseFailException("증표가 부족합니다.");
                    }
                }else{
                    // 일반 아이템의 경우
                    // 유저의 코인보다 구매 총액이 클 때
                    if(characterInfoDto.getCoin() < totalAmount){
                        con.rollback();
                        throw new PurchaseFailException("코인 차감에 실패했습니다..");
                    }

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
    public int updateUserCoinPay(Connection con, CharacterInfoDto characterInfoDto, int totalAmount) throws SQLException {
        PreparedStatement ps = null;

        int resultCoin = characterInfoDto.getCoin() -totalAmount;

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

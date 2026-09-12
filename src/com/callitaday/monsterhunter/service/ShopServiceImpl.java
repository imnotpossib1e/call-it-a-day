package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dao.CharacterInfoDao;
import com.callitaday.monsterhunter.dao.CharacterInfoDaoImpl;
import com.callitaday.monsterhunter.dao.InventoryDao;
import com.callitaday.monsterhunter.dao.InventoryDaoImpl;
import com.callitaday.monsterhunter.dao.ItemDao;
import com.callitaday.monsterhunter.dao.ItemDaoImpl;
import com.callitaday.monsterhunter.dao.ShopDAO;
import com.callitaday.monsterhunter.dao.ShopDaoImpl;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.InventoryDto;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import java.sql.SQLException;

public class ShopServiceImpl implements ShopService{
    // 상수 정의 - 방어구 / 무기
    private static final int ARMOR_1 = 3;
    private static final int ARMOR_2 = 4;
    private static final int SWORD_1= 5;
    private static final int SWORD_2 = 6;

    // 상수 정의 - 쿠폰 아이템 번호
    private static final int COUPON_ID = 7;



    ShopDAO shopDao = new ShopDaoImpl();
    CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
    InventoryDao inventoryDao = new InventoryDaoImpl();
    ItemDao itemDao = new ItemDaoImpl();


    private static ShopService instance = new ShopServiceImpl();

    public static ShopService getInstance(){
        return instance;
    }

    /**
     * 상점 아이템 구매
     */
    @Override
    public void purchaseItem(int userId, int itemId, int quantity) throws AddException, ModifyException,PurchaseFailException, NotFoundException {
        try{
            // 회원 정보 유효성 검사
            CharacterInfoDto characterInfoDto = characterInfoDao.getCharacterByUserId(userId);
            if(characterInfoDto == null){
                throw new NotFoundException("유저 정보를 찾을 수 없습니다.");
            }

            // 수량 확인 로직
            InventoryDto inventoryDto = inventoryDao.getItemQuantity(userId, itemId);

            // 구매 조건 로직
            this.validatePurchase(characterInfoDto, inventoryDto, itemId, quantity);

            // 구매 총액 계산
            int totalAmount = itemDao.getItemByItemId(itemId).getItemPrice() * quantity;

            if(characterInfoDto.getCoin() < totalAmount){
                throw new PurchaseFailException("코인이 부족합니다.");
            }

            // 구매 요건 충족

            // 구매 로직
            int result = shopDao.purchaseItem(characterInfoDto, quantity, itemId, totalAmount, inventoryDto);
            if(result == 0){
                throw new PurchaseFailException("구매 처리 중 오류가 발생했습니다");
            }
        }catch (AddException e){
            throw new AddException(e.getMessage());

        }catch (ModifyException e){
            throw new ModifyException(e.getMessage());
        }catch (NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch (PurchaseFailException | SQLException e){
            throw new PurchaseFailException(e.getMessage());
        }
    }

    /**
     * 상점 구매 유효성 검사
     */
    @Override
    public void validatePurchase(CharacterInfoDto characterInfoDto, InventoryDto inventoryDto, int itemId, int quantity)
        throws PurchaseFailException, SQLException {
        // 무기, 방어구 타입 아이템의 수량이 1을 넘어서는 경우 에러 발생
        // 무기, 방어구 레벨 별 구매 제한
        if(itemId == ARMOR_1 || itemId == SWORD_1){
            if(characterInfoDto.getStage_id() < 2){
                throw new PurchaseFailException("해당 아이템은 스테이지 2를 클리어한 이후에 구매하실 수 있습니다.");
            }
            if(inventoryDto !=null && inventoryDto.getQuantity()+quantity > 1){
                throw new PurchaseFailException("무기와 방어구는 하나만 보유할 수 있습니다.");
            }
        }
        if(itemId == ARMOR_2 || itemId == SWORD_2){
            if(characterInfoDto.getStage_id()<4){
                throw new PurchaseFailException("해당 아이템은 스테이지 4를 클리어한 이후에 구매하실 수 있습니다.");
            }
            if(inventoryDto !=null && inventoryDto.getQuantity()+quantity > 1){
                throw new PurchaseFailException("무기와 방어구는 하나만 보유할 수 있습니다.");
            }
        }

        // 쿠폰 조건 로직
        if(itemId == COUPON_ID){
            // 쿠폰을 하나 이상 구매하는 경우 에러 발생
            if(inventoryDto !=null && inventoryDto.getQuantity()+quantity > 1 ){
                throw new PurchaseFailException("쿠폰은 하나만 보유할 수 있습니다.");
            }

            // 증표 개수 확인
            for(int i = 1; i <=5; i++){
                InventoryDto token = inventoryDao.getItemQuantity(characterInfoDto.getUserId(), 100+i);
                if (token == null || token.getQuantity() >= 1){
                    throw new PurchaseFailException("증표가 부족합니다.");
                }
            }
        }

    }
}

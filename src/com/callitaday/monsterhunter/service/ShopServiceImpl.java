package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dao.ItemDao;
import com.callitaday.monsterhunter.dao.ItemDaoImpl;
import com.callitaday.monsterhunter.exception.AddException;
import com.callitaday.monsterhunter.exception.ModifyException;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.exception.PurchaseFailException;
import java.sql.SQLException;

public class ShopServiceImpl implements ShopService{
    ItemDao itemDao = new ItemDaoImpl();
    private static ShopService instance = new ShopServiceImpl();

    public static ShopService getInstance(){
        return instance;
    }

    /**
     * 상점 아이템 구매
     */
    @Override
    public void purchaseItem(int user_id, int item_id, int quantity) throws AddException, ModifyException,PurchaseFailException, NotFoundException {
        try{
            int result = itemDao.purchaseItem(user_id, quantity, item_id);
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
}

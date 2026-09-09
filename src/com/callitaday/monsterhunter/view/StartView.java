package com.callitaday.monsterhunter.view;

import com.callitaday.monsterhunter.controller.ShopController;
import com.callitaday.monsterhunter.dao.CharactorInfoDao;
import com.callitaday.monsterhunter.dao.CharactorInfoDaoImpl;
import com.callitaday.monsterhunter.dao.ItemDao;
import com.callitaday.monsterhunter.dao.ItemDaoImpl;
import com.callitaday.monsterhunter.dto.InventoryDto;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StartView {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        ItemDao itemDao = new ItemDaoImpl();
        CharactorInfoDao charactorInfoDao = new CharactorInfoDaoImpl();

            // 사용자 아이템 목록 조회 DAO
//            List<InventoryDto> list =  itemDao.getItemInfo(1);
//            list.forEach(System.out::println);

            // 아이템 구매 DAO
//            getItemPurchase(int user_id, int quantity, int item_id)
//            int re = itemDao.getItemPurchase(1, 2, 5);
//            System.out.println(re);

            // 아이템 구매
            System.out.print("구매할 아이템 번호 : ");
            int choiceNum = Integer.parseInt(sc.nextLine());
            System.out.print("아이템 수량 : ");
            int quantity = Integer.parseInt(sc.nextLine());
        ShopController.purchaceItem(1, choiceNum, quantity);

    }

}

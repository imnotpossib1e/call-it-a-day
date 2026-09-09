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
    public static void main(String[] args) {
            MenuView.menu();
    }

}

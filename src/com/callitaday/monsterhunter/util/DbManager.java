package com.callitaday.monsterhunter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {
    // 고정값을 상수 필드로 관리 -> 인터페이스에 선언(인터페이스는 모두 상수)

    /**
     * 로드
     */
    static {
        try {
            Class.forName(DbProperties.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 연결
     */
    public static Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(DbProperties.URL, DbProperties.USER_ID,
                                                     DbProperties.USER_PASS);
        return con;
    }

    /**
     * 닫기 (DDl & DML인 경우)
     */
    public static void dbClose(Connection con, Statement st) {
        try {
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 닫기 (SELECT인 경우)
     */
    public static void dbClose(Connection con, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            dbClose(con, st);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
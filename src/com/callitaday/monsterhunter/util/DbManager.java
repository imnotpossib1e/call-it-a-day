package com.callitaday.monsterhunter.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbManager {
    private static Properties proFile = new Properties();
    private static Properties queryProfile = new Properties();


    public static Properties getQueryProfile(){
        return queryProfile;
    }
    /**
     * 로드
     */
    static {
        try {
            //외부 properteis파일 로딩하기
            proFile.load(new FileInputStream("resources/dbInfo.properties"));
            Class.forName(proFile.getProperty("driverName"));
            queryProfile.load(new FileInputStream("resources/query.properties"));

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Properties getProFile() {
        return proFile;
    }

    /**
     * 연결
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            proFile.getProperty("url"),
            proFile.getProperty("userName"),
            proFile.getProperty("userPass"));
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
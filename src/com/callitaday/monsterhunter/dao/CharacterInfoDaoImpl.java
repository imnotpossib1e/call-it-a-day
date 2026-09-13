package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CharacterInfoDaoImpl implements CharacterInfoDao {

    /**
     * user_id 에 맞는캐릭터 정보 불러오기
     *
     * @param user_id
     */
    @Override
    public CharacterInfoDto getCharacterByUserId(int user_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from character_info where user_id = ?";
        CharacterInfoDto characterInfoDto = null;

        try {
            con=DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            if(rs.next()){
                characterInfoDto = new CharacterInfoDto(rs.getInt("user_id"), rs.getInt("hp"), rs.getInt("mp"), rs.getInt("atk"),rs.getInt("def"), rs.getInt("coin"), rs.getInt("stage_id"));
            }

        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return characterInfoDto;
    }

    public int updateCharacterByUserId(Connection con, int userId) throws SQLException{
        PreparedStatement ps = null;

        String sql = "update character_info set atk = atk+10 , def = def+10 where user_id = ?";
        int re = 0;
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, userId);
            re = ps.executeUpdate();
        }finally {
            DbManager.dbClose(null, ps);
        }

        return re;
    }


    /**
     * 회원가입 시 유저의 characterInfo 생성하기
     *
     * @param user_id
     */
    @Override
    public int insertCharacterInfo(int user_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        String sql = "insert into character_info(user_id) values(?)";
        int result = 0;

        try{
            con = DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);

            result = ps.executeUpdate();
        }finally {
            DbManager.dbClose(con, ps);
        }
        return result;
    }
}

package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import com.callitaday.monsterhunter.util.DbManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CharactorInfoDaoImpl implements CharactorInfoDao{

    /**
     * user_id 에 맞는캐릭터 정보 불러오기
     *
     * @param user_id
     */
    @Override
    public CharactorInfoDto getCharactorByUserId(int user_id) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from charactor_info where user_id = ?";
        CharactorInfoDto charactorInfoDto = null;

        try {
            con=DbManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, user_id);
            rs = ps.executeQuery();

            if(rs.next()){
                charactorInfoDto = new CharactorInfoDto(rs.getInt("user_id"), rs.getInt("hp"), rs.getInt("mp"), rs.getInt("atk"),rs.getInt("def"), rs.getInt("job"), rs.getInt("coin"), rs.getInt("stage_id"));
            }

        }finally {
            DbManager.dbClose(con, ps, rs);
        }

        return charactorInfoDto;
    }
}

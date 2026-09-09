package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharactorInfoDto;
import java.sql.SQLException;

public interface CharactorInfoDao {
    /**
     * user_id 에 맞는캐릭터 정보 불러오기
     */
    public CharactorInfoDto getCharactorByUserId(int user_id) throws SQLException;

}

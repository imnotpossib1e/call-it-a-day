package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import java.sql.SQLException;

public interface CharacterInfoDao {
    /**
     * user_id 에 맞는캐릭터 정보 불러오기
     */
    public CharacterInfoDto getCharacterByUserId(int user_id) throws SQLException;

}

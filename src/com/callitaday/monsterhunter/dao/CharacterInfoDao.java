package com.callitaday.monsterhunter.dao;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import java.sql.Connection;
import java.sql.SQLException;

public interface CharacterInfoDao {
    /**
     * user_id 에 맞는캐릭터 정보 불러오기
     */
    public CharacterInfoDto getCharacterByUserId(int user_id) throws SQLException;

    /**
     * user_id에 맞는 캐릭터 hp,mp 수정하기
     *
     */
    public int updateCharacterByUserId(Connection con, int user_id) throws SQLException;

    /**
     * 회원가입 시 유저의 characterInfo 생성하기
     */
    public int insertCharacterInfo(int user_id) throws SQLException;
}

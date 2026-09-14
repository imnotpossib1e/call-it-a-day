package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dao.CharacterInfoDao;
import com.callitaday.monsterhunter.dao.CharacterInfoDaoImpl;
import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.sql.SQLException;
import java.util.List;

public class CharacterInfoServiceImpl implements CharacterInfoService{
    CharacterInfoDao characterInfoDao = new CharacterInfoDaoImpl();
    private static CharacterInfoService instance = new CharacterInfoServiceImpl();
    public static CharacterInfoService getInstance(){
        return instance;
    }

    /**
     * 해당 유저의 캐릭터 정보 불러오기
     *
     * @param userId
     */
    @Override
    public CharacterInfoDto selectCharInfoByUserId(int userId) throws NotFoundException {
        try{
            CharacterInfoDto characterInfoDto = characterInfoDao.getCharacterByUserId(userId);
            if(characterInfoDto == null){
                throw new NotFoundException("해당 유저의 캐릭터 정보가 없습니다.");
            }
            return characterInfoDto;

        }catch (SQLException e){
            throw new NotFoundException("해당 유저의 캐릭터 정보를 찾을 수 없습니다.");
        }
    }
}

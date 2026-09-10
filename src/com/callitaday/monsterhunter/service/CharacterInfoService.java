package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.util.List;

public interface CharacterInfoService {

    /**
     * 해당 유저의 캐릭터 정보 불러오기
     */
    public CharacterInfoDto selectCharInfoByUserId(int userId) throws NotFoundException;
}

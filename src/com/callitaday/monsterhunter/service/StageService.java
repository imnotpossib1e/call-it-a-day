package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.util.List;

public interface StageService {
    /**
     * 스테이지 조회
     */
    public List<StageDto> selectStage(int userId) throws NotFoundException;


}

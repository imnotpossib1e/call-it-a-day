package com.callitaday.monsterhunter.controller;

import com.callitaday.monsterhunter.dto.CharacterInfoDto;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import com.callitaday.monsterhunter.service.CharacterInfoService;
import com.callitaday.monsterhunter.service.CharacterInfoServiceImpl;
import com.callitaday.monsterhunter.service.StageService;
import com.callitaday.monsterhunter.service.StageServiceImpl;
import com.callitaday.monsterhunter.view.EndView;
import com.callitaday.monsterhunter.view.FailView;
import java.util.List;

public class StageController {
    public static StageService stageService = StageServiceImpl.getInstace();
    public static CharacterInfoService characterInfoService = CharacterInfoServiceImpl.getInstance();

    /**
     * 스테이지 조회
     */
    public static void selectStage(int userId){
        try{
            // 전체 스테이지 조회
            List<StageDto> list = stageService.selectStage(userId);
            // 현재 스테이지 조회
            CharacterInfoDto characterInfoDto = characterInfoService.selectCharInfoByUserId(userId);
            int stageId = characterInfoDto.getStage_id();

            //엔드뷰
            EndView.printStageSelect(list, stageId);
        }catch (NotFoundException e){
            FailView.errorMessage(e.getMessage());
        }
    }
}

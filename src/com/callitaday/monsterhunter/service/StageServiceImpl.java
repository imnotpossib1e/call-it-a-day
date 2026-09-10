package com.callitaday.monsterhunter.service;

import com.callitaday.monsterhunter.dao.StageDao;
import com.callitaday.monsterhunter.dao.StageDaoImpl;
import com.callitaday.monsterhunter.dto.StageDto;
import com.callitaday.monsterhunter.exception.NotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageServiceImpl implements StageService{
    StageDao stageDao = new StageDaoImpl();

    private static StageService instance = new StageServiceImpl();
    public static StageService getInstance(){
        return instance;
    }

    /**
     * 스테이지 조회
     */
    @Override
    public List<StageDto> selectStage(int userId) throws NotFoundException {
        List<StageDto> list = null;
        try{
            list= stageDao.selectAllStage();
            if(list == null || list.size() == 0) throw new NotFoundException("스테이지 목록이 없습니다.");
        } catch (SQLException e) {
            throw  new NotFoundException("스테이지 정보를 찾을 수 없습니다.");
        }
        return list;
    }
}

package com.wangdao.dao.qualifycontrol;

import com.wangdao.bean.qualify.FinalMeasuretCheck;

import java.util.HashMap;
import java.util.List;

public interface FinalMeasuretCheckMapper {
    int deleteByPrimaryKey(String fMeasureCheckId);

    int insert(FinalMeasuretCheck record);

    int insertSelective(FinalMeasuretCheck record);

    FinalMeasuretCheck selectByPrimaryKey(String fMeasureCheckId);

    int updateByPrimaryKeySelective(FinalMeasuretCheck record);

    int updateByPrimaryKey(FinalMeasuretCheck record);

    List<FinalMeasuretCheck> selectMeasureByPage(HashMap<String, Integer> map);
    Integer selectAllMeasureCount();


    List<FinalMeasuretCheck> selectMeasureByCheckIdCondition(HashMap<String, Object> map);
    Integer selectMeasureCountByCheckIdCondition(String searchValue);


    List<FinalMeasuretCheck> selectMeasureByOrderIdCondition(HashMap<String, Object> map);
    Integer selectMeasureCountByOrderIdCondition(String searchValue);

}
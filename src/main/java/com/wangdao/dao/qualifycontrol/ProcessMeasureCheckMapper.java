package com.wangdao.dao.qualifycontrol;

import com.wangdao.bean.qualify.ProcessMeasureCheck;

import java.util.HashMap;
import java.util.List;

public interface ProcessMeasureCheckMapper {
    int deleteByPrimaryKey(String pMeasureCheckId);

    int insert(ProcessMeasureCheck record);

    int insertSelective(ProcessMeasureCheck record);

    ProcessMeasureCheck selectByPrimaryKey(String pMeasureCheckId);

    int updateByPrimaryKeySelective(ProcessMeasureCheck record);

    int updateByPrimaryKey(ProcessMeasureCheck record);

    List<ProcessMeasureCheck> selectPMeasureCheckByPage(HashMap<String, Integer> map);

    Integer selectAllPMeasureCheck();

    List<ProcessMeasureCheck> selectPMeasureByCheckIdCondition(HashMap<String, Object> map);

    Integer selectPMeasureCheckCountByCheckIdCondition(String searchValue);
}
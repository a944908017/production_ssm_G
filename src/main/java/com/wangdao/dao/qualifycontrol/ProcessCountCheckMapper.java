package com.wangdao.dao.qualifycontrol;

import com.wangdao.bean.qualify.ProcessCountCheck;

import java.util.HashMap;
import java.util.List;

public interface ProcessCountCheckMapper {
    int deleteByPrimaryKey(String pCountCheckId);

    int insert(ProcessCountCheck record);

    int insertSelective(ProcessCountCheck record);

    ProcessCountCheck selectByPrimaryKey(String pCountCheckId);

    int updateByPrimaryKeySelective(ProcessCountCheck record);

    int updateByPrimaryKey(ProcessCountCheck record);

    List<ProcessCountCheck> selectPCountCheckByPage(HashMap<String, Integer> map);

    Integer selectAllPCountCheck();

    List<ProcessCountCheck> selectPCountByCheckIdCondition(HashMap<String, Object> map);

    Integer selectPCountCheckCountByCheckIdCondition(String searchValue);
}
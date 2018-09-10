package com.wangdao.dao.qualifycontrol;

import com.wangdao.bean.qualify.FinalCountCheck;

import java.util.HashMap;
import java.util.List;

public interface FinalCountCheckMapper {
    int deleteByPrimaryKey(String fCountCheckId);

    int insert(FinalCountCheck record);

    int insertSelective(FinalCountCheck record);

    FinalCountCheck selectByPrimaryKey(String fCountCheckId);

    int updateByPrimaryKeySelective(FinalCountCheck record);

    int updateByPrimaryKey(FinalCountCheck record);

    List<FinalCountCheck> selectFCountByPage(HashMap<String, Integer> map);

    Integer selectAllfCountCheck();

    List<FinalCountCheck> selectFCountCheckByCheckIdCondition(HashMap<String, Object> map);

    Integer selectFCountCheckCountByCheckIdCondition(String searchValue);

    List<FinalCountCheck> selectFCountCheckByOrderIdCondition(HashMap<String, Object> map);

    Integer selectFCountCheckCountByOrderIdCondition(String searchValue);
}
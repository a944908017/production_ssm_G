package com.wangdao.dao.technology;

import com.wangdao.bean.technology.TechnologyPlan;

import java.util.List;
import java.util.Map;

public interface TechnologyPlanMapper {
    int deleteByPrimaryKey(String technologyPlanId);

    int insert(TechnologyPlan record);

    int insertSelective(TechnologyPlan record);

    TechnologyPlan selectByPrimaryKey(String technologyPlanId);

    int updateByPrimaryKeySelective(TechnologyPlan record);

    int updateByPrimaryKey(TechnologyPlan record);

    List<TechnologyPlan> selectByPage(Map<String, Object> params);

    List<TechnologyPlan> searchByTechnologyId(String tid);

    List<TechnologyPlan> seachByTechnologyIdList(List<String> tempTid);
}
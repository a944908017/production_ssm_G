package com.wangdao.dao.technology;

import com.wangdao.bean.technology.TechnologyRequirement;

import java.util.List;
import java.util.Map;

public interface TechnologyRequirementMapper {
    int deleteByPrimaryKey(String technologyRequirementId);

    int insert(TechnologyRequirement record);

    int insertSelective(TechnologyRequirement record);

    TechnologyRequirement selectByPrimaryKey(String technologyRequirementId);

    int updateByPrimaryKeySelective(TechnologyRequirement record);

    int updateByPrimaryKey(TechnologyRequirement record);

    List<TechnologyRequirement> selectByPage(Map<String, Object> params);

    List<TechnologyRequirement> searchByTechnologyId(String tid);

    List<TechnologyRequirement> seachByTechnologyIdList(List<String> tempTid);
}
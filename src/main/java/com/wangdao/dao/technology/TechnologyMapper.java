package com.wangdao.dao.technology;

import com.wangdao.bean.technology.Technology;

import java.util.List;
import java.util.Map;

public interface TechnologyMapper {
    int deleteByPrimaryKey(String technologyId);

    int insert(Technology record);

    int insertSelective(Technology record);

    Technology selectByPrimaryKey(String technologyId);

    int updateByPrimaryKeySelective(Technology record);

    int updateByPrimaryKey(Technology record);

    List<Technology> selectByPage(Map<String, Object> params);

    List<Technology> searchName(String name);

    List<String> searchNameReturnId(String name);

}
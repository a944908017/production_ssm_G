package com.wangdao.dao.technology;

import com.wangdao.bean.technology.Process;

import java.util.List;
import java.util.Map;

public interface ProcessMapper {
    int deleteByPrimaryKey(String processId);

    int insert(Process record);

    int insertSelective(Process record);

    Process selectByPrimaryKey(String processId);

    int updateByPrimaryKeySelective(Process record);

    int updateByPrimaryKey(Process record);

    List<Process> selectByPage(Map<String, Object> params);
}
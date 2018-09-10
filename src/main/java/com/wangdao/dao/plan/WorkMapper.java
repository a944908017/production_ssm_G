package com.wangdao.dao.plan;

import com.wangdao.bean.plan.Work;
import com.wangdao.bean.plan.WorkExample;

import java.util.List;

public interface WorkMapper {
    int deleteByPrimaryKey(String workId);

    int insert(Work record);

    int insertSelective(Work record);

    Work selectByPrimaryKey(String workId);

    int updateByPrimaryKeySelective(Work record);

    int updateByPrimaryKey(Work record);

    /**
     * 按条件查询Work对象
     * @param example
     * @return
     */
    List<Work> selectByExample(WorkExample example);

    /**
     * 根据条件删除对象
     * @param example
     * @return
     */
    int deleteByExample(WorkExample example);

}
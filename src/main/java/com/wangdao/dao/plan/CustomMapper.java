package com.wangdao.dao.plan;

import com.wangdao.bean.plan.Custom;
import com.wangdao.bean.plan.CustomExample;

import java.util.List;

public interface CustomMapper {


    /**
     * 根据查询到的customId去查询对应的Custom对象
     * @param customId
     * @return
     */
    Custom selectCustomInCustomByCustomId(String customId);

    /**
     * 查询全部客户对象
     * @param custom
     * @return
     */
    List<Custom> findAllCustom(Custom custom);

    /**
     * 根据客户姓名来查询对应的客户对象
     * @param searchValue
     * @return
     */
    Custom selectCustomInCustomByCustomName(String searchValue);

    /**
     * 根据条件删除对应的Custom对象
     * @param example
     * @return
     */
    int deleteByExample(CustomExample example);

    /**
     * 新增客户信息
     * @param record
     * @return
     */
    int insert(Custom record);

    /**
     * 根据条件查询对应的客户对象
     * @param example
     * @return
     */
    List<Custom> selectByExample(CustomExample example);

    /**
     * 更新数据，空数据不处理
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Custom record);

    /**
     * 根据name查找对应的ids
     * @param customName
     * @return
     */
    List<String> selectCustomIdsByCustomName(String customName);

    Custom selectByPrimaryKey(String customId);


}
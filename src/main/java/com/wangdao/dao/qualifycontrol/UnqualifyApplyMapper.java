package com.wangdao.dao.qualifycontrol;

import com.wangdao.bean.qualify.UnqualifyApply;
import com.wangdao.bean.qualify.UnqualifySelectByProductIds;

import java.util.HashMap;
import java.util.List;

public interface UnqualifyApplyMapper {
    int deleteByPrimaryKey(String unqualifyApplyId);

    int insert(UnqualifyApply record);

    int insertSelective(UnqualifyApply record);

    UnqualifyApply selectByPrimaryKey(String unqualifyApplyId);

    List<UnqualifyApply> selectAll();

    int updateByPrimaryKeySelective(UnqualifyApply record);

    int updateByPrimaryKey(UnqualifyApply record);

    List<UnqualifyApply> selectUnqualifyApplyByPage(HashMap<String, Integer> map);

    Integer selectAllCount();

    List<UnqualifyApply> selectUnqualifyByIdCondition(HashMap<String, Object> map);

    Integer selectCountByIdCondition(String searchValue);

    List<UnqualifyApply> selectByProductId(String productId);

    Integer selectCountByProductId(String productId);

    List<UnqualifyApply> selectUnqualifyByProductIds(UnqualifySelectByProductIds usbi);

    Integer selectCountByProductIds(List<String> productIds);
}
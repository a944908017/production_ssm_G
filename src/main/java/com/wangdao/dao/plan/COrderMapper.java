package com.wangdao.dao.plan;

import com.wangdao.bean.plan.COrder;
import com.wangdao.bean.plan.COrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface COrderMapper {


    /**
     * 查询全部Order对象，连表查询
     * @param cOrder
     * @return
     */
    List<COrder> findAllOrder(COrder cOrder);

    /**
     * 根据orderId查询到c_order表中的custom_id对应的值
     * @param orderId
     * @return
     */
    String selectCustomIdInCOrderByOrderId(String orderId);

    /**
     *
     * 根据条件查询对应的订单对象，连表查询
     * @param example
     * @return
     */
    List<COrder> selectByExample(COrderExample example);

    /**
     * 根据custom_id来查询对应的订单对象list
     * @param customId
     * @return
     */
    List<COrder> selectCOrderListInCOrderByCustomId(String customId);

    /**
     * 在c_order表中根据productid来查询COrder对象的list
     * @param productId
     * @return
     */
    List<COrder> selectCOrderListInCOrderByProductId(String productId);

    /**
     * 根据条件删除对应的订单对象
     * @param example
     * @return
     */
    int deleteByExample(COrderExample example);

    /**
     * 根据orderid来查询对应的订单对象
     * @param orderId
     * @return
     */
    COrder selectByPrimaryKey(String orderId);

    /**
     * 使用Example方法将订单对象插入
     * @param cOrder
     * @return
     */
    int insert(COrder cOrder);

    /**
     * 更新数据，空白字段可以不填充更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(COrder record);

    /**
     *
     * @param cOrderName
     * @return
     */
//    List<String> selectCOrderIdsByCOrderName(String cOrderName);
}
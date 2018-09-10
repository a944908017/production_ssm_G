package com.wangdao.dao.plan;

import com.wangdao.bean.plan.Product;
import com.wangdao.bean.plan.ProductExample;

import java.util.List;

public interface ProductMapper {
    /**
     * 在product表中使用ProductId查询到对应的Product对象
     * @param productId
     * @return
     */
    Product selectProductInProductByProductId(String productId);

    /**
     * 在product表中使用ProductName来查询对应的Product对象
     * @param searchValue
     * @return
     */
    Product selectProductInProductByProductName(String searchValue);

    List<Product> selectByExample(ProductExample example);

    String selectProductNameInProductByProductId(String productId);

    /**
     * 删除Product对象
     * @param example
     * @return
     */
    int deleteByExample(ProductExample example);

    /**
     * 插入Product对象
     * @param record
     * @return
     */
    int insert(Product record);

    Product selectByPrimaryKey(String productId);

    /**
     * 根据name去查询对应的ids的list
     * @param productName
     * @return
     */
    List<String> selectProductIdsByProductName(String productName);

    /**
     * 更新数据，空白不操作
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Product record);
}
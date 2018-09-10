package com.wangdao.service.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.plan.*;

import java.util.List;

/**
 * com.wangdao.service.plan.PlanService接口，提供实现PlanController（计划进度模块）中功能的实现方法
 */
public interface PlanService {

    /********************订单管理*******************/
    /**
     * 查询指定页中对应的订单信息
     *
     * @param page
     * @param rows
     * @param cOrderExample
     * @return 返回一个FromDatePostman对象
     */
    FormDataPostman fingCOrder(Integer page, Integer rows, COrderExample cOrderExample);

    /**
     * 根据订单id找到对应的订单对象
     *
     * @param orderId
     * @return
     */
    COrder getCOrderByCOrderId(String orderId);

    /**
     * 根据订单id查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    FormDataPostman searchOrderByOrderId(Integer page, Integer rows, String searchValue);

    /**
     * 根据客户姓名查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    FormDataPostman searchOrderByCustomName(Integer page, Integer rows, String searchValue);

    /**
     * 根据产品名称查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    FormDataPostman searchOrderByProductName(Integer page, Integer rows, String searchValue);


    /**
     * 根据cOrderIds来删除对应的订单
     *
     * @param cOrderIds 需要删除的订单id的数据
     * @return
     */
    int deleteCOrdersByIds(String[] cOrderIds);

    /**
     * 根据COrderId来查询对应的COrder对象
     *
     * @param orderId
     * @return
     */
    COrder selectOrderByOrderId(String orderId);

    /**
     * 将COrder对象插入到c_order对象中
     *
     * @param cOrder
     * @return
     */
    int insertOrder(COrder cOrder);

    /**
     * 不分页的查找到全部COrder对象
     *
     * @return
     */
    List<COrder> findAllCOrderNotPage();

    /**
     * 更新Order数据中的信息
     *
     * @param cOrder
     * @return
     */
    int updateOrder(COrder cOrder);

    /**
     * 根据name查找到对应的ids模糊查询
     * @param cOrderName
     * @return
     */
//    List<String> getCOrderIdsByCOrderName(String cOrderName);

    /*********************客户管理**********************/
    /**
     * 根据orderId查询到对应Custom对象
     *
     * @param customId
     * @return
     */
    Custom getCustomByCustomId(String customId);

    /**
     * 查询指定页中对应的客户信息
     *
     * @param page
     * @param rows
     * @param custom
     * @return
     */
    FormDataPostman fingCustom(Integer page, Integer rows, CustomExample custom);

    /**
     * 不分页的查找到所有的客户对象
     *
     * @return 返回一个包含有全部custom对象的list
     */
    List<Custom> findAllCustomNotPage();

    /**
     * 根据ids删除对应的custom对象
     *
     * @param ids
     * @return
     */
    int deleteCustomsByIds(String[] ids);

    /**
     * 插入客户对象
     *
     * @param custom
     * @return
     */
    int insertCustom(Custom custom);

    /**
     * 更新CUstom数据中的信息
     *
     * @param custom
     * @return
     */
    int updateCustom(Custom custom);

    /**
     * 根据name获取到对应的CustomId（模糊查询）
     * @param customName
     * @return
     */
    List<String> getCustomIdsByCustomName(String customName);

    /******************产品管理********************/

    /**
     * 根据PorductId查询到对应的Product对象
     *
     * @param productId
     * @return
     */
    Product getProductByProductId(String productId);

    /**
     * 不分页的查找到全部的产品对象
     *
     * @return 返回一个包含有全部product对象的list
     */
    List<Product> findAllProductNotPage();

    /**
     * 查找全部产品对象并分页
     *
     * @param page
     * @param rows
     * @param productExample
     * @return
     */
    FormDataPostman fingProduct(Integer page, Integer rows, ProductExample productExample);

    /**
     * 根据Ids来删除对应的Product对象
     *
     * @param ids
     * @return
     */
    int deleteProductsByIds(String[] ids);

    /**
     * 新增Product对象
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 根据ProductName查询到对应的ProductId，可能是模糊查询所以这里我们返回一个List
     *
     * @param productName
     * @return
     */
    List<String> getProductIdsByProductName(String productName);

    /**
     * 更新Product数据
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);


    /*****************作业管理*******************/

    /**
     * 找到全部Work对象并分页显示
     *
     * @param page
     * @param rows
     * @param workExample
     * @return
     */
    FormDataPostman fingWork(Integer page, Integer rows, WorkExample workExample);

    /**
     * 删除ids对ing的work对象
     *
     * @param ids
     * @return
     */
    int deleteWorksByIds(String[] ids);

    /**
     * 根据id查找对应的Work对象
     *
     * @param workId
     * @return
     */
    Work selectWorkByWorkId(String workId);

    /**
     * 插入work对象
     *
     * @param work
     * @return
     */
    int insertWork(Work work);

    /**
     * 不分页获取到全部Work对象
     *
     * @return
     */
    List<Work> findAllWorkNotPage();

    /**
     * 根据workid去获取对应的Word对象
     *
     * @param workId
     * @return
     */
    Work getWorkByWorkId(String workId);

    /**
     * 更新Work数据
     *
     * @param work
     * @return
     */
    int updateWork(Work work);

    /*****************生产计划管理******************/

    /**
     * 找到生产计划数据
     *
     * @param page
     * @param rows
     * @param manufactureExample
     * @return
     */
    FormDataPostman fingManufacture(Integer page, Integer rows, ManufactureExample manufactureExample);

    /**
     * 根据ids删除对应的Manufacture对象
     *
     * @param ids
     * @return
     */
    int deleteManufacturesByIds(String[] ids);

    /**
     * 根据id查对应的Manufacture对象
     *
     * @param manufactureSn
     * @return
     */
    Manufacture selectManufactureByManufactureId(String manufactureSn);

    /**
     * 插入Manufacture对象
     *
     * @param manufacture
     * @return
     */
    int insertManufacture(Manufacture manufacture);

    /**
     * 不分页的获取到全部Manufacture对象
     *
     * @return
     */
    List<Manufacture> findAllManufactureNotPage();

    /**
     * 更新Manufacture数据
     *
     * @param manufacture
     * @return
     */
    int updateManufacture(Manufacture manufacture);


    /*****************生产派工管理******************/
    /**
     * 查找全部Task数据并分页
     *
     * @param taskExample
     * @param page
     * @param rows
     * @param taskExample
     * @return
     */
    FormDataPostman fingTask(Integer page, Integer rows, TaskExample taskExample);

    /**
     * 删除ids对应的Task对象
     *
     * @param ids
     * @return
     */
    int deleteTaskByIds(String[] ids);

    /**
     * 根据TaskId查询对应的Task的对象
     *
     * @param taskId
     * @return
     */
    Task selectTaskByTaskId(String taskId);

    /**
     * 插入Task对象
     *
     * @param task
     * @return
     */
    int insertTask(Task task);

    /**
     * 更新Task数据
     *
     * @param task
     * @return
     */
    int updateTask(Task task);


}

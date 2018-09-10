package com.wangdao.service.plan.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.plan.*;
import com.wangdao.dao.plan.*;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: PlanServiceImpl:实现PlanService接口，具体实现PlanController（计划进度模块）中的方法
 * @date 2018/8/31 1:36
 */
// PROPAGATION_REQUIRED：如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。
// isolation表示事件访问的机别
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
@Service("PlanService")
public class PlanServiceImpl implements PlanService {

    /**
     * 自动注入持久层Dao对象
     */
    @Autowired
    private COrderMapper cOrderMapper;
    @Autowired
    private CustomMapper customMapper;
    @Autowired
    private ManufactureMapper manufactureMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private WorkMapper workMapper;


    /*********订单管理**********/


    /**
     * 查询指定页面中需要显示的订单信息
     *
     * @param page
     * @param rows
     * @param cOrderExample
     * @return 返回一个FromDatePostman对象
     */
    @Override
    public FormDataPostman fingCOrder(Integer page, Integer rows, COrderExample cOrderExample) {
        //在你需要进行分页的 MyBatis 查询方法前调用 PageHelper.startPage 静态方法即可，
        // 紧跟在这个方法后的第一个MyBatis 查询方法会被进行分页。
        //使用PageHelper进行分页处理
        PageHelper.startPage(page, rows);
        List<COrder> corderList = cOrderMapper.selectByExample(cOrderExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.findCOrder/corderList="+corderList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<COrder> pageInfo = new PageInfo<>(corderList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.findCOrder/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 根据订单id找到对应的订单对象
     *
     * @param orderId
     * @return
     */
    @Override
    public COrder getCOrderByCOrderId(String orderId) {
        return cOrderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 根据订单id查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @Override
    public FormDataPostman searchOrderByOrderId(Integer page, Integer rows, String searchValue) {

        PageHelper.startPage(page, rows);
        COrderExample cOrderExample = new COrderExample();
        COrderExample.Criteria criteria = cOrderExample.createCriteria();//构造自定义查询条件
        criteria.andOrderIdEqualTo(searchValue);//根据订单id去查找
        List<COrder> corderList = cOrderMapper.selectByExample(cOrderExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByOrderId/corderList="+corderList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<COrder> pageInfo = new PageInfo<>(corderList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByOrderId/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 根据客户姓名查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @Override
    public FormDataPostman searchOrderByCustomName(Integer page, Integer rows, String searchValue) {

        Custom custom = customMapper.selectCustomInCustomByCustomName(searchValue);

        if (custom == null) {
            return new FormDataPostman(0, Collections.singletonList(0));

        } else {
            PageHelper.startPage(page, rows);

            //不可以使用如下方法去查询，custom_id无法具体指定使用某一个表中的值
/*        COrderExample cOrderExample=new COrderExample();
        COrderExample.Criteria criteria=cOrderExample.createCriteria();//构造自定义查询条件
        criteria.andCustomIdEqualTo("custom."+custom.getCustomId());//根据订单id去查找
        List<COrder> corderList = cOrderMapper.selectByExample(cOrderExample);*/

            List<COrder> corderList = cOrderMapper.selectCOrderListInCOrderByCustomId(custom.getCustomId());

            //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByCustomName/corderList="+corderList);

            //创建一个FormDataPostman对象
            FormDataPostman formDataPostman = new FormDataPostman();

            //取订单记录的总条数
            PageInfo<COrder> pageInfo = new PageInfo<>(corderList);
            formDataPostman.setRows(pageInfo.getList());

            formDataPostman.setTotal(pageInfo.getTotal());
            //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByCustomName/formDataPostman="+formDataPostman);

            return formDataPostman;
        }
    }

    /**
     * 根据产品名称查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     */
    @Override
    public FormDataPostman searchOrderByProductName(Integer page, Integer rows, String searchValue) {
        Product product = productMapper.selectProductInProductByProductName(searchValue);
        if (product == null) {
            return new FormDataPostman(0, Collections.singletonList(0));
        } else {

            PageHelper.startPage(page, rows);
            //此处同样不能使用Example的方法，Column 'product_id' in where clause is ambiguous
//        COrderExample cOrderExample=new COrderExample();
//        COrderExample.Criteria criteria=cOrderExample.createCriteria();//构造自定义查询条件
//        criteria.andProductIdEqualTo(product.getProductId());//根据产品id去查找
//        List<COrder> corderList = cOrderMapper.selectByExample(cOrderExample);

            List<COrder> corderList = cOrderMapper.selectCOrderListInCOrderByProductId(product.getProductId());

            //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByProductName/corderList="+corderList);

            //创建一个FormDataPostman对象
            FormDataPostman formDataPostman = new FormDataPostman();

            //取订单记录的总条数
            PageInfo<COrder> pageInfo = new PageInfo<>(corderList);
            formDataPostman.setRows(pageInfo.getList());

            formDataPostman.setTotal(pageInfo.getTotal());
            //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.searchOrderByProductName/formDataPostman="+formDataPostman);

            return formDataPostman;
        }
    }

    /**
     * 根据cOrderIds来删除对应的订单
     *
     * @param cOrderIds 需要删除的订单id的数据
     * @return
     */
    @Override
    public int deleteCOrdersByIds(String[] cOrderIds) {

        COrderExample cOrderExample = new COrderExample();
        COrderExample.Criteria criteria = cOrderExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteCOrdersByIds/Arrays.asList(cOrderIds)=" + Arrays.asList(cOrderIds));

        criteria.andOrderIdIn(Arrays.asList(cOrderIds));//根据订单id去删除

        int result = cOrderMapper.deleteByExample(cOrderExample);

        return result;
    }

    /**
     * 根据COrderId来查询对应的COrder对象
     *
     * @param orderId
     * @return
     */
    @Override
    public COrder selectOrderByOrderId(String orderId) {
        return cOrderMapper.selectByPrimaryKey(orderId);
    }

    /**
     * 将COrder对象插入到c_order对象中
     *
     * @param cOrder
     * @return
     */
    @Override
    public int insertOrder(COrder cOrder) {
        return cOrderMapper.insert(cOrder);
    }

    /**
     * 不分页的查找到全部COrder对象
     *
     * @return
     */
    @Override
    public List<COrder> findAllCOrderNotPage() {
        List<COrder> cOrderList = cOrderMapper.findAllOrder(null);
        return cOrderList;
    }

    /**
     * 更新COrder的数据
     * @param cOrder
     * @return
     */
    @Override
    public int updateOrder(COrder cOrder) {
        return cOrderMapper.updateByPrimaryKeySelective(cOrder);
    }

//    @Override
//    public List<String> getCOrderIdsByCOrderName(String cOrderName) {
//        List<String> result=cOrderMapper.selectCOrderIdsByCOrderName(cOrderName);
//
//        return result;
//    }

    /*********客户管理**********/

    /**
     * 根据orderId查询到对应Custom对象
     *
     * @param customId
     * @return
     */
    @Override
    public Custom getCustomByCustomId(String customId) {

        //根据查询到的customId去查询对应的Custom对象
        Custom custom = customMapper.selectCustomInCustomByCustomId(customId);
        //System.out.println("W//com.wangdao.service.plan.impl.PlanServiceImpl.selectCustomInCustomByCustomId/custom="+custom);
        //返回查询到的Custom对象
        return custom;
    }

    /**
     * 查找当前页需要显示的客户信息
     *
     * @param page
     * @param rows
     * @param customExample
     * @return
     */
    @Override
    public FormDataPostman fingCustom(Integer page, Integer rows, CustomExample customExample) {


        PageHelper.startPage(page, rows);
        List<Custom> customList = customMapper.selectByExample(customExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingCustom/customList="+customList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<Custom> pageInfo = new PageInfo<>(customList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingCustom/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 不分页的查找到所有的客户对象
     *
     * @return 返回一个包含有全部custom对象的list
     */
    @Override
    public List<Custom> findAllCustomNotPage() {
        List<Custom> customList = customMapper.findAllCustom(null);
        return customList;
    }

    /**
     * 根据customIds来删除对应的Custom对象
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteCustomsByIds(String[] ids) {

        CustomExample customExample = new CustomExample();
        CustomExample.Criteria criteria = customExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteCustomsByIds/Arrays.asList(customIds)=" + Arrays.asList(customIds));

        criteria.andCustomIdIn(Arrays.asList(ids));//根据订单id去删除

        int result = customMapper.deleteByExample(customExample);

        return result;
    }

    /**
     * 新增客户对象
     *
     * @param custom
     * @return
     */
    @Override
    public int insertCustom(Custom custom) {
        return customMapper.insert(custom);
    }

    /**
     * 更新Custom的数据
     * @param custom
     * @return
     */
    @Override
    public int updateCustom(Custom custom) {
        return customMapper.updateByPrimaryKeySelective(custom);
    }

    /**
     * 根据name查找到对应的ids模糊查询
     * @param customName
     * @return
     */
    @Override
    public List<String> getCustomIdsByCustomName(String customName) {
        List<String> result=customMapper.selectCustomIdsByCustomName(customName);

        return result;
    }

//    /**
//     * 根据id去查找对应的Custom对象并分页
//     * @param page
//     * @param rows
//     * @param customExample
//     * @return
//     */
//    @Override
//    public FormDataPostman getCustomByCustomIdByPage(Integer page, Integer rows, CustomExample customExample) {
//        PageHelper.startPage(page, rows);
//        List<Custom> customList = customMapper.selectByExample(customExample);
//
//        //创建一个FormDataPostman对象
//        FormDataPostman formDataPostman = new FormDataPostman();
//
//        //取订单记录的总条数
//        PageInfo<Custom> pageInfo = new PageInfo<>(customList);
//        formDataPostman.setRows(pageInfo.getList());
//
//        formDataPostman.setTotal(pageInfo.getTotal());
//        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingCustom/formDataPostman="+formDataPostman);
//
//        return formDataPostman;
//
//
//        return pageInfo;
//    }


    /*********产品管理**********/
    /**
     * 根据ProductId查询到对应的Product对象
     *
     * @param productId
     * @return
     */
    @Override
    public Product getProductByProductId(String productId) {

        //根据查询到的ProductId去查询对应的Product对象
        Product product = productMapper.selectProductInProductByProductId(productId);
        //System.out.println("W//com.wangdao.service.plan.impl.PlanServiceImpl.selectProductInProductByProductId/product="+product);
        //返回查询到的Custom对象
        return product;
    }

    @Override
    public List<Product> findAllProductNotPage() {

        ProductExample productExample = new ProductExample();

        List<Product> productList = productMapper.selectByExample(null);

        //System.out.println("W//com.wangdao.service.plan.impl.PlanServiceImpl.findAllProductNotPage/productList"+productList);

        return productList;
    }

    /**
     * 查找全部产品对象并分页
     *
     * @param page
     * @param rows
     * @param productExample
     * @return
     */
    @Override
    public FormDataPostman fingProduct(Integer page, Integer rows, ProductExample productExample) {

        PageHelper.startPage(page, rows);
        List<Product> productList = productMapper.selectByExample(productExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingProduct/productList="+productList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<Product> pageInfo = new PageInfo<>(productList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingProduct/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 根据Ids来删除对应的Product对象
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteProductsByIds(String[] ids) {

        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteProductsByIds/Arrays.asList(productIds)=" + Arrays.asList(ids));

        criteria.andProductIdIn(Arrays.asList(ids));//根据商品id去删除

        int result = productMapper.deleteByExample(productExample);

        return result;
    }

    /**
     * 插入Product数据
     * @param product
     * @return
     */
    @Override
    public int insertProduct(Product product) {
        return productMapper.insert(product);
    }

    /**
     * 根据name去查询对应的id的list
     * @param productName
     * @return
     */
    @Override
    public List<String> getProductIdsByProductName(String productName) {

        List<String> result=productMapper.selectProductIdsByProductName(productName);

        return result;
    }

    /**
     * 更新Product数据
     * @param product
     * @return
     */
    @Override
    public int updateProduct(Product product) {
        return productMapper.updateByPrimaryKeySelective(product);
    }


    /*********作业管理**********/

    @Override
    public FormDataPostman fingWork(Integer page, Integer rows, WorkExample workExample) {
        //使用PageHelper进行分页处理
        PageHelper.startPage(page, rows);
        List<Work> workList = workMapper.selectByExample(workExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingWork/workList="+workList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<Work> pageInfo = new PageInfo<>(workList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingWork/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 删除ids对应的work对象
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteWorksByIds(String[] ids) {
        WorkExample workExample = new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteWorksByIds/Arrays.asList(workIds)=" + Arrays.asList(ids));

        criteria.andWorkIdIn(Arrays.asList(ids));//根据商品id去删除

        int result = workMapper.deleteByExample(workExample);

        return result;
    }

    /**
     * 根据id查找对应的Work对象
     *
     * @param workId
     * @return
     */
    @Override
    public Work selectWorkByWorkId(String workId) {
        return workMapper.selectByPrimaryKey(workId);
    }

    /**
     * 插入work对象
     *
     * @param work
     * @return
     */
    @Override
    public int insertWork(Work work) {
        return workMapper.insert(work);
    }

    @Override
    public List<Work> findAllWorkNotPage() {

        return workMapper.selectByExample(null);
    }

    /**
     * 根据WorkId去获取对象的Work对象
     *
     * @param workId
     * @return
     */
    @Override
    public Work getWorkByWorkId(String workId) {
        Work work = workMapper.selectByPrimaryKey(workId);
        return work;
    }

    @Override
    public int updateWork(Work work) {
        return workMapper.updateByPrimaryKeySelective(work);
    }

    /*********生产计划管理**********/

    @Override
    public FormDataPostman fingManufacture(Integer page, Integer rows, ManufactureExample manufactureExample) {

        PageHelper.startPage(page, rows);
        List<Manufacture> manufactureList = manufactureMapper.selectByExample(manufactureExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingManufacture/manufactureList="+manufactureList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<Manufacture> pageInfo = new PageInfo<>(manufactureList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingManufacture/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 根据ids来删除对应的Manufacture对象
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteManufacturesByIds(String[] ids) {
        ManufactureExample manufactureExample = new ManufactureExample();
        ManufactureExample.Criteria criteria = manufactureExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteManufacturesByIds/Arrays.asList(ManufactureSns)=" + Arrays.asList(customIds));

        criteria.andManufactureSnIn(Arrays.asList(ids));//根据订单id去删除

        int result = manufactureMapper.deleteByExample(manufactureExample);

        return result;
    }

    /**
     * 根据id查对应的Manufacture对象
     *
     * @param manufactureSn
     * @return
     */
    @Override
    public Manufacture selectManufactureByManufactureId(String manufactureSn) {
        return manufactureMapper.selectByPrimaryKey(manufactureSn);
    }

    /**
     * 插入Manufacture对象
     *
     * @param manufacture
     * @return
     */
    @Override
    public int insertManufacture(Manufacture manufacture) {
        return manufactureMapper.insert(manufacture);
    }

    @Override
    public List<Manufacture> findAllManufactureNotPage() {

        return manufactureMapper.selectByExample(null);
    }

    @Override
    public int updateManufacture(Manufacture manufacture) {
        return manufactureMapper.updateByPrimaryKeySelective(manufacture);
    }


    /*********生产派工管理**********/

    /**
     * 查找到全部Task数据并分页
     *
     * @param page
     * @param rows
     * @param taskExample
     * @return
     */
    @Override
    public FormDataPostman fingTask(Integer page, Integer rows, TaskExample taskExample) {
        PageHelper.startPage(page, rows);
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingTask/taskList="+taskList);

        //创建一个FormDataPostman对象
        FormDataPostman formDataPostman = new FormDataPostman();

        //取订单记录的总条数
        PageInfo<Task> pageInfo = new PageInfo<>(taskList);
        formDataPostman.setRows(pageInfo.getList());

        formDataPostman.setTotal(pageInfo.getTotal());
        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.fingTask/formDataPostman="+formDataPostman);

        return formDataPostman;
    }

    /**
     * 根据ids删除对应的Task对象
     *
     * @param ids
     * @return
     */
    @Override
    public int deleteTaskByIds(String[] ids) {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();//构造自定义查询条件

        //System.out.println("w//com.wangdao.service.plan.impl.PlanServiceImpl.deleteTaskByIds/Arrays.asList(taskIds)=" + Arrays.asList(ids));

        criteria.andTaskIdIn(Arrays.asList(ids));//根据taskid去删除

        int result = taskMapper.deleteByExample(taskExample);

        return result;
    }

    /**
     * 根据id查询对应的Task对象
     *
     * @param taskId
     * @return
     */
    @Override
    public Task selectTaskByTaskId(String taskId) {
        return taskMapper.selectByPrimaryKey(taskId);
    }

    /**
     * 插入Task对象
     *
     * @param task
     * @return
     */
    @Override
    public int insertTask(Task task) {
        return taskMapper.insert(task);
    }

    @Override
    public int updateTask(Task task) {
        return taskMapper.updateByPrimaryKeySelective(task);
    }


}

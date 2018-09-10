package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.device.Device;
import com.wangdao.bean.plan.COrder;
import com.wangdao.bean.plan.COrderExample;
import com.wangdao.bean.plan.CustomExample;
import com.wangdao.bean.plan.Product;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: COrderController:处理计划进度模块中订单管理相关的请求
 * @date 2018/8/31 1:32
 */
@Controller
@RequestMapping("/order")
public class COrderController {

    /**
     * 自动注入PlanService
     */
    @Autowired
    @Qualifier("PlanService")
    private PlanService planService;


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
    }

    /*********订单管理**********/


    /**
     * 处理用户查询订单的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由listOrder来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findOrder() {
        //转到订单列表显示的页面上
        return "order_list";
    }

    /**
     * 根据orderId返回订单对象
     *
     * @param orderId 从前端随url发出的orderId
     * @return 返回当前请求的订单对应的订单对象
     * @throws Exception
     */
    @RequestMapping("/get/{orderId}")
    @ResponseBody
    //@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上
    public COrder getCOrderByCOrderId(@PathVariable String orderId) throws Exception {
        //System.out.println("w//com.wangdao.controller.plan.CustomController.getCOrderByCOrderId/orderId="+orderId);
        COrder cOrder = planService.getCOrderByCOrderId(orderId);
        return cOrder;
    }

    /**
     * 将数据库中的订单信息传到订单信息显示页面order_list.jsp
     *
     * @param page          请求的页数
     * @param rows          每页中的数据条数
     * @param cOrderExample 订单对象的Example
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listOrder(Integer page, Integer rows, COrderExample cOrderExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingCOrder(page, rows, cOrderExample);

        //System.out.println("W//com.wangdao.controller.plan.OrderController.listOrder/formDataPostman="+ formDataPostman);

        return formDataPostman;

    }

    /**
     * 根据订单id查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_order_by_orderId")
    @ResponseBody
    public FormDataPostman searchOrderByOrderId(Integer page, Integer rows, String searchValue) throws Exception {

        COrderExample cOrderExample = new COrderExample();
        COrderExample.Criteria criteria = cOrderExample.createCriteria();
        criteria.andOrderIdLike("%" + searchValue + "%");

        FormDataPostman result = listOrder(page, rows, cOrderExample);
        return result;
    }

    /**
     * 根据客户名称查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_order_by_orderCustom")
    @ResponseBody
    public FormDataPostman searchOrderByOrderCustom(Integer page, Integer rows, String searchValue) throws Exception {

        COrderExample cOrderExample = new COrderExample();
        COrderExample.Criteria criteria = cOrderExample.createCriteria();
        List<String> customIds=planService.getCustomIdsByCustomName("%"+searchValue+"%");
        //System.out.println("w//customids="+customIds);
        criteria.andCustomIdIn(customIds);

        if (customIds.size() == 0) {
            //如果取不到对应搜索的Id的值 构建一个返回对象
            return new FormDataPostman(0, customIds);
        }

        FormDataPostman result = listOrder(page, rows, cOrderExample);
        return result;
    }

    /**
     * 根据产品名称查找
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_order_by_orderProduct")
    @ResponseBody
    public FormDataPostman searchOrderByProductName(Integer page, Integer rows, String searchValue) throws Exception {
        COrderExample cOrderExample = new COrderExample();
        COrderExample.Criteria criteria = cOrderExample.createCriteria();
        List<String> productIds=planService.getProductIdsByProductName("%"+searchValue+"%");
        criteria.andProductIdIn(productIds);

        if (productIds.size() == 0) {
            //如果取不到对应搜索的Id的值 构建一个返回对象
            return new FormDataPostman(0,productIds);
        }

        FormDataPostman result = listOrder(page, rows, cOrderExample);
        return result;
    }

    /**
     * Todo:删除字段之前先对用户的权限进行判断？
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delete_judge")
    @ResponseBody
    private StatusMessagePostman delete_judge() throws Exception {
        return null;
    }

    /**
     * 删除前端传回的ids对应的数据
     *
     * @param ids 需要被删除的订单编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteCOrdersByIds(ids);
        if (result != 0) {
            return new StatusMessagePostman(200, "OK", null);
        } else {
            return null;
        }

    }

    /**
     * Todo:增加字段之前先对用户的权限进行判断？
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/add_judge")
    @ResponseBody
    private StatusMessagePostman add_judge() throws Exception {
        return null;
    }

    /**
     * 处理打开新增订单窗口的请求
     *
     * @return 转发到order_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openOrderADD() throws Exception {
        return "order_add";
    }

//    @RequestMapping(value="/insert", method= RequestMethod.POST)
//    @ResponseBody
//    private StatusMessagePostman insert(@Valid COrder cOrder, BindingResult bindingResult) throws Exception {
//        StatusMessagePostman result;
//        if(bindingResult.hasErrors()){
//            FieldError fieldError = bindingResult.getFieldError();
//            System.out.println(fieldError.getDefaultMessage());
//            return new StatusMessagePostman(100,fieldError.getDefaultMessage(),null);
//        }
//        if(planService.selectOrderByOrderId(cOrder.getOrderId()) != null){
//            result = new StatusMessagePostman(0, "该订单编号已经存在，请更换订单编号！", null);
//        }else{
//            result = planService.insertOrder(cOrder);
//        }
//        return result;
//    }

    /**
     * 实现新增订单的功能
     *
     * @param cOrder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(COrder cOrder) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.COrderController.insert/cOrder=" + cOrder);

        StatusMessagePostman statusMessagePostman;
        if (planService.selectOrderByOrderId(cOrder.getOrderId()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该订单编号已经存在，请修改订单编号！", null);
        } else {
            int result = planService.insertOrder(cOrder);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 查询全部corder对象
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_data")
    @ResponseBody
    public List<COrder> getCOrderData() throws Exception {
        List<COrder> cOrderList = planService.findAllCOrderNotPage();
        return cOrderList;
    }

    /**
     * Todo:推测是用于权限判断
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit_judge")
    @ResponseBody
    public String edit_judge() throws Exception {
        return null;
    }

    /**
     * 跳转到编辑页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/edit")
    public String openCOrderEdit() throws Exception {
        return "order_edit";
    }

    /**
     * 实现更新操作
     * /update
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param cOrder
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateCustom(@ModelAttribute COrder cOrder) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.COrderController.updateDevice/cOrder=" + cOrder);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateOrder(cOrder);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }


}

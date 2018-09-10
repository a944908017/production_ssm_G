package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.plan.Custom;
import com.wangdao.bean.plan.Manufacture;
import com.wangdao.bean.plan.ManufactureExample;
import com.wangdao.bean.plan.Work;
import com.wangdao.service.plan.PlanService;
import com.wangdao.service.technology.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: ManufactureController:处理计划进度模块中生产计划模块中的功能
 * @date 2018/9/4 11:03
 */
@Controller
@RequestMapping("/manufacture")
public class ManufactureController {

    /**
     * 自动注入PlanService
     */
    @Autowired
    @Qualifier("PlanService")
    private PlanService planService;

    /**
     * 自动注入TechnologyService
     */
    @Autowired
    @Qualifier("technologyService")
    private TechnologyService technologyService;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(simpleDateFormat, true));
    }

    /*********生产计划管理**********/
    /**
     * 处理用户查询生产计划的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由ManufactureOrder来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findManufacture() {
        //转到订单列表显示的页面上
        return "manufacture_list";
    }

    /**
     * 将数据库中的订单信息传到订单信息显示页面order_list.jsp
     *
     * @param page               请求的页数
     * @param rows               每页中的数据条数
     * @param manufactureExample 订单对象
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listManufacture(Integer page, Integer rows, ManufactureExample manufactureExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingManufacture(page, rows, manufactureExample);

        //System.out.println("W//com.wangdao.controller.plan.ManufactureController.listManufacture/formDataPostman="+ formDataPostman);

        return formDataPostman;

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
     * @param ids 需要被删除的生产计划编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteManufacturesByIds(ids);
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
     * 处理打开新增生产计划窗口的请求
     *
     * @return 转发到manufacture_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openManufactureADD() throws Exception {
        return "manufacture_add";
    }

    /**
     * 实现新增订单的功能
     *
     * @param manufacture
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(Manufacture manufacture) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.ManufactureController.insert/manufacture=" + manufacture);

        StatusMessagePostman statusMessagePostman;
        if (planService.selectManufactureByManufactureId(manufacture.getManufactureSn()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该订单编号已经存在，请修改订单编号！", null);
        } else {
            int result = planService.insertManufacture(manufacture);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 根据manufactureSn来查询对应的Manufacture对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_manufacture_by_manufactureSn")
    @ResponseBody
    public FormDataPostman searchManufactureByManufactureSn(Integer page, Integer rows, String searchValue) throws Exception {
        ManufactureExample manufactureExample = new ManufactureExample();
        ManufactureExample.Criteria criteria = manufactureExample.createCriteria();
        criteria.andManufactureSnLike("%" + searchValue + "%");

        FormDataPostman result = listManufacture(page, rows, manufactureExample);
        return result;
    }

    /**
     * 根据manufactureOrderId订单编号 来查询对应的Manufacture对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_manufacture_by_manufactureOrderId")
    @ResponseBody
    public FormDataPostman searchManufactureByManufactureOrderId(Integer page, Integer rows, String searchValue) throws Exception {
        ManufactureExample manufactureExample = new ManufactureExample();
        ManufactureExample.Criteria criteria = manufactureExample.createCriteria();
        criteria.andOrderIdLike("%" + searchValue + "%");

        FormDataPostman result = listManufacture(page, rows, manufactureExample);
        return result;
    }

    /**
     * 根据manufactureTechnologyName工艺的名称 来查询对应的Manufacture对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_manufacture_by_manufactureTechnologyName")
    @ResponseBody
    public FormDataPostman searchManufactureByManufactureTechnologyName(Integer page, Integer rows, String searchValue) throws Exception {
        ManufactureExample manufactureExample = new ManufactureExample();
        ManufactureExample.Criteria criteria = manufactureExample.createCriteria();
        //此处方法使用的实际上是：technologyService.getTechnologyIdsByTechnologyName()
        List<String> technologyIds = technologyService.getDeviceIdsByDeviceName("%" + searchValue + "%");
        //System.out.println("w//technologyIds=" + technologyIds);
        criteria.andTechnologyIdIn(technologyIds);
        //System.out.println("w//manufactureExample=" + manufactureExample);
        if (technologyIds.size() == 0) {
            //如果取不到对应搜索的Id的值 构建一个返回对象
            return new FormDataPostman(0, technologyIds);
        }

        FormDataPostman result = listManufacture(page, rows, manufactureExample);
        return result;
    }

    /**
     * 根据ManufactureId返回作业对象
     *
     * @param manufactureSn 从前端随url发出的manufactureSn
     * @return 返回当前请求的数据对应的生产计划 Manufacture 对象
     * @throws Exception
     */
    @RequestMapping("/get/{manufactureSn}")
    @ResponseBody
    //@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上
    public Manufacture getManufactureByManufactureSn(@PathVariable String manufactureSn) throws Exception {
        //System.out.println("w//com.wangdao.controller.plan.ManufactureController.getManufactureByManufactureSn/manufactureSn="+manufactureSn);
        Manufacture manufacture = planService.selectManufactureByManufactureId(manufactureSn);
        return manufacture;
    }

    /**
     * 查询全部Manufacture对象用于添加数据的页面的产品下拉框数据填充
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_data")
    @ResponseBody
    public List<Manufacture> getManufactureDataBeforeAdd() throws Exception {
        List<Manufacture> manufactureList = planService.findAllManufactureNotPage();
        return manufactureList;
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
    public String openManufactureEdit() throws Exception {
        return "manufacture_edit";
    }

    /**
     * 实现更新操作
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param manufacture
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateManufacture(@ModelAttribute Manufacture manufacture) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.ManufactureController.updateManufacture/manufacture=" + manufacture);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateManufacture(manufacture);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }
}

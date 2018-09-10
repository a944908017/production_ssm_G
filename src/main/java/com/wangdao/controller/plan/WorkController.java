package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.plan.*;
import com.wangdao.service.device.DeviceService;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: WorkController:处理计划进度模块中作业管理模块中的请求
 * @date 2018/9/3 22:52
 */
@Controller
@RequestMapping("/work")
public class WorkController {

    /**
     * 自动注入PlanService
     */
    @Autowired
    @Qualifier("PlanService")
    private PlanService planService;

    /**
     * 自动注入DeviceService
     */
    @Autowired
    @Qualifier("deviceService")
    private DeviceService deviceService;

    /*********作业管理**********/

    /**
     * 处理用户查询作业的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由listWork来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findWork() {
        //转到作业列表显示的页面上
        return "work_list";
    }

    /**
     * 将数据库中的作业信息传到产品信息显示页面work_list.jsp
     *
     * @param page   请求的页数
     * @param rows   每页中的数据条数
     * @param workExample   作业对象
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listWork(Integer page, Integer rows, WorkExample workExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingWork(page, rows, workExample);

        //System.out.println("W//com.wangdao.controller.plan.WorkController.listWork/formDataPostman="+ formDataPostman);

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
     * @param ids 需要被删除的作业编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteWorksByIds(ids);
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
     * 处理打开新增作业窗口的请求
     *
     * @return 转发到work_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openWorkADD() throws Exception {
        return "work_add";
    }

    /**
     * 实现新增作业的功能
     * @param work
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(Work work) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.COrderController.insert/work=" + work);

        StatusMessagePostman statusMessagePostman;
        if (planService.selectWorkByWorkId(work.getWorkId()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该订单编号已经存在，请修改作业编号！", null);
        } else {
            int result = planService.insertWork(work);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 根据workId返回作业对象
     *
     * @param workId 从前端随url发出的workId
     * @return 返回当前请求的生产派工信息对应的作业对象
     * @throws Exception
     */
    @RequestMapping("/get/{workId}")
    @ResponseBody
    //@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上
    public Work getWorkByWorkId(@PathVariable String workId) throws Exception {
        //System.out.println("w//com.wangdao.controller.plan.WorkController.getWorkByWorkId/workId="+workId);
        Work work = planService.getWorkByWorkId(workId);
        return work;
    }

    /**
     * 根据workId来模糊查询对应的Work对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_work_by_workId")
    @ResponseBody
    public FormDataPostman searchWorkByWorkId(Integer page, Integer rows, String searchValue) throws Exception {
        WorkExample workExample=new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        criteria.andWorkIdLike("%"+searchValue+"%");

        FormDataPostman result= listWork(page,rows,workExample);
        return result;
    }

    /**
     * 根据workProduct产品名称 来模糊查询对应的Work对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_work_by_workProduct")
    @ResponseBody
    public FormDataPostman searchWorkByWorkProduct(Integer page, Integer rows, String searchValue) throws Exception {
        WorkExample workExample=new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        //根据ProductName来模糊查询对应的ProductId的List
        List<String> productIds=planService.getProductIdsByProductName("%"+searchValue+"%");

        //System.out.println("w//com.wangdao.controller.plan.WorkController.searchWorkByWorkProduct/productIds="+productIds);

        criteria.andProductIdIn(productIds);

        if (productIds.size() == 0) {
            //如果取不到对应搜索的Id的值 构建一个返回对象
            return new FormDataPostman(0, productIds);
        }

        FormDataPostman result= listWork(page,rows,workExample);
        return result;
    }

    /**
     * 根据workDevice设备名称 来模糊查询对应的Work对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_work_by_workDevice")
    @ResponseBody
    public FormDataPostman searchWorkByWorkDevice(Integer page, Integer rows, String searchValue) throws Exception {
        WorkExample workExample=new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();

        //根据DeviceName来模糊查询对应的DeviceId的List

        List<String> deviceIds=deviceService.getDevcieIdsByDeviceName("%"+searchValue+"%");

        //System.out.println("w//com.wangdao.controller.plan.WorkController.searchWorkByWorkDevice/deviceIds="+deviceIds);

        criteria.andDeviceIdIn(deviceIds);

        if (deviceIds.size() == 0) {
            //如果取不到对应搜索的Id的值 构建一个返回对象
            return new FormDataPostman(0, deviceIds);
        }

        FormDataPostman result= listWork(page,rows,workExample);
        return result;
    }

    /**
     * 根据workProcess工序id 来模糊查询对应的Work对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_work_by_workProcess")
    @ResponseBody
    public FormDataPostman searchWorkByWorkProcess(Integer page, Integer rows, String searchValue) throws Exception {
        WorkExample workExample=new WorkExample();
        WorkExample.Criteria criteria = workExample.createCriteria();
        criteria.andProcessIdLike("%"+searchValue+"%");

        FormDataPostman result= listWork(page,rows,workExample);
        return result;
    }

    /**
     * 查询全部Work对象用于添加数据的页面的产品下拉框数据填充
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_data")
    @ResponseBody
    public List<Work> getWorkDataBeforeAdd() throws Exception {
        List<Work> workList = planService.findAllWorkNotPage();
        return workList;
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
    public String openWorkEdit() throws Exception {
        return "work_edit";
    }

    /**
     * 实现更新操作
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param work
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateWork(@ModelAttribute Work work) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.WorkController.updateWork/work=" + work);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateWork(work);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }


}

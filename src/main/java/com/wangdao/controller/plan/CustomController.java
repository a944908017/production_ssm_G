package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.device.DeviceExample;
import com.wangdao.bean.plan.COrder;
import com.wangdao.bean.plan.Custom;
import com.wangdao.bean.plan.CustomExample;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: CustomController:处理计划进度模块中客户管理模块的功能
 * @date 2018/9/1 1:04
 */
@Controller
@RequestMapping("/custom")
public class CustomController {

    /**
     * 自动注入PlanService
     */
    @Autowired
    @Qualifier("PlanService")
    private PlanService planService;


    /*********客户管理**********/

    /**
     * 处理用户查询客户的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由listCustom来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findCustom() {
        //转到订单列表显示的页面上
        return "custom_list";
    }


    /**
     * 将数据库中的客户信息传到客户信息显示页面custom_list.jsp
     *
     * @param page   请求的页数
     * @param rows   每页中的数据条数
     * @param customExample 客户对象example
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listCustom(Integer page, Integer rows, CustomExample customExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingCustom(page, rows, customExample);

        //System.out.println("W//com.wangdao.controller.plan.OrderController.listOrder/formDataPostman="+ formDataPostman);

        return formDataPostman;

    }

    /**
     * 处理订单管理模块中客户信息框的回显请求
     *
     * @param customId 从前端随url发出的选中的订单对应的customId
     * @return 返回当前请求的订单对应的Custom对象
     * @throws Exception
     */
    @RequestMapping("/get/{customId}")
    @ResponseBody
    //@PathVariable用于将请求URL中的模板变量映射到功能处理方法的参数上
    public Custom getCustomByCustomId(@PathVariable String customId) throws Exception {
        //System.out.println("w//com.wangdao.controller.plan.CustomController.getCustomByOrderId/customId="+customId);
        Custom custom = planService.getCustomByCustomId(customId);
        return custom;
    }

    /**
     * 查询全部custom对象用于添加order数据的页面的客户下拉框数据填充
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/get_data")
    @ResponseBody
    public List<Custom> getCustomDataBeforeOrderAdd() throws Exception {
        List<Custom> customList = planService.findAllCustomNotPage();
        return customList;
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
     * @param ids 需要被删除的客户编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteCustomsByIds(ids);
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
     * 处理打开 新增客户 窗口的请求
     *
     * @return 转发到custom_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openCustomADD() throws Exception {
        return "custom_add";
    }

    /**
     * 实现新增客户信息的功能
     * @param custom
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(Custom custom) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.CustomController.insert/custom=" + custom);

        StatusMessagePostman statusMessagePostman;
        if (planService.getCustomByCustomId(custom.getCustomId()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该客户编号已经存在，请修改订单编号！", null);
        } else {
            int result = planService.insertCustom(custom);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 根据customId来模糊查询对应的Custom对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_custom_by_customId")
    @ResponseBody
    public FormDataPostman searchCustomByCustomId(Integer page, Integer rows, String searchValue) throws Exception {

        CustomExample customExample=new CustomExample();
        CustomExample.Criteria criteria = customExample.createCriteria();
        criteria.andCustomIdLike("%"+searchValue+"%");

        FormDataPostman result= listCustom(page,rows,customExample);
        return result;
    }

    /**
     * 根据CustomName来查询对应的Custom对象
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_custom_by_customName")
    @ResponseBody
    public FormDataPostman searchCustomByCustomName(Integer page, Integer rows, String searchValue) throws Exception {
        CustomExample customExample=new CustomExample();
        CustomExample.Criteria criteria = customExample.createCriteria();
        criteria.andCustomNameLike("%"+searchValue+"%");

        FormDataPostman result= listCustom(page,rows,customExample);
        return result;
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
    public String openCustomEdit() throws Exception {
        return "custom_edit";
    }

    /**
     * 实现更新操作
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param custom
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateCustom(@ModelAttribute Custom custom) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.CustomController.updateCustom/custom=" + custom);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateCustom(custom);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }

}

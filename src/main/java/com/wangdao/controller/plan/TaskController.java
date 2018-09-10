package com.wangdao.controller.plan;

import com.wangdao.bean.dataPostman.FormDataPostman;
import com.wangdao.bean.dataPostman.StatusMessagePostman;
import com.wangdao.bean.plan.Manufacture;
import com.wangdao.bean.plan.ManufactureExample;
import com.wangdao.bean.plan.Task;
import com.wangdao.bean.plan.TaskExample;
import com.wangdao.service.plan.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wangxy013
 * website：<a href="https://github.com/Wangxy013">GitHub:Wangxy013</a>
 * @version V1.0
 * @Description: TaskController:处理计划进度模块中生产派工小模块的请求
 * @date 2018/9/4 12:32
 */

@Controller
@RequestMapping("/task")
public class TaskController {

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

    /*********生产派工管理**********/
    /**
     * 处理用户查询生产派工的请求，因为前端会先后发送两次请求，所以本方法的作用仅仅是转到静态页面，实际处理数据的工作由taskOrder来实现
     *
     * @return
     */
    @RequestMapping(value = "/find")
    public String findTask() {
        //转到订单列表显示的页面上
        return "task_list";
    }

    /**
     * 将数据库中的订单信息传到订单信息显示页面task_list.jsp
     *
     * @param page   请求的页数
     * @param rows   每页中的数据条数
     * @param taskExample   生产派工对象
     * @return 返回一个FromDatePostman对象，其中包括该页面需要显示的数据
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public FormDataPostman listTask(Integer page, Integer rows, TaskExample taskExample) {

        FormDataPostman formDataPostman = new FormDataPostman();

        formDataPostman = planService.fingTask(page, rows, taskExample);

        //System.out.println("W//com.wangdao.controller.plan.TaskController.listTask/formDataPostman="+ formDataPostman);

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
     * @param ids 需要被删除的生产派工编号数组
     * @return 返回一个专门处理状态信息的对象
     * @throws Exception
     */
    @RequestMapping(value = "/delete_batch")
    @ResponseBody
    private StatusMessagePostman delete_batch(String[] ids) throws Exception {

        int result = planService.deleteTaskByIds(ids);
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
     * 处理打开新增生产派工信息窗口的请求
     *
     * @return 转发到task_add.jsp
     * @throws Exception
     */
    @RequestMapping("/add")
    public String openTaskADD() throws Exception {
        return "task_add";
    }

    /**
     * 实现新增生产派工信息的功能
     * @param task
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    private StatusMessagePostman insert(Task task) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.TaskController.insert/task=" + task);

        StatusMessagePostman statusMessagePostman;
        if (planService.selectTaskByTaskId(task.getTaskId()) != null) {
            statusMessagePostman = new StatusMessagePostman(0, "该订单编号已经存在，请修改作业编号！", null);
        } else {
            int result = planService.insertTask(task);
            if (result != 0) {
                statusMessagePostman = new StatusMessagePostman(200, "OK", null);
            } else {
                statusMessagePostman = null;
            }
        }
        return statusMessagePostman;
    }

    /**
     * 根据taskId来查询对应的Task对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_task_by_taskId")
    @ResponseBody
    public FormDataPostman searchTaskeByTaskId(Integer page, Integer rows, String searchValue) throws Exception {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andTaskIdLike("%" + searchValue + "%");

        FormDataPostman result = listTask(page, rows, taskExample);
        return result;
    }

    /**
     * 根据taskWorkId 作业编号 来查询对应的Task对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_task_by_taskWorkId")
    @ResponseBody
    public FormDataPostman searchTaskeByTaskWorkId(Integer page, Integer rows, String searchValue) throws Exception {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andWorkIdLike("%" + searchValue + "%");

        FormDataPostman result = listTask(page, rows, taskExample);
        return result;
    }

    /**
     * 根据taskManufactureSn生产批号 来查询对应的Task对象
     *
     * @param page
     * @param rows
     * @param searchValue
     * @return
     * @throws Exception
     */
    @RequestMapping("/search_task_by_taskManufactureSn")
    @ResponseBody
    public FormDataPostman searchTaskeByTaskManufactureSn(Integer page, Integer rows, String searchValue) throws Exception {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andManufactureSnLike("%" + searchValue + "%");

        FormDataPostman result = listTask(page, rows, taskExample);
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
    public String openTaskEdit() throws Exception {
        return "task_edit";
    }

    /**
     * 实现更新操作
     * /update_all 处理点击编辑按钮后弹窗中的编辑请求，处理点击关键字后弹窗中的编辑请求
     * /update_note 处理富文本信息编辑框中的修改请求
     *
     * @param task
     * @return
     * @throws Exception
     */
    @RequestMapping(value = {"/update_all", "/update_note"})
    @ResponseBody
    public StatusMessagePostman updateTask(@ModelAttribute Task task) throws Exception {
        //System.out.println("W//com.wangdao.controller.plan.ManufactureController.updateTask/task=" + task);

        StatusMessagePostman statusMessagePostman;
        int result = planService.updateTask(task);
        if (result != 0) {
            statusMessagePostman = new StatusMessagePostman(200, "OK", null);
        } else {
            statusMessagePostman = new StatusMessagePostman(100, "Error，更新失败！", "null");
        }

        return statusMessagePostman;
    }


}

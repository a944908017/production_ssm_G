package com.wangdao.controller.qualifycontrol;

import com.wangdao.bean.qualify.ProcessCountCheck;
import com.wangdao.service.qualifycontrol.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 工序计数质检
 */
@RequestMapping
@Controller
public class ProcessCountCheckController {
    @Autowired
    @Qualifier("QualityService")
    QualityService qualityService;

    /**
     * 前往工序计数质检的列表
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("p_count_check/find")
    public ModelAndView pCountCheckFind(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("pCountCheck:add");
        sysPermissionList.add("pCountCheck:edit");
        sysPermissionList.add("pCountCheck:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("p_count_check_list");
        return mv;
    }

    /**
     * 给工序计数质检列表页返回json数据
     *
     * @return
     */
    @RequestMapping("p_count_check/list")
    @ResponseBody
    public HashMap<String, Object> pCountCheckList(String page, String rows) {

        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<ProcessCountCheck> processCountCheckList = qualityService.selectPCountCheckByPage(limit, offset);
        Integer total = qualityService.selectAllPCountCheck();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", processCountCheckList);
        return map;
    }

    /**
     * 根据工序计数质检编号模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("p_count_check/search_pCountCheck_by_pCountCheckId")
    @ResponseBody
    public HashMap<String, Object> search_pCountCheck_by_pCountCheckId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<ProcessCountCheck> processCountCheckList = qualityService.selectPCountByCheckIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectPCountCheckCountByCheckIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", processCountCheckList);
        return map;
    }


    /**
     * 新增操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("pCountCheck/add_judge")
    public String pCountCheck_add_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * 前往新增页面
     *
     * @param mv
     * @return
     */
    @ResponseBody
    @RequestMapping("p_count_check/add")
    public ModelAndView p_count_check_add(ModelAndView mv) {
        mv.setViewName("p_count_check_add");
        return mv;
    }

    /**
     * 新增一个工序计量质检
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("p_count_check/insert")
    public HashMap<String, Object> p_count_check_insert(ProcessCountCheck processCountCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.insertPCountCheck(processCountCheck);
        if (ret) {
            map.put("status", 200);
        }else {
            map.put("status", 500);
            map.put("msg", "增加失败");
        }
        return map;
    }

    /**
     * 编辑操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("pCountCheck/edit_judge")
    public String pCountCheck_edit_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * 前往编辑页面
     *
     * @param mv
     * @return
     */
    @ResponseBody
    @RequestMapping("p_count_check/edit")
    public ModelAndView p_count_check_edit(ModelAndView mv) {
        mv.setViewName("p_count_check_edit");
        return mv;
    }

    /**
     * 更新工序计数质检
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"p_count_check/update_all","/p_count_check/update_note"})
    public HashMap<String, Object> p_count_check_update_all(ProcessCountCheck processCountCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.updateProcessCountCheck(processCountCheck);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

    /**
     * 删除操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("pCountCheck/delete_judge")
    public String pCountCheck_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * [批量]删除指定id的工序计数质检
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("p_count_check/delete_batch")
    public HashMap<String, Object> p_count_check_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.deletePCountCheckByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }


}

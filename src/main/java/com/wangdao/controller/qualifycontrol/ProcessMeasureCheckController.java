package com.wangdao.controller.qualifycontrol;

import com.wangdao.bean.qualify.ProcessMeasureCheck;
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
 * 工序计量质检
 */
@RequestMapping
@Controller
public class ProcessMeasureCheckController {
    @Autowired
    @Qualifier("QualityService")
    QualityService qualityService;

    /**
     * 前往工序计量质检的列表
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("p_measure_check/find")
    public ModelAndView pMeasureCheckFind(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("pMeasureCheck:add");
        sysPermissionList.add("pMeasureCheck:edit");
        sysPermissionList.add("pMeasureCheck:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("p_measure_check_list");
        return mv;
    }

    /**
     * 给工序计量质检列表页返回json数据
     *
     * @return
     */
    @RequestMapping("p_measure_check/list")
    @ResponseBody
    public HashMap<String, Object> pMeasureCheckList(String page, String rows) {

        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<ProcessMeasureCheck> processMeasureCheckList = qualityService.selectPMeasureCheckByPage(limit, offset);
        Integer total = qualityService.selectAllPMeasureCheck();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", processMeasureCheckList);
        return map;
    }

    /**
     * 根据工序计量质检编号模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("p_measure_check/search_pMeasureCheck_by_pMeasureCheckId")
    @ResponseBody
    public HashMap<String, Object> search_pMeasureCheck_by_pMeasureCheckId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<ProcessMeasureCheck> processMeasureCheckList = qualityService.selectPMeasureByCheckIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectPMeasureCheckCountByCheckIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", processMeasureCheckList);
        return map;
    }


    /**
     * 新增操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("pMeasureCheck/add_judge")
    public String pMeasureCheck_add_judge() {
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
    @RequestMapping("p_measure_check/add")
    public ModelAndView p_measure_check_add(ModelAndView mv) {
        mv.setViewName("p_measure_check_add");
        return mv;
    }

    /**
     * 新增一个工序计量质检
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("p_measure_check/insert")
    public HashMap<String, Object> p_measure_check_insert(ProcessMeasureCheck processMeasureCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.insertPMeasureCheck(processMeasureCheck);
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
    @RequestMapping("pMeasureCheck/edit_judge")
    public String pMeasureCheck_edit_judge() {
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
    @RequestMapping("p_measure_check/edit")
    public ModelAndView p_measure_check_edit(ModelAndView mv) {
        mv.setViewName("p_measure_check_edit");
        return mv;
    }

    /**
     * 更新工序计量质检
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"p_measure_check/update_all","/p_measure_check/update_note"})
    public HashMap<String, Object> p_measure_check_update_all(ProcessMeasureCheck processMeasureCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.updateProcessMeasureCheck(processMeasureCheck);
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
    @RequestMapping("pMeasureCheck/delete_judge")
    public String pMeasureCheck_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * [批量]删除指定id的工序计量质检
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("p_measure_check/delete_batch")
    public HashMap<String, Object> p_measure_check_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.deletePmeasureCheckByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }


}

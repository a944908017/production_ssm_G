package com.wangdao.controller.qualifycontrol;

import com.wangdao.bean.qualify.FinalCountCheck;
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
 * 成品计数质检
 */
@RequestMapping
@Controller
public class FinalCountCheckController {
    @Autowired
    @Qualifier("QualityService")
    QualityService qualityService;

    /**
     * 前往成品计数质检的列表
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("f_count_check/find")
    public ModelAndView fCountCheckFind(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("fCountCheck:add");
        sysPermissionList.add("fCountCheck:edit");
        sysPermissionList.add("fCountCheck:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("f_count_check_list");
        return mv;
    }

    /**
     * 给成品计数质检列表页返回json数据
     *
     * @return
     */
    @RequestMapping("f_count_check/list")
    @ResponseBody
    public HashMap<String, Object> fCountCheckList(String page, String rows) {

        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalCountCheck> fCountCheckList = qualityService.selectFCountCheckByPage(limit, offset);
        Integer total = qualityService.selectAllfCountCheck();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", fCountCheckList);
        return map;
    }

    /**
     * 根据成品计数质检编号模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("f_count_check/search_fCountCheck_by_fCountCheckId")
    @ResponseBody
    public HashMap<String, Object> search_fCountCheck_by_fCountCheckId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalCountCheck> fCountCheckList = qualityService.selectFCountByCheckIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectFCountCheckCountByCheckIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", fCountCheckList);
        return map;
    }

    /**
     * 根据订单编号名模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("f_count_check/search_fCountCheck_by_orderId")
    @ResponseBody
    public HashMap<String, Object> search_fCountCheck_by_orderId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalCountCheck> finalCountCheckList = qualityService.selectFCountCheckByOrderIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectFCountCheckCountByOrderIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", finalCountCheckList);
        return map;
    }


    /**
     * 新增操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("fCountCheck/add_judge")
    public String fCountCheck_add_judge() {
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
    @RequestMapping("f_count_check/add")
    public ModelAndView f_count_check_add(ModelAndView mv) {
        mv.setViewName("f_count_check_add");
        return mv;
    }

    /**
     * 新增一个成品计数质检
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("f_count_check/insert")
    public HashMap<String, Object> f_count_check_insert(FinalCountCheck finalCountCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.insertFCountCheck(finalCountCheck);
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
    @RequestMapping("fCountCheck/edit_judge")
    public String fCountCheck_edit_judge() {
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
    @RequestMapping("f_count_check/edit")
    public ModelAndView f_count_check_edit(ModelAndView mv) {
        mv.setViewName("f_count_check_edit");
        return mv;
    }

    /**
     * 更新成品计数质检
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"f_count_check/update_all","f_count_check/update_note"})
    public HashMap<String, Object> f_count_check_update_all(FinalCountCheck finalCountCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.updateFinalCountCheck(finalCountCheck);
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
    @RequestMapping("fCountCheck/delete_judge")
    public String fCountCheck_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * [批量]删除指定id的成品计数质检
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("f_count_check/delete_batch")
    public HashMap<String, Object> f_count_check_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.deleteFCountCheckByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

}

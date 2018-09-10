package com.wangdao.controller.qualifycontrol;

import com.wangdao.bean.qualify.UnqualifyApply;
import com.wangdao.service.qualifycontrol.QualityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 不合格品管理
 */
@RequestMapping("/unqualify")
@Controller
public class QualityController {
    @Autowired
    @Qualifier("QualityService")
    QualityService qualityService;


    /**
     * 前往不合格品的列表
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("/find")
    public ModelAndView unqualifyFind(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        sysPermissionList.add("unqualify:add");
        sysPermissionList.add("unqualify:edit");
        sysPermissionList.add("unqualify:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("unqualify_list");
        return mv;
    }

    /**
     * 给不合格品列表页返回json数据
     *
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public HashMap<String, Object> unqualifyList(String page, String rows) {

        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<UnqualifyApply> unqualifyList = qualityService.selectUnqualifyApplyByPage(limit, offset);
        Integer total = qualityService.selectAllCount();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", unqualifyList);
        return map;
    }

    /**
     * 根据不合格品申请编号模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search_unqualify_by_unqualifyId")
    @ResponseBody
    public HashMap<String, Object> search_unqualify_by_unqualifyId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<UnqualifyApply> unqualifyList = qualityService.selectUnqualifyByIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectCountByIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", unqualifyList);
        return map;
    }

    /**
     * 根据产品名模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search_unqualify_by_productName")
    @ResponseBody
    public HashMap<String, Object> search_unqualify_by_productName(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<UnqualifyApply> unqualifyList = qualityService.selectUnqualifyByPnameCondition(searchValue, limit, offset);
        Integer total = qualityService.selectCountByPNameCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", unqualifyList);
        return map;
    }

    /**
     * 新增操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/add_judge")
    public String unqualify_add_judge() {
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
    @RequestMapping("/add")
    public ModelAndView unqualify_add(ModelAndView mv) {
        mv.setViewName("unqualify_add");
        return mv;
    }

    /**
     * 新增一个不合格品
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/insert")
    public HashMap<String, Object> unqualify_insert(UnqualifyApply unqualifyApply) {

        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.insertUnqualify(unqualifyApply);
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
    @RequestMapping("/edit_judge")
    public String unqualify_edit_judge() {
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
    @RequestMapping("/edit")
    public ModelAndView unqualify_edit(ModelAndView mv) {
        mv.setViewName("unqualify_edit");
        return mv;
    }

    /**
     * 更新不合格品
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"/update_all","/update_note"})
    public HashMap<String, Object> unqualify_update_all(UnqualifyApply unqualifyApply) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.updateUnqualifyApply(unqualifyApply);
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
    @RequestMapping("/delete_judge")
    public String unqualify_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * [批量]删除指定id的不合格品
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/delete_batch")
    public HashMap<String, Object> unqualify_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.deleteByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }


}

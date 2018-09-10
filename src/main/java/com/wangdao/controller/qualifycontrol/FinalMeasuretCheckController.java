package com.wangdao.controller.qualifycontrol;

import com.wangdao.bean.qualify.FinalMeasuretCheck;
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
 * 成品计量质检
 */
@RequestMapping
@Controller
public class FinalMeasuretCheckController {
    @Autowired
    @Qualifier("QualityService")
    QualityService qualityService;

    /**
     * 前往成品计量质检的列表
     *
     * @param mv
     * @param session
     * @return
     */
    @RequestMapping("measure/find")
    public ModelAndView measureFind(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("fMeasureCheck:add");
        sysPermissionList.add("fMeasureCheck:edit");
        sysPermissionList.add("fMeasureCheck:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("measurement_list");
        return mv;
    }

    /**
     * 给成品计量质检列表页返回json数据
     *
     * @return
     */
    @RequestMapping("measure/list")
    @ResponseBody
    public HashMap<String, Object> measureList(String page, String rows) {

        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalMeasuretCheck> measureList = qualityService.selectMeasureByPage(limit, offset);
        Integer total = qualityService.selectAllMeasureCount();

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", measureList);
        return map;
    }

    /**
     * 根据成品计量质检编号模糊查询
     *
     * @param searchValue
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("measure/search_fMeasureCheck_by_fMeasureCheckId")
    @ResponseBody
    public HashMap<String, Object> search_fMeasureCheck_by_fMeasureCheckId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalMeasuretCheck> measureList = qualityService.selectMeasureByCheckIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectMeasureCountByCheckIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", measureList);
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
    @RequestMapping("measure/search_fMeasureCheck_by_orderId")
    @ResponseBody
    public HashMap<String, Object> search_fMeasureCheck_by_orderId(String searchValue, String page, String rows) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<FinalMeasuretCheck> measureList = qualityService.selectMeasureByOrderIdCondition(searchValue, limit, offset);
        Integer total = qualityService.selectMeasureCountByOrderIdCondition(searchValue);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", measureList);
        return map;
    }


    /**
     * 新增操作前判断
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("fMeasureCheck/add_judge")
    public String fMeasureCheck_add_judge() {
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
    @RequestMapping("measure/add")
    public ModelAndView fMeasureCheck_add(ModelAndView mv) {
        mv.setViewName("measurement_add");
        return mv;
    }

    /**
     * 新增一个成品计量质检
     * @param finalMeasuretCheck
     * @return
     */
    @ResponseBody
    @RequestMapping("measure/insert")
    public HashMap<String, Object> measure_insert(FinalMeasuretCheck finalMeasuretCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.insertFMeasureCheck(finalMeasuretCheck);
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
    @RequestMapping("fMeasureCheck/edit_judge")
    public String fMeasureCheck_edit_judge() {
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
    @RequestMapping("measure/edit")
    public ModelAndView measure_edit(ModelAndView mv) {
        mv.setViewName("measurement_edit");
        return mv;
    }

    /**
     * 更新成品计量质检
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"measure/update_all","measure/update_note"})
    public HashMap<String, Object> measure_update_all(FinalMeasuretCheck finalMeasuretCheck) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.updateFinalMeasureCheck(finalMeasuretCheck);
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
    @RequestMapping("fMeasureCheck/delete_judge")
    public String fMeasureCheck_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    /**
     * [批量]删除指定id的成品计量质检
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("measure/delete_batch")
    public HashMap<String, Object> measure_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = qualityService.deleteFMeasureCheckByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

}

package com.wangdao.controller.technology;


import com.github.pagehelper.PageHelper;
import com.wangdao.bean.FormData.FormDataPostman_2;
import com.wangdao.bean.technology.Technology;
import com.wangdao.bean.technology.TechnologyPlan;
import com.wangdao.service.technology.TechnologyPlanService;
import com.wangdao.service.technology.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TechnologyPlanController {

    @Autowired
    @Qualifier("technologyPlanService")
    private TechnologyPlanService technologyPlanService;

    @Autowired
    @Qualifier("technologyService")
    private TechnologyService technologyService;


    @RequestMapping(value = "/technologyPlan/list")
    @ResponseBody
    public String TechnologyPlanList(Integer page,Integer rows) {
        TechnologyPlan technologyPlan = new TechnologyPlan();

        FormDataPostman_2 postman = new FormDataPostman_2();
        List<TechnologyPlan> countList = technologyPlanService.findTechnologyPlan(technologyPlan);

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<TechnologyPlan> technologyPlans = technologyPlanService.findTechnologyPlan(technologyPlan);
        List<TechnologyPlan> technologyPlanList=new ArrayList<>();
        for(TechnologyPlan t:technologyPlans){
            technologyPlanList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyPlanList);

        return postman.toString();
    }

    @RequestMapping(value = "/technologyPlan/find")
    public String selectTechnologyPlan(){
        return "technologyPlan_list";
    }

    @RequestMapping(value = "/technologyPlan/search_technologyPlan_by_technologyPlanId")
    @ResponseBody
    public String TechnologyPlanSearchId(Integer page,Integer rows,String searchValue) {
        TechnologyPlan technologyPlan = new TechnologyPlan();
        technologyPlan.setTechnology_Plan_Id(searchValue);

        FormDataPostman_2 postman = new FormDataPostman_2();
        List<TechnologyPlan> countList = technologyPlanService.findTechnologyPlan(technologyPlan);

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<TechnologyPlan> technologyPlans = technologyPlanService.findTechnologyPlan(technologyPlan);
        List<TechnologyPlan> technologyPlanList=new ArrayList<>();
        for(TechnologyPlan t:technologyPlans){
            technologyPlanList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyPlanList);

        return postman.toString();
    }

    @RequestMapping(value = "/technologyPlan/search_technologyPlan_by_technologyName")
    @ResponseBody
    public String TechnologyPlanSearchTTechnologyName(Integer page,Integer rows,String searchValue) {
        //TechnologyPlan technologyPlan = new TechnologyPlan();
        //technologyPlan.setTechnology_Id(searchValue);

        FormDataPostman_2 postman = new FormDataPostman_2();
        List<Technology> technologyList=technologyService.findTechnologyByName(searchValue);

        List<String> tempTid=new ArrayList<>();
        for(Technology t:technologyList){
            tempTid.add(t.getTechnologyId());
        }

        List<TechnologyPlan> countList = technologyPlanService.findTechnologyPlanByTechnoList(tempTid);

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<TechnologyPlan> technologyPlans = technologyPlanService.findTechnologyPlanByTechnoList(tempTid);
        List<TechnologyPlan> technologyPlanList=new ArrayList<>();

        for(TechnologyPlan t:technologyPlans){
            technologyPlanList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyPlanList);
        return postman.toString();
    }

    @RequestMapping(value = "/technologyPlan/get/'{formName}")
    @ResponseBody
    public TechnologyPlan TechnologyPlanInfo(@PathVariable String formName) {
        TechnologyPlan technologyPlan = technologyPlanService.findTechnologyPlanById(formName);
        return technologyPlan;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/delete_judge")
    public String deleteJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/edit_judge")
    public String editJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/add_judge")
    public String addJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/delete_batch")
    public Map<String,String> removeTechnologyPlan(String ids){
        Map<String,String> map=new HashMap<>();
        try {
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除部门
            technologyPlanService.removeTechnologyPlanById(id);
        }
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","删除失败");
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/insert")
    public Map<String,String> insertTechnologyPlan(@ModelAttribute TechnologyPlan technologyPlan){
        Map<String,String> map=new HashMap<>();
        // 执行添加操作
        try {
        technologyPlanService.addTechnologyPlan(technologyPlan);
            map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","添加失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/technologyPlan/update_all")
    public Map<String,String> updateTechnologyPlan(
            @ModelAttribute TechnologyPlan technologyPlan){
        Map<String,String> map=new HashMap<>();
        try {
        technologyPlanService.modifyTechnologyPlan(technologyPlan);
            map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","修改失败");
        }
        // 执行修改操作



        return map;
    }

    @RequestMapping(value="/technologyPlan/add")
    public String addTechnologyPlan(){
        return "technologyPlan_add";
    }

    @RequestMapping(value="/technologyPlan/edit")
    public String editTechnologyPlan(){
        return "technologyPlan_edit";
    }

    @RequestMapping(value = "/technologyPlan/get_data")
    @ResponseBody
    public List<TechnologyPlan> getData() {
        TechnologyPlan technologyPlan=new TechnologyPlan();
        List<TechnologyPlan> technologyPlanList = technologyPlanService.findTechnologyPlan(technologyPlan);
        return technologyPlanList;
    }
}
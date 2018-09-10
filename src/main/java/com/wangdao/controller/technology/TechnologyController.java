package com.wangdao.controller.technology;

import com.github.pagehelper.PageHelper;
import com.wangdao.bean.FormData.FormDataPostman_2;
import com.wangdao.bean.technology.Technology;
import com.wangdao.service.technology.TechnologyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Description: 处理工艺请求控制器
 * @author   
 * @version V1.0   
 */

@Controller
public class TechnologyController {

    @Autowired
    @Qualifier("technologyService")
    private TechnologyService technologyService;

    @RequestMapping(value = "/technology/find")
    public String selectTechnology(){

        return "technology_list";
    }

    @RequestMapping(value = "/technology/list")
    @ResponseBody
    public String TechnologyList(Integer page,Integer rows){

        Technology technology=new Technology();

        FormDataPostman_2 postman=new FormDataPostman_2();
        List<Technology> countList = technologyService.findTechnology(technology);

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Technology> technologies = technologyService.findTechnology(technology);
        List<Technology> technologyList=new ArrayList<>();
        for(Technology t:technologies){
            technologyList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyList);

        return postman.toString();
    }


    @RequestMapping(value = "/technology/search_technology_by_technologyId")
    @ResponseBody
    public String TechnologySearchById(Integer page,Integer rows,String searchValue){

        Technology technology=new Technology();
        technology.setTechnology_Id(searchValue);

        FormDataPostman_2 postman=new FormDataPostman_2();
        List<Technology> countList = technologyService.findTechnology(technology);

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Technology> technologies = technologyService.findTechnology(technology);
        List<Technology> technologyList=new ArrayList<>();
        for(Technology t:technologies){
            technologyList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyList);

        return postman.toString();
    }

    @RequestMapping(value = "/technology/search_technology_by_technologyName")
    @ResponseBody
    public String TechnologySearchByName(Integer page,Integer rows,String searchValue){

        Technology technology=new Technology();
        technology.setTechnology_Name(searchValue);
        //System.out.println(searchValue);

        FormDataPostman_2 postman=new FormDataPostman_2();
        List<Technology> countList = technologyService.findTechnology(technology);//记录总数

        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Technology> technologies = technologyService.findTechnology(technology);//查到的分页数组
        List<Technology> technologyList=new ArrayList<>();
        for(Technology t:technologies){
            technologyList.add(t);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(technologyList);

        return postman.toString();
    }

    @ResponseBody
    @RequestMapping(value="/technology/delete_batch")
    public Map<String,String> removeTechnology(String ids){
        // 分解id字符串
        Map<String,String> map=new HashMap<>();
        try {
        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除部门
            technologyService.removeTechnologyById(id);
        }
            map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","删除失败");
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/technology/insert")
    public Map<String,String> insertTechnology(@ModelAttribute Technology technology){
        Map<String,String> map=new HashMap<>();
        // 执行添加操作
        try {
        technologyService.addTechnology(technology);
        map.put("status","200");
    }catch (Exception e){
        map.put("status","500");
        map.put("msg","添加失败");
    }
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/technology/update_all")
    public Map<String,String> updateTechnology(
            @ModelAttribute Technology technology){
        Map<String,String> map=new HashMap<>();
        try {
        technologyService.modifyTechnology(technology);
        map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","修改失败");
        }
        // 执行修改操作



        return map;
    }


    @RequestMapping(value="/technology/add")
    public String addTechnology(){
        return "technology_add";
    }

    @RequestMapping(value="/technology/edit")
    public String editTechnology(){
        return "technology_edit";
    }

    @RequestMapping(value = "/technology/get/{formName}")
    @ResponseBody
    public Technology TechnologyInfo(@PathVariable String formName) {
        Technology technology = technologyService.findTechnologyById(formName);
        return technology;
    }

    @ResponseBody
    @RequestMapping(value="/technology/delete_judge")
    public String deleteJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technology/edit_judge")
    public String editJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technology/add_judge")
    public String addJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/technology/update_note")
    public Map<String,String> updateTechnologyNote(
            @ModelAttribute Technology technology){

        technologyService.modifyTechnology(technology);
        Map<String,String> map=new HashMap<>();
        map.put("status","200");
        return map;
    }

    @RequestMapping(value = "/technology/get_data")
    @ResponseBody
    public List<Technology> getData() {
        Technology technology=new Technology();
        List<Technology> technologyList = technologyService.findTechnology(technology);
        return technologyList;
    }


}

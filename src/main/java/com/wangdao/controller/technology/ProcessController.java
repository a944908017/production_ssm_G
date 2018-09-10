package com.wangdao.controller.technology;

import com.github.pagehelper.PageHelper;
import com.wangdao.bean.FormData.FormDataPostman_2;
import com.wangdao.bean.technology.Process;
import com.wangdao.service.technology.ProcessService;
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

@Controller
public class ProcessController {

    @Autowired
    @Qualifier("processService")
    private ProcessService processService;

    @RequestMapping(value = "/process/list")
    @ResponseBody
    public String processList(Integer page,Integer rows) {
        Process process = new Process();

        FormDataPostman_2 postman = new FormDataPostman_2();


        List<Process> countList=processService.findProcess(process);
        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Process>processes = processService.findProcess(process);
        List<Process> processList=new ArrayList<>();
        for(Process p:processes){
            processList.add(p);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(processList);

        return postman.toString();
    }

    @RequestMapping(value = "/process/find")
    public String selectProcess(){
        return "process_list";
    }

    @RequestMapping(value = "/process/search_process_by_processId")
    @ResponseBody
    public String processSearchId(Integer page,Integer rows,String searchValue) {
        Process process = new Process();
        process.setProcess_Id(searchValue);

        FormDataPostman_2 postman = new FormDataPostman_2();


        List<Process> countList=processService.findProcess(process);
        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Process>processes = processService.findProcess(process);
        List<Process> processList=new ArrayList<>();
        for(Process p:processes){
            processList.add(p);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(processList);

        return postman.toString();
    }

    @RequestMapping(value = "/process/search_process_by_technologyPlanId")
    @ResponseBody
    public String processSearchTechnologyPlanId(Integer page,Integer rows,String searchValue) {
        Process process = new Process();
        process.setTechnology_Plan_Id(searchValue);

        FormDataPostman_2 postman = new FormDataPostman_2();


        List<Process> countList=processService.findProcess(process);
        PageHelper.startPage(page,rows);//第一个参数的意思为：当前页数，第二个参数的意思为：每页显示多少条记录
        List<Process>processes = processService.findProcess(process);
        List<Process> processList=new ArrayList<>();
        for(Process p:processes){
            processList.add(p);
        }
        postman.setTotal(String.valueOf(countList.size()));
        postman.setRows(processList);

        return postman.toString();
    }

    @ResponseBody
    @RequestMapping(value="/process/delete_batch")
    public Map<String,String> removeProcess(String ids){
        Map<String,String> map=new HashMap<>();
        try {


        String[] idArray = ids.split(",");
        for(String id : idArray){
            // 根据id删除部门
            processService.removeProcessById(id);
        }
            map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","删除失败");
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value="/process/insert")
    public Map<String,String> addProcess(
            @ModelAttribute Process process){
        Map<String,String> map=new HashMap<>();
        // 执行添加操作
        try {
            processService.addProcess(process);


        map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","添加失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value="/process/update_all")
    public Map<String,String> updateProcess(
            @ModelAttribute Process process){
        Map<String,String> map=new HashMap<>();
        try {
            processService.modifyProcess(process);
            map.put("status","200");
        }catch (Exception e){
            map.put("status","500");
            map.put("msg","修改失败");
        }
        // 执行修改操作



        return map;
    }

    @ResponseBody
    @RequestMapping(value="/process/delete_judge")
    public String deleteJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/process/edit_judge")
    public String editJudge(){
        return null;
    }

    @ResponseBody
    @RequestMapping(value="/process/add_judge")
    public String addJudge(){
        return null;
    }

    @RequestMapping(value="/process/add")
    public String addProcess(){
        return "process_add";
    }

    @RequestMapping(value="/process/edit")
    public String editProcess(){
        return "process_edit";
    }

    @RequestMapping(value = "/process/get/{formName}")
    @ResponseBody
    public Process processInfo(@PathVariable String formName) {
        Process process = processService.findProcessById(formName);
        return process;
    }

    @RequestMapping(value = "/process/get_data")
    @ResponseBody
        public List<Process> getData() {
        Process process=new Process();
        List<Process> processList = processService.findProcess(process);
        return processList;
    }
}

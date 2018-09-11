package com.wangdao.controller.staff;

import com.wangdao.bean.staff.Employee;
import com.wangdao.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    @Qualifier("staffService")
    StaffService staffService;

    //前往员工列表
    @ResponseBody
    @RequestMapping("/find")
    public ModelAndView employee_find(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("employee:add");
        sysPermissionList.add("employee:edit");
        sysPermissionList.add("employee:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("employee_list");
        return mv;
    }

    //给员工列表返回json数据，无条件查询，跟据员工编号/员工名称/部门名称
    @ResponseBody
    @RequestMapping(value = {"/list", "/search_employee_by_employeeId",
            "/search_employee_by_employeeName",
            "/search_employee_by_departmentName"})
    public HashMap<String, Object> employee_list(String page, String rows, String searchValue, HttpServletRequest request, String condition) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }
        List<Employee> employeeList = null;
        Integer total = null;
        String requestURI = request.getRequestURI();
        if (requestURI != null) {
            switch (requestURI) {
                case "/employee/list":
                    searchValue="";
                    condition = "";
                    break;
                case "/employee/search_employee_by_employeeId":
                    condition = "EId";
                    break;
                case "/employee/search_employee_by_employeeName":
                    condition = "EName";
                    break;
                case "/employee/search_employee_by_departmentName":
                    condition = "DName";
                    break;
            }
            employeeList = staffService.selectEmployeeByPage(limit, offset, searchValue, condition);
            total = staffService.selectEmployeeCount(searchValue, condition);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", employeeList);
        return map;

    }

    //获取所有员工的集合
    @ResponseBody
    @RequestMapping("/get_data")
    public List<Employee> employee_get_data() {
        return staffService.selectEmployeeList();
    }


    //根据员工ID获取该员工的信息
    @ResponseBody
    @RequestMapping("/get/{empId}")
    public Employee getById(@PathVariable String empId) {
        return staffService.selectEmployeeById(empId);
    }

    //修改前判断
    @ResponseBody
    @RequestMapping("/edit_judge")
    public HashMap<String, Object> employee_edit_judge() {
        return null;
    }

    //前往修改页面
    @ResponseBody
    @RequestMapping("/edit")
    public ModelAndView employee_edit(ModelAndView mv) {
        mv.setViewName("employee_edit");
        return mv;
    }

    //修改员工信息
    @ResponseBody
    @RequestMapping("/update_all")
    public HashMap<String, Object> employee_update_all(Employee employee) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.updateEmployee(employee);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

    //员工增加前判断
    @RequestMapping("/add_judge")
    @ResponseBody
    public HashMap<String, Object> employee_add_judge() {
        return null;
    }

    //跳转至增加员工的页面
    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView employee_add(ModelAndView mv) {
        mv.setViewName("employee_add");
        return mv;
    }

    //增加一个新的员工
    @ResponseBody
    @RequestMapping("/insert")
    public HashMap<String, Object> employee_insert(Employee employee) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.insertEmployee(employee);
        if (ret) {
            map.put("status", 200);
        } else {
            map.put("status", 500);
            map.put("msg", "增加失败");
        }
        return map;
    }

    //删除员工前判断
    @ResponseBody
    @RequestMapping("/delete_judge")
    public String employee_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    //[批量]删除员工
    @ResponseBody
    @RequestMapping("/delete_batch")
    public HashMap<String, Object> employee_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.deleteEmployeeByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

}

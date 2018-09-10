package com.wangdao.controller.staff;

import com.wangdao.bean.staff.Department;
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
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    @Qualifier("staffService")
    StaffService staffService;

    //前往部门列表
    @ResponseBody
    @RequestMapping("/find")
    public ModelAndView department_find(ModelAndView mv, HttpSession session) {
        List<String> sysPermissionList = new ArrayList<>();
        //增加增删改按钮
        sysPermissionList.add("department:add");
        sysPermissionList.add("department:edit");
        sysPermissionList.add("department:delete");
        session.setAttribute("sysPermissionList", sysPermissionList);
        mv.setViewName("department_list");
        return mv;
    }

    //给部门列表返回json数据，无条件查询，部门编号查询，部门名称查询
    @ResponseBody
    @RequestMapping(value = {"/list",
            "/search_department_by_departmentId",
            "/search_department_by_departmentName"})
    public HashMap<String, Object> department_list(String page, String rows, String searchValue, HttpServletRequest request) {
        Integer limit = null;
        Integer offset = null;
        if (!page.isEmpty() && !rows.isEmpty()) {
            limit = Integer.parseInt(rows);
            offset = (Integer.parseInt(page) - 1) * limit;
        }

        List<Department> departmentList = null;
        Integer total = null;
        String requestURI = request.getRequestURI();
        if (requestURI != null) {
            switch (requestURI) {
                case "/department/list":
                    departmentList = staffService.selectDepartmentByPage(limit, offset);
                    total = staffService.selectAllDepartment();
                    break;
                case "/department/search_department_by_departmentId":
                    departmentList = staffService.selectDepartmentByDid(limit, offset, searchValue);
                    total = staffService.selectDepartmentCountByDid(searchValue);
                    break;
                case "/department/search_department_by_departmentName":
                    departmentList = staffService.selectDepartmentByDName(limit, offset, searchValue);
                    total = staffService.selectDepartmentCountByDName(searchValue);
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", departmentList);
        return map;
    }

    //获取指定id的部门
    @ResponseBody
    @RequestMapping("/get/{id}")
    public Department department_get(@PathVariable String id) {
        return staffService.selectDepartmentById(id);
    }

    //获取所有的部门
    @ResponseBody
    @RequestMapping("/get_data")
    public List<Department> department_get_data() {
        return staffService.selectDepartmentList();
    }

    //部门增加前判断
    @RequestMapping("/add_judge")
    @ResponseBody
    public HashMap<String, Object> department_add_judge() {
        return null;
    }

    //跳转至增加部门的页面
    @RequestMapping("/add")
    @ResponseBody
    public ModelAndView department_add(ModelAndView mv) {
        mv.setViewName("department_add");
        return mv;
    }

    //增加一个新的部门
    @ResponseBody
    @RequestMapping("/insert")
    public HashMap<String, Object> pemployee_insert(Department department) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.insertDepartment(department);
        if (ret) {
            map.put("status", 200);
        } else {
            map.put("status", 500);
            map.put("msg", "增加失败");
        }
        return map;
    }

    //修改一个部门前判断
    @ResponseBody
    @RequestMapping("/edit_judge")
    public String department_edit_judge() {
        //推断与用户权限有关
        return null;
    }

    //前往修改页面
    @ResponseBody
    @RequestMapping("/edit")
    public ModelAndView department_edit(ModelAndView mv) {
        mv.setViewName("department_edit");
        return mv;
    }

    //修改部门，更新部门职责
    @ResponseBody
    @RequestMapping(value = {"/update_all","/update_note"})
    public HashMap<String, Object> department_update_all(Department department) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.updateDepartment(department);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

    //删除部门前判断
    @ResponseBody
    @RequestMapping("/delete_judge")
    public String department_delete_judge() {
        //推断与用户权限有关
        return null;
    }

    //[批量]删除部门
    @ResponseBody
    @RequestMapping("/delete_batch")
    public HashMap<String, Object> department_delete_batch(String ids) {
        HashMap<String, Object> map = new HashMap<>();
        boolean ret = staffService.deleteDepartmentByIds(ids);
        if (ret) {
            map.put("status", 200);
        }
        return map;
    }

}

package com.wangdao.service.staff.impl;

import com.wangdao.bean.staff.Department;
import com.wangdao.bean.staff.Employee;
import com.wangdao.bean.staff.EmployeeSearchByDNameExt;
import com.wangdao.dao.staff.DepartmentMapper;
import com.wangdao.dao.staff.EmployeeMapper;
import com.wangdao.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("staffService")
public class StaffServiceImpl implements StaffService {
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;

    //根据页码查询
    @Override
    public List<Department> selectDepartmentByPage(Integer limit, Integer offset) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("limit", limit);
        map.put("offset", offset);
        return departmentMapper.selectDepartmentByPage(map);
    }

    //查询部门总数
    @Override
    public Integer selectAllDepartment() {
        return departmentMapper.selectAllDepartment();
    }

    //查询所有部门
    @Override
    public ArrayList<Department> selectDepartmentList() {
        return departmentMapper.selectDepartmentList();
    }

    //根据部门编号查询，返回部门的集合
    @Override
    public List<Department> selectDepartmentByDid(Integer limit, Integer offset, String searchValue) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return departmentMapper.selectDepartmentByDid(map);
    }

    //根据部门编号查询，返回部门的总数
    @Override
    public Integer selectDepartmentCountByDid(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return departmentMapper.selectDepartmentCountByDid(searchValue);
    }

    //根据部门编号查询，返回部门的总数
    @Override
    public List<Department> selectDepartmentByDName(Integer limit, Integer offset, String searchValue) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("searchValue", searchValue);
        map.put("limit", limit);
        map.put("offset", offset);
        return departmentMapper.selectDepartmentByDName(map);
    }

    //根据部门名称查询，返回部门的总数
    @Override
    public Integer selectDepartmentCountByDName(String searchValue) {
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        return departmentMapper.selectDepartmentCountByDName(searchValue);
    }

    //增加一个部门
    @Override
    public boolean insertDepartment(Department department) {
        Department department_old = departmentMapper.selectByPrimaryKey(department.getDepartmentId());
        if (department_old != null) {
            return false;
        }
        int i = departmentMapper.insert(department);
        return i == 1;
    }

    //修改一个部门
    @Override
    public boolean updateDepartment(Department department) {
        int i = departmentMapper.updateByPrimaryKeySelective(department);
        return i == 1;
    }

    //[批量]删除部门
    @Transactional
    @Override
    public boolean deleteDepartmentByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = departmentMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    //获取指定id的部门
    @Override
    public Department selectDepartmentById(String id) {
        return departmentMapper.selectByPrimaryKey(id);
    }


    /******************员工管理*****************/

    //根据页码和条件查询员工
    @Override
    public List<Employee> selectEmployeeByPage(Integer limit, Integer offset, String searchValue, String condition) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("limit", limit);
        map.put("offset", offset);
        map.put("searchValue", searchValue);
        if ("DName".equals(condition)) {
            List<Department> departmentList = departmentMapper.selectDepartmentByDName(map);
            List<String> ids = new ArrayList<>();
            for (Department department : departmentList) {
                ids.add(department.getDepartmentId());
            }
            EmployeeSearchByDNameExt helpBean = new EmployeeSearchByDNameExt();
            helpBean.setLimit(limit);
            helpBean.setOffset(offset);
            helpBean.setIds(ids);
            return employeeMapper.selectEmployeeByDIds(helpBean);
        }
        map.put("condition", condition);
        return employeeMapper.selectEmployeeByConditionAndPage(map);

    }

    //查询符合条件的员工总数
    @Override
    public Integer selectEmployeeCount(String searchValue, String condition) {
        HashMap<String, Object> map = new HashMap<>();
        if (searchValue != null) {
            searchValue = "%" + searchValue + "%";
        }
        map.put("condition", condition);
        map.put("searchValue", searchValue);
        if ("DName".equals(condition)) {
            List<Department> departmentList = departmentMapper.selectDepartmentByDName(map);
            List<String> ids = new ArrayList<>();
            for (Department department : departmentList) {
                ids.add(department.getDepartmentId());
            }
            return employeeMapper.selectEmployeeCountByDIds(ids);
        }
        return employeeMapper.selectEmployeeCount(map);
    }

    //查询所有员工
    @Override
    public List<Employee> selectEmployeeList() {
        return employeeMapper.selectEmployeeList();
    }

    //根据Id查询员工
    @Override
    public Employee selectEmployeeById(String empId) {
        return employeeMapper.selectByPrimaryKey(empId);
    }

    //修改员工信息
    @Override
    public boolean updateEmployee(Employee employee) {
        int i = employeeMapper.updateByPrimaryKeySelective(employee);
        return i == 1;
    }

    //增加一个员工
    @Override
    public boolean insertEmployee(Employee employee) {
        Employee employee_old = employeeMapper.selectByPrimaryKey(employee.getEmpId());
        if (employee_old != null) {
            return false;
        }
        int i = employeeMapper.insertSelective(employee);
        return i == 1;
    }

    //[批量]删除员工
    @Transactional
    @Override
    public boolean deleteEmployeeByIds(String ids) {
        boolean flag = false;
        String[] split = ids.split(",");
        for (String s : split) {
            int i = employeeMapper.deleteByPrimaryKey(s);
            flag = i == 1 ? true : false;
            if (flag == false) {
                throw new RuntimeException("删除失败");
            }
        }
        return flag;
    }

    //通过员工ID模糊查询


    //通过员工ID查询总数


    //通过员工姓名模糊查询


    //通过员工姓名查询总数


    //通过部门名称模糊查询


    //通过部门名称查询总数


}

package com.wangdao.service.staff;

import com.wangdao.bean.staff.Department;
import com.wangdao.bean.staff.Employee;

import java.util.ArrayList;
import java.util.List;

public interface StaffService {
    List<Department> selectDepartmentByPage(Integer limit, Integer offset);

    Integer selectAllDepartment();

    List<Employee> selectEmployeeByPage(Integer limit, Integer offset, String searchValue, String condition);

    ArrayList<Department> selectDepartmentList();

    List<Employee> selectEmployeeList();

    Employee selectEmployeeById(String empId);

    boolean updateEmployee(Employee employee);

    List<Department> selectDepartmentByDid(Integer limit, Integer offset, String searchValue);

    Integer selectDepartmentCountByDid(String searchValue);

    List<Department> selectDepartmentByDName(Integer limit, Integer offset, String searchValue);

    Integer selectDepartmentCountByDName(String searchValue);

    boolean insertDepartment(Department department);

    boolean updateDepartment(Department department);

    boolean deleteDepartmentByIds(String ids);

    Department selectDepartmentById(String id);

    boolean insertEmployee(Employee employee);

    boolean deleteEmployeeByIds(String ids);

    Integer selectEmployeeCount(String searchValue, String condition);
}

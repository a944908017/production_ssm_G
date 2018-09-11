package com.wangdao.dao.staff;

import com.wangdao.bean.staff.Employee;
import com.wangdao.bean.staff.EmployeeSearchByDNameExt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(String empId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(String empId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> selectEmployeeList();

    Integer selectEmployeeCount(HashMap<String, Object> map);

    List<Employee> selectEmployeeByConditionAndPage(HashMap<String, Object> map);

    List<Employee> selectEmployeeByDIds(EmployeeSearchByDNameExt employeeSearchByDNameExt);

    Integer selectEmployeeCountByDIds(List<String> ids);
}
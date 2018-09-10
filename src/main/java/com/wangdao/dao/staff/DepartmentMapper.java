package com.wangdao.dao.staff;

import com.wangdao.bean.staff.Department;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(String departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(String departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> selectDepartmentByPage(HashMap<String, Integer> map);

    Integer selectAllDepartment();

    ArrayList<Department> selectDepartmentList();

    List<Department> selectDepartmentByDid(HashMap<String, Object> map);

    List<Department> selectDepartmentByDName(HashMap<String, Object> map);

    Integer selectDepartmentCountByDName(String searchValue);

    Integer selectDepartmentCountByDid(String searchValue);
}
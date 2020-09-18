package com.panshi.mybatis.mapper;

import com.panshi.mybatis.pojo.Employee;

/***
 * @Auther: guo
 * @Date: 13:05 2020/9/17
 */
public interface EmployeeMapper {
    Employee getEmpById(Integer id);

    void insertEmp(Employee employee);
}

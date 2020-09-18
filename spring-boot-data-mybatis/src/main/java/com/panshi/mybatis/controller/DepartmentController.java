package com.panshi.mybatis.controller;

import com.panshi.mybatis.mapper.DepartmentMapper;
import com.panshi.mybatis.mapper.EmployeeMapper;
import com.panshi.mybatis.pojo.Department;
import com.panshi.mybatis.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 * @Auther: guo
 * @Date: 12:37 2020/9/17
 */
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @RequestMapping("/dept/{id}")
    public Department query(@PathVariable("id")Integer id) {
        return departmentMapper.getDeptById(id);
    }

    @RequestMapping("/dept")
    public void insert( Department department) {
        departmentMapper.insertDept(department);
    }

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id")Integer id) {
        return employeeMapper.getEmpById(id);
    }
}

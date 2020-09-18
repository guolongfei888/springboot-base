package com.panshi.mybatis.pojo;

/***
 * @Auther: guo
 * @Date: 12:34 2020/9/17
 */
public class Department {
    private Integer id;
    private String departmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}

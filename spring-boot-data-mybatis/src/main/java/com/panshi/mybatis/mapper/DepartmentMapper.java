package com.panshi.mybatis.mapper;

import com.panshi.mybatis.pojo.Department;
import org.apache.ibatis.annotations.*;

/***
 * @Auther: guo
 * @Date: 12:36 2020/9/17
 */
//@Mapper
public interface DepartmentMapper {
    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}

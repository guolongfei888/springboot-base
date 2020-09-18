package com.panshi.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/***
 * @Auther: guo
 * @Date: 11:22 2020/9/17
 */
@RestController
public class HelloController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/query")
    public Map<String,Object> map() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from department");
        return list.get(0);
    }
}

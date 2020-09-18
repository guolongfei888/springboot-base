package com.panshi.springboot.controller;

import com.panshi.springboot.bean.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class HelloController {
    @Resource
    private Person person;

    @RequestMapping("/hello")
    public String hello() {
//        return "hello World!!!";
        return person.toString();
    }
}

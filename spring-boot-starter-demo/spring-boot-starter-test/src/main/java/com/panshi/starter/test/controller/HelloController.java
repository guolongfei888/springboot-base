package com.panshi.starter.test.controller;

import com.panshi.starter.configurer.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Auther: guo
 * @Date: 22:09 2020/9/15
 */
@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello() {
        return  helloService.sayHellSuperbeyone("aaa");
    }
}

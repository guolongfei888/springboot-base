package com.panshi.mybatis.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/***
 * @Auther: guo
 * @Date: 19:47 2020/9/15
 */
public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("HelloApplicationContextInitializer...initialize  "+configurableApplicationContext);
    }
}

package com.panshi.springboot;

import com.panshi.springboot.bean.Person;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * SpringBoot单元测试:
 *
 * 可以在测试期间分方便的类似编码一样进行自动注入容器等操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SpringBootConfigApplicationTests {
	@Autowired
	Person person;

	@Autowired
	ApplicationContext ioc;

	@Test
	void contextLoads() {
		System.out.println(person);
	}

	@Test
	void helloService() {
		boolean b = ioc.containsBean("helloService2");
		System.out.println(b);
	}
}

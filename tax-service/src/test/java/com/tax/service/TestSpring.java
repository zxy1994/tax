package com.tax.service;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {

	/**
	 * 通过@Configuration配置类中开始包扫描
	 */
	@Test
	public void testAnnotation() {
		AnnotationConfigApplicationContext applicationContext = 
				new AnnotationConfigApplicationContext(ServiceConfig.class);
		TestService testService = (TestService) applicationContext.getBean("testService");
		testService.sayHello();
	}
	
	/**
	 * 通过xml文件开启包扫描的方式
	 */
	@Test
	public void testXml() {
		ClassPathXmlApplicationContext applicationContext = 
				new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		TestService testService = (TestService) applicationContext.getBean("testService");
		testService.sayHello();
	}
	
}

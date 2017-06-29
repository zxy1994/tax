package com.test.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.test.pojo.Student;
import com.test.service.TestHibernate;

public class TestSpring {
	private ClassPathXmlApplicationContext context;
	
	@Before
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}

	@Test
	public void test() {
		TestHibernate testHibernate = (TestHibernate) context.getBean("testHibernate");
		Student s = new Student();
		s.setName("小沈阳");
		testHibernate.saveStudent(s);
	}
	
	public static void main(String[] args) {
		Logger logger = LogManager.getLogger(TestSpring.class);
		logger.debug("这是debug");
		logger.info("这是Info");
		logger.error("这是error");
		int i = 1/0;
		
	}

}

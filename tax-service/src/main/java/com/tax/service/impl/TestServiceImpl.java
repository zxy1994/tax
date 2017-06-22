package com.tax.service.impl;

import org.springframework.stereotype.Service;

import com.tax.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	static {
		System.out.println("加载了TestServiceImpl的class文件");
	}
	public TestServiceImpl() {
		System.out.println("实例化了TestServiceImpl");
	}
	@Override
	public void sayHello() {
		System.out.println("spring生效了");
	}

}

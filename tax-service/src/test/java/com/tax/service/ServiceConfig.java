package com.tax.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ServiceConfig {
	static {
		System.out.println("加载了ServiceConfig的Class文件");
	}
}

package com.test.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.test.service.TestService;

public class TestAction extends ActionSupport  {
	@Autowired
	private TestService testService;
	
	@Override
	public String execute() throws Exception {
		ActionContext.getContext().put("responseData", "您好");
		testService.sayHello();
		return SUCCESS;
	}
}

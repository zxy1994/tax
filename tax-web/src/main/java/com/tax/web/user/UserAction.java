package com.tax.web.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.service.UserService;

/**
 * UserAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月11日 上午10:06:05
 * @version  v1.0
 */

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 4526496105243102063L;
	@Autowired
	private UserService userService;
	
	public String listUi(){
		return "list";
	}

}

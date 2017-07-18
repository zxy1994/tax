package com.tax.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.pojo.nsfw.User;
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
	private List<User> userList;
	private User user;
	
	public String listUI() {
		this.setUserList(userService.findAll());
		return "listUI";
	}

	public String addUI() {
		return "addUI";
	}

	public String add() {
		userService.save(user);
		return "list";
	}

	public String editUI() {
		this.setUser(userService.findById(user.getId()));
		return "editUI";
	}
	
	public String edit() {
		userService.update(user);
		return "list";
	}

	
	/** setter and getter method */
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}

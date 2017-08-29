package com.tax.web.login;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.core.constant.Constant;
import com.tax.pojo.nsfw.User;
import com.tax.pojo.nsfw.UserRole;
import com.tax.service.nsfw.UserService;

/**
 * LoginAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月29日 上午11:59:15
 * @version  v1.0
 */

public class LoginAction extends ActionSupport implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2822406977802949377L;
	@Autowired
	private UserService userService;
	private User user;
	private String loginResult;
	
	
	/** 跳转到登陆页 */
	public String toLoginUI() {
		return "loginUI";
	}
	
	/** 跳转到没有权限页面 */
	public String toNoPermissionUI() {
		return "noPermissionUI";
	}
	
	/** 登陆方法 */
	public String login() {
		User loginUser = userService.findUserByAccountAndPassword(user.getAccount(),user.getPassword());
		if(null != loginUser) {
			List<UserRole> userRoleList = userService.findAllUserRoleByUserId(loginUser.getId());
			user.setUserRoleList(userRoleList);
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute(Constant.USER, loginUser);
			return "home";
		}
		loginResult = "登陆失败，用户名或密码错误！";
		return "loginUI";
	}

	
	/** setter and getter method */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(String loginResult) {
		this.loginResult = loginResult;
	}
	
	

}

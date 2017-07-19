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
	private String[] selectedRow;
	
	/** 跳转到列表页面 */
	public String listUI() {
		this.setUserList(userService.findAll());
		return "listUI";
	}

	/** 跳转到添加页面 */
	public String addUI() {
		return "addUI";
	}

	/** 添加 */
	public String add() {
		if(user != null) {
			userService.save(user);
		}
		return "list";
	}
	
	/** 跳转到编辑页面 */
	public String editUI() {
		if(null != user && null != user.getId()) {
			this.setUser(userService.findById(user.getId()));
		}
		return "editUI";
	}
	
	/** 编辑 */
	public String edit() {
		if(null != user && null != user.getId()) {
			userService.update(user);
		}
		return "list";
	}
	
	/** 删除 */
	public String delete() {
		if(null != user && null != user.getId()){
			userService.deleteById(user.getId());
		}
		return "list";
	}
	
	/** 批量删除 */
	public String batchDelete() {
		if(null != selectedRow ){
			for (String id : selectedRow) {
				userService.deleteById(id);
			}
		}
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

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	

}

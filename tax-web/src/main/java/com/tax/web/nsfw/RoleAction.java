package com.tax.web.nsfw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tax.core.action.BaseAction;
import com.tax.core.constant.Constant;
import com.tax.pojo.nsfw.Role;
import com.tax.service.nsfw.RoleService;

/**
 * RoleAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月11日 上午10:06:05
 * @version  v1.0
 */

public class RoleAction extends BaseAction {

	private static final long serialVersionUID = 4526496105243102063L;
	@Autowired
	private RoleService roleService;
	private List<Role> roleList;
	private Role role;
	/** 权限数组 */
	private String[] privileges;
	
	
	/** 跳转到列表页面 */
	public String listUI() {
		ActionContext.getContext().put("privilegeMap", Constant.PRIVILEGE_MAP);
		this.setRoleList(roleService.findAll());
		return "listUI";
	}

	/** 跳转到添加页面 */
	public String addUI() {
		ActionContext.getContext().put("privilegeMap", Constant.PRIVILEGE_MAP);
		return "addUI";
	}

	/** 添加  */
	public String add() {
		if (role != null) {
			roleService.saveRoleAndRolePrivilege(role,privileges);
		}
		return "list";
	}
	
	/** 跳转到编辑页面 */
	public String editUI() {
		if(null != role && null != role.getRoleId()) {
			this.setRole(roleService.findById(role.getRoleId()));
		}
		return "editUI";
	}
	
	/** 编辑 */
	public String edit() {
		if (null != role && null != role.getRoleId()) {
			roleService.update(role);
		}
		return "list";
	}
	
	/** 删除  */
	public String delete() {
		if(null != role && null != role.getRoleId()) {
			roleService.deleteById(role.getRoleId());
		}
		return "list";
	}
	
	

	/** 批量删除 */
	public String batchDelete() {
		if(null != selectedRow ){
			for (String id : selectedRow) {
				roleService.deleteById(id);
			}
		}
		return "list";
	}

	
	/** setter and getter method */
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String[] privileges) {
		this.privileges = privileges;
	}
	

}

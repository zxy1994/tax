package com.tax.pojo.nsfw;

import java.io.Serializable;
import java.util.Set;

/**
 * Role
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月9日 下午9:37:21
 * @version v1.0
 */
public class Role implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -8155159856467403973L;
	private String roleId;
	private String name;
	private String state;
	private Set<RolePrivilege> rolePrivileges;
	
	//角色状态
	public static String ROLE_STATE_VALID = "1";//有效
	public static String ROLE_STATE_INVALID = "0";//无效
	
	public Role() {
	}

	public Role(String roleId) {
		this.roleId = roleId;
	}

	public Role(String roleId, String name, String state, Set<RolePrivilege> rolePrivileges) {
		this.roleId = roleId;
		this.name = name;
		this.state = state;
		this.rolePrivileges = rolePrivileges;
	}
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Set<RolePrivilege> getRolePrivileges() {
		return rolePrivileges;
	}
	public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}
	
}

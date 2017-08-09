package com.tax.pojo.nsfw;

import java.io.Serializable;

/**
 * 角色权限中间表
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月9日 下午9:37:27
 * @version v1.0
 */
public class RolePrivilege implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8938709096485491318L;
	private RolePrivilegeId id;

	public RolePrivilege() {
	}

	public RolePrivilege(RolePrivilegeId id) {
		this.id = id;
	}

	public RolePrivilegeId getId() {
		return id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}
	
}

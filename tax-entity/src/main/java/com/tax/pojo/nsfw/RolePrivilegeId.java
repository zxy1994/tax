package com.tax.pojo.nsfw;

import java.io.Serializable;

/**
 * RolePrivilegeId
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月9日 下午9:37:43
 * @version v1.0
 */
public class RolePrivilegeId implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 9177754655426495454L;
	private String roleId;
	private String code;
	
	
	
	public RolePrivilegeId() {
	}

	public RolePrivilegeId(String roleId, String code) {
		this.roleId = roleId;
		this.code = code;
	}

	/** setter and getter method */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePrivilegeId other = (RolePrivilegeId) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}
	
	
}

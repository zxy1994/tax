package com.tax.pojo.nsfw;

import java.io.Serializable;

/**
 * 用户角色实体
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月22日 下午5:26:40
 * @version  v1.0
 */

public class UserRole implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 主键用联合主键，这里抽成一个主键类 */
	private UserRoleId id;
	
	/** setter and getter method */
	public UserRoleId getId() {
		return id;
	}
	
	public void setId(UserRoleId id) {
		this.id = id;
	}
	
	

}

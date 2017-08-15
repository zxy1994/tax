package com.tax.dao.nsfw.impl;

import org.springframework.stereotype.Repository;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.tax.dao.nsfw.RoleDao;
import com.tax.pojo.nsfw.Role;

/**
 * RoleDaoImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午9:31:28
 * @version v1.0
 */

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
	
	/**
	 * 保存角色，返回角色Id
	 * @param role 角色实体
	 * @return 角色id
	 */
	@Override
	public String saveRole(Role role) {
		return (String) this.getCurrentSession().save(role);
	}

	

}

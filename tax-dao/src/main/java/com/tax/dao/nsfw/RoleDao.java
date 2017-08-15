package com.tax.dao.nsfw;

import com.tax.core.dao.BaseDao;
import com.tax.pojo.nsfw.Role;

/**
 * RoleDao
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午9:26:28
 * @version v1.0
 */
public interface RoleDao extends BaseDao<Role> {

	/**
	 * 保存角色，返回角色Id
	 * @param role 角色实体
	 * @return 角色id
	 */
	String saveRole(Role role);

}

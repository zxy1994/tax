package com.tax.dao.nsfw;

import com.tax.core.dao.BaseDao;
import com.tax.pojo.nsfw.RolePrivilege;

/**
 * RolePrivilegeDao
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午9:26:28
 * @version v1.0
 */
public interface RolePrivilegeDao extends BaseDao<RolePrivilege> {

	/**
	 * 通过角色id删除相关的角色权限
	 * @param roleId 角色id
	 */
	void deleteByRoleId(String roleId);


}

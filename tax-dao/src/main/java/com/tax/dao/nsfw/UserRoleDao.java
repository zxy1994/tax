package com.tax.dao.nsfw;

import java.util.List;

import com.tax.core.dao.BaseDao;
import com.tax.pojo.nsfw.UserRole;

/**
 * UserRoleDao
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月23日 下午3:50:39
 * @version  v1.0
 */

public interface UserRoleDao extends BaseDao<UserRole> {
	
	/**
	 * 通过用户id查询出对应的用户角色集合
	 * @param id    用户id
	 * @return		集合
	 */
	List<UserRole> findAllUserRoleByUserId(String id);

	/**
	 * 通过用户id删除用户角色
	 * @param id
	 */
	void deleteByUserId(String id);

}

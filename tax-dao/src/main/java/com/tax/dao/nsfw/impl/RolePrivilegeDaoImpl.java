package com.tax.dao.nsfw.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.tax.dao.nsfw.RolePrivilegeDao;
import com.tax.pojo.nsfw.RolePrivilege;

/**
 * RolePrivilegeDaoImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午10:55:28
 * @version v1.0
 */

@Repository("rolePrivilegeDao")
public class RolePrivilegeDaoImpl extends BaseDaoImpl<RolePrivilege> implements RolePrivilegeDao {
	
	/**
	 * 通过角色id删除相关的角色权限
	 * @param roleId 角色id
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteByRoleId(String roleId) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(RolePrivilege.class);
		List<RolePrivilege> list = criteria.add(Restrictions.eq("id.roleId", roleId)).list();
		for (RolePrivilege rolePrivilege : list) {
			session.delete(rolePrivilege);
		}
	}


}

package com.tax.dao.nsfw.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.tax.dao.nsfw.UserRoleDao;
import com.tax.pojo.nsfw.UserRole;

/**
 * UserRoleDaoImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月23日 下午3:56:38
 * @version  v1.0
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {
	
	/**
	 * 通过用户id查询出对应的用户角色集合
	 * @param id    用户id
	 * @return		集合
	 */
	@Override
	public List<UserRole> findAllUserRoleByUserId(String id) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(UserRole.class);
		List<UserRole> list = criteria.add(Restrictions.eq("id.userId", id)).list();
		return list;
	}
	
	/**
	 * 通过用户id删除用户角色
	 * @param id
	 */
	@Override
	public void deleteByUserId(String id) {
		Session session = this.getCurrentSession();
		String hql = "DELETE UserRole where id.userId = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		query.executeUpdate();
	}


}

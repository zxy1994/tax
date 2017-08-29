package com.tax.dao.nsfw.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.tax.dao.nsfw.UserDao;
import com.tax.pojo.nsfw.User;

/**
 * UserDaoImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月1日 下午3:14:21
 * @version  v1.0
 */

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public List<User> findUserByAccountAndId(String account, String id) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("account", account));
		if(StringUtils.isNotBlank(id)){
			criteria.add(Restrictions.ne("id", id));
		}
		return criteria.list();
	}

	@Override
	public String saveUser(User user) {
		return (String) this.getHibernateTemplate().save(user);
	}
	
	/**
	 * 通过账号和密码查找用户
	 * @param account
	 * @param password
	 * @return user实体
	 */
	@Override
	public User findUserByAccountAndPassword(String account, String password) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("password",password));
		criteria.add(Restrictions.eq("state",User.USER_STATE_VALID));
		return (User) criteria.uniqueResult();
	}
	

}

package com.tax.dao.nsfw.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
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
		List<User> list = criteria.list();
		return list;
	}
	

}

package com.tax.dao.nsfw.impl;

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
	

}

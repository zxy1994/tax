package com.tax.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.dao.nsfw.UserDao;
import com.tax.pojo.nsfw.User;
import com.tax.service.UserService;

/**
 * UserServiceImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月15日 下午5:01:21
 * @version  v1.0
 */

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(User user) {
		userDao.update(user);
	}

	@Override
	public void deleteById(Serializable id) {
		User user = userDao.findById(id);
		userDao.deleteById(id);
	}

	@Override
	public User findById(Serializable id) {
		return userDao.findById(id);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	
}

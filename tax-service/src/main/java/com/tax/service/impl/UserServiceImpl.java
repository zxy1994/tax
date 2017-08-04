package com.tax.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.core.util.ExcelUtils;
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

	@Override
	public void importExcel(File userExcel, String userExcelFileName) {
		try {
			List<ArrayList<String>> data = ExcelUtils.parseExcel(userExcel, userExcelFileName);
			if(null != data && data.size() > 3) {
				for (int i = 2; i < data.size(); i++) {
					User user = new User();
					user.setName(data.get(i).get(0));		//用户名
					user.setAccount(data.get(i).get(1));	//账号
					user.setDept(data.get(i).get(2));	    //部门
					user.setGender("男".equals(data.get(i).get(3))); //性别
					user.setMobile(data.get(i).get(4));
					user.setEmail(data.get(i).get(5));
					user.setBirthday(new Date(data.get(i).get(6)));
					user.setPassword("123456");
					user.setState(User.USER_STATE_VALID);
					this.save(user);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("通过Excel导入用户数据方法出现了异常");
		}
	}

	
}

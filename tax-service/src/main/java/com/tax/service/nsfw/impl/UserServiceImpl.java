package com.tax.service.nsfw.impl;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.core.util.ExcelUtils;
import com.tax.dao.nsfw.UserDao;
import com.tax.dao.nsfw.UserRoleDao;
import com.tax.pojo.nsfw.User;
import com.tax.pojo.nsfw.UserRole;
import com.tax.pojo.nsfw.UserRoleId;
import com.tax.service.nsfw.UserService;

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
	@Autowired
	private UserRoleDao userRoleDao;

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
					if(StringUtils.isNotBlank(data.get(i).get(6))) {
						user.setBirthday(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get(i).get(6)));
					}
					user.setPassword("123456");
					user.setState(User.USER_STATE_VALID);
					this.save(user);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("通过Excel导入用户数据方法出现了异常",e);
		}
	}

	@Override
	public boolean verifyAccount(String account, String id) {
		// 调用dao层查询数据库
		List<User> list = userDao.findUserByAccountAndId(account,id);
		return null != list && list.size() > 0;
	}
	
	/**
	 * 保存用户和用户角色
	 * @param user
	 * @param roleIds
	 */
	@Override
	public void saveUserAndUserRole(User user, String[] roleIds) {
		// 1.保存用户
		String userId = userDao.saveUser(user);
		// 2.保存用户角色
		if(null != roleIds){
			for (String roleId : roleIds) {
				userRoleDao.save(new UserRole(new UserRoleId(userId, roleId)));
			}
		}
	}
	
	/**
	 * 通过用户id查询出对应的用户角色集合
	 * @param id    用户id
	 * @return		集合
	 */
	@Override
	public List<UserRole> findAllUserRoleByUserId(String id) {
		return userRoleDao.findAllUserRoleByUserId(id);
	}
	
	/**
	 * 更新用户和用户角色
	 * @param user
	 * @param roleIds
	 */
	@Override
	public void updateUserAndUserRole(User user, String[] roleIds) {
		// 1.删除老的用户角色
		userRoleDao.deleteByUserId(user.getId());
		// 2.更新用户
		userDao.update(user);
		// 3.保存用户角色
		if(null != roleIds){
			for (String roleId : roleIds) {
				userRoleDao.save(new UserRole(new UserRoleId(user.getId(), roleId)));
			}	
		}
		
		
	}
	
	/**
	 * 通过用户id删除用户和用户角色
	 * @param id 用户id
	 */
	@Override
	public void deleteUserAndUserRole(String id) {
		// 1.删除老的用户角色
		userRoleDao.deleteByUserId(id);
		// 2.删除用户
		userDao.deleteById(id);
	}

	
}

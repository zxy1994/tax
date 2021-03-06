package com.tax.dao.nsfw;

import java.util.List;

import com.tax.core.dao.BaseDao;
import com.tax.pojo.nsfw.User;

/**
 * UserDao
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月1日 下午3:13:33
 * @version  v1.0
 */
public interface UserDao extends BaseDao<User> {

	/**
	 * 通过账号和id查询用户
	 * @param account
	 * @param id
	 * @return 用户集合
	 */
	List<User> findUserByAccountAndId(String account, String id);

	/**
	 * 保存用户,返回用户id
	 * @param user
	 * @return 用户id
	 */
	String saveUser(User user);
	
	/**
	 * 通过账号和密码查找用户
	 * @param account
	 * @param password
	 * @return user实体
	 */
	User findUserByAccountAndPassword(String account, String password);

}

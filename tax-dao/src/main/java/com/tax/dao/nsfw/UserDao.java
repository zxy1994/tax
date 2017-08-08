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

}

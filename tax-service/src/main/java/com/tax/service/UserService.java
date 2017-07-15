package com.tax.service;

import java.io.Serializable;
import java.util.List;

import com.tax.pojo.nsfw.User;

/**
 * UserService
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月15日 下午5:01:26
 * @version  v1.0
 */

public interface UserService {
	
	/**
	 * 新增
	 * @param user
	 */
	public void save(User user);

	/**
	 * 更新
	 * @param user
	 */
	public void update(User user);

	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteById(Serializable id);

	
	/**
	 * 通过id查找
	 * @param id
	 * @return 实体
	 */
	public User findById(Serializable id);

	
	/**
	 * 查找所有
	 * @return List集合
	 */
	public List<User> findAll();
}

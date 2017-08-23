package com.tax.service.nsfw;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.tax.pojo.nsfw.User;
import com.tax.pojo.nsfw.UserRole;

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

	/**
	 * 使用Excel导入用户
	 * @param userExcel
	 * @param userExcelFileName
	 */
	public void importExcel(File userExcel, String userExcelFileName);

	/**
	 * 校验账号是否存在
	 * @param account 账号
	 * @param id	用户id，可以为null
	 * @return
	 */
	public boolean verifyAccount(String account, String id);

	/**
	 * 保存用户和用户角色
	 * @param user
	 * @param roleIds
	 */
	public void saveUserAndUserRole(User user, String[] roleIds);

	/**
	 * 通过用户id查询出对应的用户角色集合
	 * @param id    用户id
	 * @return		集合
	 */
	public List<UserRole> findAllUserRoleByUserId(String id);

	/**
	 * 更新用户和用户角色
	 * @param user
	 * @param roleIds
	 */
	public void updateUserAndUserRole(User user, String[] roleIds);

	/**
	 * 通过用户id删除用户和用户角色
	 * @param id 用户id
	 */
	public void deleteUserAndUserRole(String id);
}

package com.tax.service.nsfw;

import java.io.Serializable;
import java.util.List;

import com.tax.pojo.nsfw.Role;

/**
 * RoleService
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午9:32:42
 * @version v1.0
 */
public interface RoleService {
	
	/**
	 * 新增
	 * @param role
	 */
	public void save(Role role);

	/**
	 * 更新
	 * @param role
	 */
	public void update(Role role);

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
	public Role findById(Serializable id);

	
	/**
	 * 查找所有
	 * @return List集合
	 */
	public List<Role> findAll();

	/**
	 * 保存角色和角色权限
	 * @param role	角色对象
	 * @param privileges 权限code数组
	 */
	public void saveRoleAndRolePrivilege(Role role, String[] privileges);

	/**
	 * 更新角色和角色权限
	 * @param role 角色
	 * @param privileges 角色权限code数组
	 */
	public void updateRoleAndRolePrivilege(Role role, String[] privileges);

	/**
	 * 删除角色和角色权限
	 * @param roleId 角色id
	 */
	public void deleteRoleAndRolePrivilege(String roleId);
	

	
}

package com.tax.service.nsfw.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.core.service.impl.BaseServiceImpl;
import com.tax.core.util.PageResult;
import com.tax.core.util.QueryHelper;
import com.tax.dao.nsfw.RoleDao;
import com.tax.dao.nsfw.RolePrivilegeDao;
import com.tax.pojo.nsfw.Role;
import com.tax.pojo.nsfw.RolePrivilege;
import com.tax.pojo.nsfw.RolePrivilegeId;
import com.tax.service.nsfw.RoleService;

/**
 * RoleServiceImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月15日 下午9:34:03
 * @version v1.0
 */

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	private RoleDao roleDao;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	
	@Autowired
	public void setRoleDao(RoleDao roleDao) {
		super.setBaseDao(roleDao);
		this.roleDao = roleDao;
	}


	@Override
	public Role findById(Serializable id) {
		Role role = roleDao.findById(id);
		// service层的session未关闭，在此处把延迟加载的属性给加载出来
		for (RolePrivilege rolePrivilege : role.getRolePrivileges()) {
			rolePrivilege.getId();
		}
		return role;
	}

	@Override
	public List<Role> findAll() {
		List<Role> list = roleDao.findAll();
		// service层session还未关闭，把延迟加载的属性获取一遍，就ok了
		for (Role role : list) {
			Set<RolePrivilege> set = role.getRolePrivileges();
			for (RolePrivilege rolePrivilege : set) {
				rolePrivilege.getId();
			}
		}
		return list;
	}
	
	@Override
	public PageResult<Role> findByPage(QueryHelper qh, int pageNo, int pageSize) {
		PageResult<Role> pageResult = roleDao.findByPage(qh, pageNo, pageSize);
		if(pageResult.getItems().size() > 0){
			List<Role> list = pageResult.getItems();
			// service层session还未关闭，把延迟加载的属性获取一遍，就ok了
			for (Role role : list) {
				Set<RolePrivilege> set = role.getRolePrivileges();
				for (RolePrivilege rolePrivilege : set) {
					rolePrivilege.getId();
				}
			}
		}
		return pageResult;
	}
	
	/**
	 * 保存角色和角色权限
	 * @param role	角色对象
	 * @param privileges 权限code数组
	 */
	@Override
	public void saveRoleAndRolePrivilege(Role role, String[] privileges) {
		String roleId = roleDao.saveRole(role);
		if(null != privileges){
			for (String code : privileges) {
				rolePrivilegeDao.save(new RolePrivilege(new RolePrivilegeId(roleId, code)));
			}
		}
		
	}
	
	/**
	 * 更新角色和角色权限
	 * @param role 角色
	 * @param privileges 角色权限code数组
	 */
	@Override
	public void updateRoleAndRolePrivilege(Role role, String[] privileges) {
		// 1.把老的角色权限删除
		rolePrivilegeDao.deleteByRoleId(role.getRoleId());
		// 2.更新角色
		roleDao.update(role);
		// 3.保存角色权限
		if (null != privileges) {
			for (String code : privileges) {
				rolePrivilegeDao.save(new RolePrivilege(new RolePrivilegeId(role.getRoleId(), code)));
			} 
		}
	}
	
	/**
	 * 删除角色和角色权限
	 * @param roleId 角色id
	 */
	@Override
	public void deleteRoleAndRolePrivilege(String roleId) {
		// 1.把老的角色权限删除
		rolePrivilegeDao.deleteByRoleId(roleId);
		// 2.删除角色
		roleDao.deleteById(roleId);
	}

	
}

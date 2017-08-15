package com.tax.service.nsfw.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	@Override
	public void save(Role role) {
		roleDao.save(role);
	}

	@Override
	public void update(Role role) {
		roleDao.update(role);
	}

	@Override
	public void deleteById(Serializable id) {
		roleDao.deleteById(id);
	}

	@Override
	public Role findById(Serializable id) {
		return roleDao.findById(id);
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
		return null;
	}
	
	/**
	 * 保存角色和角色权限
	 * @param role	角色对象
	 * @param privileges 权限code数组
	 */
	@Override
	public void saveRoleAndRolePrivilege(Role role, String[] privileges) {
		String roleId = roleDao.saveRole(role);
		for (String code : privileges) {
			rolePrivilegeDao.save(new RolePrivilege(new RolePrivilegeId(roleId, code)));
		}
	}

	
}

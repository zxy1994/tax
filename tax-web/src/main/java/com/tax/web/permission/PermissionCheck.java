package com.tax.web.permission;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.tax.pojo.nsfw.RolePrivilege;
import com.tax.pojo.nsfw.User;
import com.tax.pojo.nsfw.UserRole;
import com.tax.service.nsfw.RoleService;
import com.tax.service.nsfw.UserService;

/**
 * 权限鉴定类(该类交给Spring容器管理，方便注入service)
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月29日 下午9:00:09
 * @version v1.0
 */

@Scope("prototype")
@Component("permissionCheck")
public class PermissionCheck implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 5190582672679879767L;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	public PermissionCheck() {
		Logger logger = LogManager.getLogger();
		logger.info("---********^^^^^^^^^^^^^^^^^^^^^^^^^^^spring实例化了----------------" + this.getClass());
	}
	
	/**
	 * 是否可访问
	 * @param user 用户
	 * @param moudle 模块code
	 * @return true：可访问，false：不可访问
	 */
	public boolean isAccesssible(User user, String moudle) {
		// 从用户中取出用户角色集合
		List<UserRole> userRoleList = user.getUserRoleList();
		// 如果没取到，就去数据库取
		if(userRoleList == null){
			userRoleList = userService.findAllUserRoleByUserId(user.getId());
		}
		// 通过用户角色可以查询出角色，而角色实体里面封装了角色权限集合；依次遍历即可
		for (UserRole userRole : userRoleList) {
			Set<RolePrivilege> rolePrivileges = roleService.findById(userRole.getId().getRoleId()).getRolePrivileges();
			for (RolePrivilege rolePrivilege : rolePrivileges) {
				if(moudle.equals(rolePrivilege.getId().getCode())) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	

}

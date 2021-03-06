package com.tax.web.nsfw;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tax.core.action.BaseAction;
import com.tax.core.util.ExcelUtils;
import com.tax.core.util.QueryHelper;
import com.tax.pojo.nsfw.Role;
import com.tax.pojo.nsfw.User;
import com.tax.pojo.nsfw.UserRole;
import com.tax.service.nsfw.RoleService;
import com.tax.service.nsfw.UserService;

/**
 * UserAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月11日 上午10:06:05
 * @version  v1.0
 */

public class UserAction extends BaseAction {

	private static final long serialVersionUID = 4526496105243102063L;
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	private List<User> userList;
	private User user;
	/** 页面传来的角色id数组 */
	private String[] roleIds;
	
	/** 文件上传的3个属性 */
	private File headImg;				// 这个名字和表单的name的值一样
	private String headImgFileName;
	private String headImgContentType;
	private File userExcel;
	private String userExcelFileName;
	private String userExcelContentType;
	
	/** 存放图片的本地文件夹  */
	private static final String USER_IMAGE_DIR = "D:/upload";
	
	/** 跳转到列表页面 */
	public String listUI() {
		//this.setUserList(userService.findAll());
		pageResult = userService.findByPage(new QueryHelper(User.class, "u"), getPageNo(), getPageSize());
		return "listUI";
	}

	/** 跳转到添加页面 */
	public String addUI() {
		List<Role> roleList = roleService.findAll();
		// 数据库查出所有角色
		ActionContext.getContext().put("roleList", roleList);
		return "addUI";
	}

	/** 添加  */
	public String add() {
		if (user != null) {
			String filePath = this.uploadFile();
			user.setHeadImg(filePath);
			userService.saveUserAndUserRole(user,roleIds);
		}
		return "list";
	}
	
	/** 跳转到编辑页面 */
	public String editUI() {
		if(null != user && null != user.getId()) {
			// 1.数据库查出所有角色,回显到页面
			List<Role> roleList = roleService.findAll();
			ActionContext.getContext().put("roleList", roleList);
			// 2.从数据库查出当前用户的用户角色
			List<UserRole> userRoleList = userService.findAllUserRoleByUserId(user.getId());
			// 3.把查询出来的当前用户的角色id存放到数组中，用于回显到 页面上
			int size = userRoleList.size();	// 把size先算出来，对后面for循环的效率有提高
			// 由于roleIds 是Action的属性且有setter和getter方法，可以直接在页面取值
			roleIds = new String[size];
			for(int i = 0;i < size;i++ ){
				roleIds[i] = userRoleList.get(i).getId().getRoleId();
			}
			// 4.查询出用户，回显到页面
			this.setUser(userService.findById(user.getId()));
		}
		return "editUI";
	}
	
	/** 编辑 */
	public String edit() {
		if (null != user && null != user.getId()) {
			// 判断有没上传新头像，如果上传了，就需要考虑删掉老的头像
			if (null != headImg) {
				// 删除原来的头像(这个头像地址从通过隐藏域前台传过来，如果本来就没有头像则为null)
				this.deleteHeadImg(user.getHeadImg());
				// 上传新头像并更新数据库
				user.setHeadImg(this.uploadFile());
			} 
			userService.updateUserAndUserRole(user,roleIds);
		}
		return "list";
	}
	
	/** 删除  */
	public String delete() {
		if(null != user && null != user.getId()) {
			// 把头像也删了 
			this.deleteHeadImg(userService.findById(user.getId()).getHeadImg());
			userService.deleteUserAndUserRole(user.getId());
		}
		return "list";
	}
	
	

	/** 批量删除 */
	public String batchDelete() {
		if(null != selectedRow ){
			for (String id : selectedRow) {
				// 把头像也删了 
				this.deleteHeadImg(userService.findById(id).getHeadImg());
				userService.deleteUserAndUserRole(id);
			}
		}
		return "list";
	}
	
	
	/** 
	 * 展示用户头像 Action方法
	 * @return 将头像输出到页面
	 * @see 访问方式：tax/nsfw/user_showHeadImg.action?user.id=xxxx
	 */
	public String showHeadImg() {
		// 这个user的id是通过前台传过来的
		if(null != user && user.getId() != null) {
			// 通过用户id去数据库查找出用户头像的地址
			String img = userService.findById(user.getId()).getHeadImg();
			if(StringUtils.isNotBlank(img)) {
				// 拼接成本地地址，如：D:/upload/user/0dc14d2b81444ce1b5600a3fe43f9f30.jpg
				// USER_IMAGE_DIR = D:/upload
				// img 如：user/0dc14d2b81444ce1b5600a3fe43f9f30.jpg
				File imgFile = new File(USER_IMAGE_DIR + "/" + img);
				// 如果图片文件存在，就输出到页面
				if(imgFile.exists()) {
					/** 获取HttpServletResponse */
					HttpServletResponse response = ServletActionContext.getResponse();
					/** 设置响应的内容类型 */
					response.setContentType("images/jpeg");
					/** 以下3行代码用于设置禁止浏览器缓存该图片 */
					response.setDateHeader("expries", -1);
					response.setHeader("Cache-Control", "no-cache");  
			        response.setHeader("Prama", "no-cache");  
			        // 以下为IO流操作
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					try {
						bis = new BufferedInputStream(new FileInputStream(imgFile));
						// 这个Response.getOutputStream()是用于输出到浏览器的输出流
						bos = new BufferedOutputStream(response.getOutputStream());
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = bis.read(buffer)) != -1) {
							bos.write(buffer, 0, len);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						// 关流
						if (bis != null) {
							try {
								bis.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						if (bos != null) {
							try {
								bos.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		//  这里没有返回视图，直接返回NONE
		return NONE;
	}
	
	/** 导出Excel */
	public String exportExcel() {
		long start = System.currentTimeMillis();
		String fileName = "用户数据";
		String sheetName = "国税用户";
		String title = "用户列表";
		String[] cellTitles = new String[]{"用户名","账号","部门","性别","邮箱"};
		// 从数据库查出数据
		List<User> uList = userService.findAll();
		// 封装数据
		List<ArrayList<Object>> data = new ArrayList<>(uList.size());
		for(User u : uList) {
			ArrayList<Object> rowData = new ArrayList<>();
			rowData.add(u.getName());
			rowData.add(u.getAccount());
			rowData.add(u.getDept());
			rowData.add(u.isGender() ? "男":"女");
			rowData.add(u.getEmail());
			data.add(rowData);
		}
		// 设置需要自动调整列宽的列
		int[] autoSizeCellNum = new int[]{4};	// 邮箱那一列宽度需要自动调整
		
		// 调用写好的工具类输出Excel文件
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			ExcelUtils.exportExcel(fileName, sheetName, title, cellTitles,autoSizeCellNum, data, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("导出 " + uList.size() + "条数据，耗时 " + (end - start) + "ms");
		return NONE;
	}
	
	/** 批量导入用户  */ 
	public String importExcel() {
		try {
			if(null != userExcelFileName && userExcelFileName.matches(".+\\.(?i)(xls|xlsx)")){
				userService.importExcel(userExcel, userExcelFileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "list";
	}
	
	/** 校验账号是否存在 */
	public String verifyAccount(){
		try {
			if(null != user && StringUtils.isNoneBlank(user.getAccount())){
				boolean result = userService.verifyAccount(user.getAccount(),user.getId());
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html");
				response.getWriter().print(String.valueOf(result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	
	
	/**
	 * 专门用于文件上传的方法，返回文件路径
	 * @return 文件路径
	 */
	private String uploadFile() {
		try {
			if (null != headImg) {
				// 获取存放文件夹路径
				// USER_IMAGE_DIR = D:/upload
				String prePath = USER_IMAGE_DIR.concat("/user");
				if(!new File(prePath).exists()) {
					new File(prePath).mkdirs();
				}
				// 新的文件名
				String fileName = UUID.randomUUID().toString().replaceAll("-", "")
						.concat(headImgFileName.substring(headImgFileName.lastIndexOf(".")));
				// 用common-io.jar的工具copy文件
				FileUtils.copyFile(headImg, new File(prePath,fileName));
				return "user/".concat(fileName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除用户头像的方法
	 * @param  imgUrl 头像url
	 */
	private void deleteHeadImg(String imgUrl) {
		try {
			if (StringUtils.isNotBlank(imgUrl)) {
				// 查找出用户头像的地址
				File imgFile = new File(USER_IMAGE_DIR + "/" + imgUrl);
				if (imgFile.exists()) {
					imgFile.delete();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** setter and getter method */
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File getHeadImg() {
		return headImg;
	}

	public void setHeadImg(File headImg) {
		this.headImg = headImg;
	}

	public String getHeadImgFileName() {
		return headImgFileName;
	}

	public void setHeadImgFileName(String headImgFileName) {
		this.headImgFileName = headImgFileName;
	}

	public String getHeadImgContentType() {
		return headImgContentType;
	}

	public void setHeadImgContentType(String headImgContentType) {
		this.headImgContentType = headImgContentType;
	}

	public File getUserExcel() {
		return userExcel;
	}

	public void setUserExcel(File userExcel) {
		this.userExcel = userExcel;
	}

	public String getUserExcelFileName() {
		return userExcelFileName;
	}

	public void setUserExcelFileName(String userExcelFileName) {
		this.userExcelFileName = userExcelFileName;
	}

	public String getUserExcelContentType() {
		return userExcelContentType;
	}

	public void setUserExcelContentType(String userExcelContentType) {
		this.userExcelContentType = userExcelContentType;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
	

}

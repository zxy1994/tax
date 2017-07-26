package com.tax.web.user;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.pojo.nsfw.User;
import com.tax.service.UserService;

/**
 * UserAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月11日 上午10:06:05
 * @version  v1.0
 */

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 4526496105243102063L;
	@Autowired
	private UserService userService;
	private List<User> userList;
	private User user;
	private String[] selectedRow;
	/** 文件上传的3个属性 */
	private File headImg;				// 这个名字和表单的name的值一样
	private String headImgFileName;
	private String headImgContentType;
	private static final String USER_IMAGE_DIR = "D:/upload";
	
	/** 跳转到列表页面 */
	public String listUI() {
		this.setUserList(userService.findAll());
		return "listUI";
	}

	/** 跳转到添加页面 */
	public String addUI() {
		return "addUI";
	}

	/** 添加  */
	public String add() {
		if (user != null) {
			String filePath = this.uploadFile();
			user.setHeadImg(filePath);
			userService.save(user);
		}
		return "list";
	}
	
	/** 跳转到编辑页面 */
	public String editUI() {
		if(null != user && null != user.getId()) {
			this.setUser(userService.findById(user.getId()));
		}
		return "editUI";
	}
	
	/** 编辑 */
	public String edit() {
		if (null != user && null != user.getId()) {
			if (null == headImg) {
				user.setHeadImg(userService.findById(user.getId()).getHeadImg());
			} else {
				// 删除原来的头像
				this.deleteHeadImg(userService.findById(user.getId()).getHeadImg());
				// 上传新头像并更新数据库
				user.setHeadImg(this.uploadFile());
			}
			userService.update(user);
		}
		return "list";
	}
	
	/** 删除  */
	public String delete() {
		if(null != user && null != user.getId()) {
			// 把头像也删了 
			this.deleteHeadImg(userService.findById(user.getId()).getHeadImg());
			userService.deleteById(user.getId());
		}
		return "list";
	}
	
	

	/** 批量删除 */
	public String batchDelete() {
		if(null != selectedRow ){
			for (String id : selectedRow) {
				userService.deleteById(id);
			}
		}
		return "list";
	}
	
	
	/** 
	 * 展示用户头像
	 * @return 将头像输出到页面
	 * @see 访问方式：tax/nsfw/user_showHeadImg.action?user.id=xxxx
	 */
	public String showHeadImg() {
		if(null != user && user.getId() != null) {
			// 查找出用户头像的地址
			String img = userService.findById(user.getId()).getHeadImg();
			if(StringUtils.isNotBlank(img)) {
				File imgFile = new File(USER_IMAGE_DIR + "/" + img);
				if(imgFile.exists()) {
					/** 获取HttpServletResponse */
					HttpServletResponse response = ServletActionContext.getResponse();
					/** 设置响应的内容类型 */
					response.setContentType("images/jpeg");
					/** 以下3行代码用于设置禁止浏览器缓存该图片 */
					response.setDateHeader("expries", -1);
					response.setHeader("Cache-Control", "no-cache");  
			        response.setHeader("Prama", "no-cache");  
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					try {
						bis = new BufferedInputStream(new FileInputStream(imgFile));
						bos = new BufferedOutputStream(response.getOutputStream());
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = bis.read(buffer)) != -1) {
							bos.write(buffer, 0, len);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
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
	 * @param 头像url
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
	

	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		System.out.println(uuid);
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

	public String[] getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
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
	

}

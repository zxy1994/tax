package com.tax.web.nsfw;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.core.util.ZipUtils;
import com.tax.pojo.nsfw.User;
import com.tax.service.nsfw.UserService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * FTL2ExcelByFreeMarker
 * 导出Excel通过FreeMarker测试类
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月17日 下午10:12:15
 * @version v1.0
 */
public class FTL2ExcelByFreeMarker extends ActionSupport {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserService userService;
	
	@SuppressWarnings("resource")
	public void export(){
		try {
			// 从数据库查出数据
			List<User> uList = userService.findAll();
			// 封装数据
			Map<String, Object> dataMap = new HashMap<String,Object>();
			List<Map<String,String>> userList = new ArrayList<>(uList.size());
			for(User u : uList) {
				Map<String,String> rowData = new HashMap<>();
				rowData.put("name", u.getName());
				rowData.put("account", u.getAccount());
				rowData.put("dept", u.getDept());
				rowData.put("gender", u.isGender() ? "男":"女");
				rowData.put("email", u.getEmail());
				userList.add(rowData);
			}
			if(userList.size() > 0){
				HttpServletRequest request = ServletActionContext.getRequest();
				FileWriter writer;
				for (int i = 0; i < 10; i++) {
					dataMap.put("userList", userList);
					Map<String, String> endMap = new HashMap<>();
					endMap.put("user", "老王");
					endMap.put("time", "2017-10-10 10:50:55");
					dataMap.put("endMap", endMap);
					Configuration cfg = new Configuration();
					cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/ftl");
					Template template = cfg.getTemplate("exportExcel.ftl");
					String rootPath = request.getRealPath("/");
					File temDir = new File(rootPath + "/download");
					if(!temDir.exists()){
						temDir.mkdirs();
					}
					writer = new FileWriter(temDir.getPath()+"/excel"+ i +".xls");
					template.process(dataMap, writer);
					writer.flush();
					writer.close();
				}
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=excel.zip");  
				ZipUtils.toZip(request.getRealPath("/")+"/download", response.getOutputStream(),false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return NONE;
	}
}

package com.tax.web.nsfw;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.core.util.ZipUtils;
import com.tax.pojo.nsfw.User;
import com.tax.service.nsfw.UserService;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

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
	public void exportZip(){
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
				/** 下面为下载zip压缩包相关流程 */
				HttpServletRequest request = ServletActionContext.getRequest();
				FileWriter writer;
				/** 1.创建临时文件夹  */
				String rootPath = request.getSession().getServletContext().getRealPath("/");
				File temDir = new File(rootPath + "/" + UUID.randomUUID().toString().replaceAll("-", ""));
				if(!temDir.exists()){
					temDir.mkdirs();
				}
				/** 2.生成需要下载的文件，存放在临时文件夹内 */
				// 这里我们直接来10个内容相同的文件为例，但这个10个文件名不可以相同
				for (int i = 0; i < 10; i++) {
					dataMap.put("userList", userList);
					Map<String, String> endMap = new HashMap<>();
					endMap.put("user", "老王");
					endMap.put("time", "2017-10-10 10:50:55");
					dataMap.put("endMap", endMap);
					Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
					cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/ftl");
					Template template = cfg.getTemplate("exportExcel.ftl");
					writer = new FileWriter(temDir.getPath()+"/excel"+ i +".xls");
					template.process(dataMap, writer);
					writer.flush();
					writer.close();
				}
				
				/** 3.设置response的header */
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=excel.zip");  
				System.out.println(temDir.getPath());
				
				/** 4.调用工具类，下载zip压缩包 */
				// 这里我们不需要保留目录结构
				ZipUtils.toZip(temDir.getPath(), response.getOutputStream(),false);
				
				/** 5.删除临时文件和文件夹 */
				// 这里我没写递归，直接就这样删除了
				File[] listFiles = temDir.listFiles();
				for (int i = 0; i < listFiles.length; i++) {
					listFiles[i].delete();
				}
				temDir.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return NONE;
	}
	
	public void exportExcel(){
		try {
			/** 1.从数据库查出数据 */
			List<User> uList = userService.findAll();
			
			/** 2.封装数据 */
			Map<String, Object> dataMap = new HashMap<String, Object>();
			List<Map<String, String>> userList = new ArrayList<>(uList.size());
			for (User u : uList) {
				Map<String, String> rowData = new HashMap<>();
				rowData.put("name", u.getName());
				rowData.put("account", u.getAccount());
				rowData.put("dept", u.getDept());
				rowData.put("gender", u.isGender() ? "男" : "女");
				rowData.put("email", u.getEmail());
				userList.add(rowData);
			}
			dataMap.put("userList", userList);
			
			/** 3.调用FreeMarker的Api生成Excel */
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			// 设置加载模板文件的位置
			cfg.setServletContextForTemplateLoading(ServletActionContext.getServletContext(), "/ftl");
			// 获取模板
			Template template = cfg.getTemplate("exportExcel.ftl");
			// 设置response的header 
			HttpServletResponse response = ServletActionContext.getResponse();
			// 防止乱码，设置下编码
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=testExcel.xls");
			// 生成Excel
			template.process(dataMap, response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

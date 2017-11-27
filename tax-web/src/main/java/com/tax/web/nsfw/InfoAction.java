package com.tax.web.nsfw;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tax.core.action.BaseAction;
import com.tax.core.util.PageResult;
import com.tax.core.util.QueryHelper;
import com.tax.pojo.nsfw.Info;
import com.tax.service.nsfw.InfoService;

/**
 * InfoAction
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年9月4日 下午11:05:12
 * @version v1.0
 */
public class InfoAction extends BaseAction {

	private static final long serialVersionUID = 4526496105243102063L;
	@Autowired
	private InfoService infoService;
	private List<Info> infoList;
	private Info info;
	private String fromType; //判断是从哪里进来的 ：n代表从导航进来的，s代表是从搜索
	
	/** 跳转到列表页面 
	 * @throws UnsupportedEncodingException */
	public String listUI() throws UnsupportedEncodingException {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Info queryInfo;
		// 判断是不是从导航里进来的
		if("n".equals(fromType)){
			queryInfo = null;
			session.setAttribute("queryInfo",queryInfo);
		}else if("s".equals(fromType)){
			queryInfo = info;
			session.setAttribute("queryInfo", queryInfo);
		}else {
			// 走这里，说明使重定象进来的，那么查询条件从session中拿
			queryInfo = (Info) session.getAttribute("queryInfo");
		}
		
		ActionContext.getContext().put("infoTypeMap", Info.INFO_TYPE_MAP);
		String hql = "FROM Info i";
		QueryHelper hq = new QueryHelper(Info.class, "i");
		if(null != queryInfo && StringUtils.isNotBlank(queryInfo.getTitle())) {
			hq.addCondition("i.title like ?", "%" + queryInfo.getTitle() +"%");
			this.setInfo(queryInfo);//用于重定向后的回显
		}
		hq.addOrderByProperty("i.id", QueryHelper.ORDER_BY_DESC);
		PageResult<Info> findByPage = infoService.findByPage(hq, 2, 2);
		this.setInfoList(infoService.findObjects(hq));
		return "listUI";
	}
	
	public void myTest(){
		QueryHelper hq = new QueryHelper(Info.class, "i");
		System.out.println(infoService.findObjects(hq));
	}

	/** 跳转到添加页面 */
	public String addUI() {
		ActionContext.getContext().put("infoTypeMap", Info.INFO_TYPE_MAP);
		info = new Info();
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return "addUI";
	}

	/** 添加  */
	public String add() {
		if (info != null) {
			infoService.save(info);;
		}
		return "list";
	}
	
	/** 跳转到编辑页面 */
	public String editUI() {
		if(null != info && null != info.getInfoId()) {
			ActionContext.getContext().put("infoTypeMap", Info.INFO_TYPE_MAP);
			this.setInfo(infoService.findById(info.getInfoId()));
		}
		return "editUI";
	}
	
	/** 编辑 */
	public String edit() {
		if (null != info && null != info.getInfoId()){
			infoService.update(info);;
		}
		return "list";
	}
	
	/** 删除  */
	public String delete() {
		if(null != info && null != info.getInfoId()) {
			infoService.deleteById(info.getInfoId());
		}
		return "list";
	}
	
	

	/** 批量删除 */
	public String batchDelete() {
		if(null != selectedRow ){
			for (String infoId : selectedRow) {
				infoService.deleteById(infoId);;
			}
		}
		return "list";
	}
	
	/** 异步发布 */
	public String changeStatus() {
		try {
			infoService.changeStatus(info);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	
	/** setter and getter method */
	public List<Info> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}

	public Info getInfo() {
		return info;
	}

	public void setInfo(Info info) {
		this.info = info;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	
	
}


package com.tax.web.nsfw;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tax.core.action.BaseAction;
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
	
	
	/** 跳转到列表页面 */
	public String listUI() {
		ActionContext.getContext().put("infoTypeMap", Info.INFO_TYPE_MAP);
		this.setInfoList(infoService.findAll());
		return "listUI";
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

	
	


}

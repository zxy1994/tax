package com.tax.web.nsfw;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.tax.core.action.BaseAction;
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
	
	
	/** 跳转到列表页面 */
	public String listUI() {
		ActionContext.getContext().put("infoTypeMap", Info.INFO_TYPE_MAP);
		/*String hql = "FROM Info i";
		List<Object> parameters = new ArrayList<>();
		if(null != info) {
			if(StringUtils.isNotBlank(info.getTitle())) {
				hql += " where i.title like ?";
				parameters.add("%" + info.getTitle() + "%");
			}
		}
		this.setInfoList(infoService.findObjects(hql, parameters));*/
		QueryHelper hq = new QueryHelper(Info.class, "i");
		if(null != info) {
			hq.addCondition("i.title like ?", "%" + info.getTitle() +"%");
		}
		this.setInfoList(infoService.findObjects(hq));
		System.out.println("--------------");
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		executorService.execute(new MyTestThread(infoService));
		executorService.shutdown();
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

}
class MyTestThread implements Runnable{
	private InfoService infoService;

	public MyTestThread(InfoService infoService) {
		super();
		this.infoService = infoService;
	}

	@Override
	public void run() {
		QueryHelper hq = new QueryHelper(Info.class, "i");
		System.out.println(infoService.findObjects(hq));
	}
	
}

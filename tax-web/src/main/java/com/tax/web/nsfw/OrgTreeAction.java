package com.tax.web.nsfw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.pojo.nsfw.OrgTree;
import com.tax.service.nsfw.OrgTreeService;

/**
 * OrgTreeAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 上午11:45:03
 * @version  v1.0
 */

public class OrgTreeAction extends ActionSupport implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 2478778799182312734L;
	@Autowired
	private OrgTreeService orgTreeService;
	/** 接受树展开时传来的id */
	private Integer id;
	/** responseData */
	private List responseData;
	
	
	/** 跳转到展示树页面 */
	public String showTree() {
		return SUCCESS;
	}
	
	/** 通过父节点id查找子节点 */
	public String findByPId() {
		if(id == null){
			id = 0;
		}
		// 通过传来的父id查询出数据
		List<OrgTree> list = orgTreeService.findListByPId(id);
		// 封装数据
		/** 上面的父id为0的话，只会显示根节点，因此我们处理下，显示根节点和一级子节点 */
		if(id == 0){
			OrgTree rootTree = list.get(0);
			List<OrgTree> childList = orgTreeService.findListByPId(rootTree.getId());
			responseData = new ArrayList();
			HashMap<String,Object> map = new HashMap<>();
			map.put("id", rootTree.getId());
			map.put("text", rootTree.getText());
			map.put("state", "open");
			map.put("children", childList);
			responseData.add(map);
		}else{
			responseData = list;
		}
		return SUCCESS;
	}
	
	
	public String showTreeTable() {
		return SUCCESS;
	}
	
	public String findTreeTableData() {
		List<OrgTree> data; 
		if(null == id){
			data = orgTreeService.findListByPId(orgTreeService.findListByPId(0).get(0).getId());
		} else {
			data = orgTreeService.findListByPId(id);
		}
		responseData = data;
		return SUCCESS;
	}
	
	
	
	/** setter and getter method */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List getResponseData() {
		return responseData;
	}

	public void setResponseData(List responseData) {
		this.responseData = responseData;
	}

	
	
	
}
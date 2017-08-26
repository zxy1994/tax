package com.tax.web.nsfw;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	/** orgTree */
	private OrgTree orgTree;
	/** 接受传来的id */
	private Integer id;
	/** 接受传来的ids */
	private String ids;
	/** responseData */
	private List responseData;
	/** tip */
	private int tip;
	
	
	/** 跳转到展示树页面 */
	public String showTree() {
		return SUCCESS;
	}
	
	/** 通过树父节点id查找子节点 */
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
	
	/** 跳转到树表格页面 */
	public String showTreeTable() {
		return SUCCESS;
	}
	
	/** 查找树表格的数据 */
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
	
	/** 跳转到添加树的页面 */
	public String showAddTree(){
		orgTree = orgTreeService.findById(id);
		return SUCCESS;
	}
	
	/** 添加树 */
	public String addTree() {
		try {
			if(null != orgTree){
				orgTreeService.save(orgTree);
				tip = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			tip = 0;
		}
		return SUCCESS;
	}
	
	/** 跳转到编辑树的页面 */
	public String showUpdateTree() {
		orgTree = orgTreeService.findById(id);
		return SUCCESS;
	}
	
	/** 编辑树 */
	public String updateTree() {
		try {
			if(null != orgTree){
				orgTreeService.update(orgTree);
				tip = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			tip = 0;
		}
		return SUCCESS;
	}
	
	public String deleteTree() {
		try {
			if(StringUtils.isNotBlank(ids)){
				String[] idsArr = ids.split(",");
				List<Integer> idList = new ArrayList<>();
				for (String id : idsArr) {
					idList.add(new Integer(id));
				}
				orgTreeService.delete(idList);
				tip = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			tip = 0;
		}
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

	public OrgTree getOrgTree() {
		return orgTree;
	}

	public void setOrgTree(OrgTree orgTree) {
		this.orgTree = orgTree;
	}

	public int getTip() {
		return tip;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
}

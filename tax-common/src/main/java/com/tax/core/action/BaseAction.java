package com.tax.core.action;

import java.io.Serializable;

import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年8月9日 下午9:30:48
 * @version v1.0
 */
public abstract class BaseAction extends ActionSupport implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -3789623170523600503L;
	/** 列表多选 */
	protected String[] selectedRow;
	
	/** setter and getter method */
	public String[] getSelectedRow() {
		return selectedRow;
	}
	
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}
	

}

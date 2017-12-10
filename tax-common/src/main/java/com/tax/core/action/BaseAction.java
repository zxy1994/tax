package com.tax.core.action;

import java.io.Serializable;

import com.opensymphony.xwork2.ActionSupport;
import com.tax.core.util.PageResult;

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
	protected PageResult<?> pageResult;
	protected int pageNo;
	protected int pageSize;
	private static int DEFAULT_PAGESIZE = 3;
	
	/** setter and getter method */
	public String[] getSelectedRow() {
		return selectedRow;
	}
	
	public void setSelectedRow(String[] selectedRow) {
		this.selectedRow = selectedRow;
	}

	public PageResult<?> getPageResult() {
		return pageResult;
	}

	public void setPageResult(PageResult<?> pageResult) {
		this.pageResult = pageResult;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		if (pageSize == 0) {
			pageSize = DEFAULT_PAGESIZE;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	

}

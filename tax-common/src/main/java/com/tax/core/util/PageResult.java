package com.tax.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果bean
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月27日 下午2:34:31
 * @version v1.0
 */
public class PageResult<T> {
	private int pageNo;
	private long totalCount;
	private int totalPage;
	private int pageSize;
	private List<T> items;
	
	public PageResult(int pageNo, long totalCount, int pageSize, List<T> items) {
		this.pageNo = pageNo;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.items = items == null ? new ArrayList<T>() : items;
		// 判断下有没数据，没数据时总页数和当前页设为0
		if(totalCount > 0){
			int temp = (int) (totalCount / pageSize);
			this.totalPage = totalCount % pageSize == 0 ? temp : temp + 1;
		} else {
			this.totalPage = 0;
			this.pageNo = 0;
		}
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
	
	
}

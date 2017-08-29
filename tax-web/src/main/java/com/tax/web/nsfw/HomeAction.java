package com.tax.web.nsfw;

import java.io.Serializable;

import com.opensymphony.xwork2.ActionSupport;

/**
 * HomeAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月29日 上午10:20:12
 * @version  v1.0
 */

public class HomeAction extends ActionSupport implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 7334577236381569804L;
	
	/** 跳转到纳税服务子系统首页 */
	public String frame(){
		return "frame";
	}
	
	/** 跳转到纳税服务子系统首页-顶部 */
	public String top(){
		return "top";
	}
	
	/** 跳转到纳税服务子系统首页-左边 */
	public String left(){
		return "left";
	}

}

package com.tax.web.home;

import java.io.Serializable;

import com.opensymphony.xwork2.ActionSupport;

/**
 * HomeAction
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月23日 下午5:25:28
 * @version  v1.0
 */

public class HomeAction extends ActionSupport implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 8375502324411601544L;
	
	@Override
	public String execute() {
		return "home";
	}

}

package com.tax.core.util;

/**
 * Excel行数据解析结果类
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月30日 下午10:33:00
 * @version v1.0
 */
public class ParseResult {
	private boolean pass = true;
	private String errorMsg;

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "PareseResult [pass=" + pass + ", errorMsg=" + errorMsg + "]";
	}
	
}

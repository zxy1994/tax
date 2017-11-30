package com.tax.core.util;

/**
 * 解析Excel行数据时的传输类
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月30日 下午10:34:00
 * @version v1.0
 */
public class ParseDto {
	/** 字符串类型 */
	public static final String FILED_TYPE_STRING = "java.lang.String";
	/** 整数类型 */
	public static final String FILED_TYPE_INTEGER = "java.lang.Integer";
	/** 小数类型 */
	public static final String FILED_TYPE_DOUBLE = "java.lang.Double";
	
	/** 目标对象 */
	private Object targetObj;
	/** 字段名字 */
	private String filedName;
	/** 是否必填 */
	private boolean required;
	/** 字段出错时显示的字段的名字 */
	private String filedErrorTipName;
	/** 数据类型 */
	private String filedType;
	/** 正则 */
	private String regex;

	/** 不带正则的构造方法 */
	public ParseDto(Object targetObj, String filedName, boolean required, String filedErrorTipName, String filedType) {
		super();
		this.targetObj = targetObj;
		this.filedName = filedName;
		this.required = required;
		this.filedErrorTipName = filedErrorTipName;
		this.filedType = filedType;
	}
	
	/** 带指定正则表达式校验的构造方法 */
	public ParseDto(Object targetObj, String filedName, boolean required, String filedErrorTipName, String filedType,
			String regex) {
		super();
		this.targetObj = targetObj;
		this.filedName = filedName;
		this.required = required;
		this.filedErrorTipName = filedErrorTipName;
		this.filedType = filedType;
		this.regex = regex;
	}

	public Object getTargetObj() {
		return targetObj;
	}

	public String getFiledName() {
		return filedName;
	}

	public boolean isRequired() {
		return required;
	}

	public String getFiledErrorTipName() {
		return filedErrorTipName;
	}

	public String getRegex() {
		return regex;
	}

	public String getFiledType() {
		return filedType;
	}
	
}

package com.tax.pojo.nsfw;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Info
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年9月4日 下午10:02:10
 * @version v1.0
 */
public class Info implements java.io.Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = -7352333340651996645L;
	private String infoId;
	private String type;
	private String source;
	private String title;
	private String content;
	private String memo;
	private String creator;
	private Timestamp createTime;
	private String state;
	
	public static String INFO_STATE_PUBLIC = "1";//发布
	public static String INFO_STATE_STOP = "0";//停用
	
	public static String INFO_TYPE_TZGG = "tzgg";
	public static String INFO_TYPE_ZCSD = "zcsd";
	public static String INFO_TYPE_NSZD = "nszd";
	
	public static Map<String, String> INFO_TYPE_MAP = new HashMap<String, String>();
	static {
		INFO_TYPE_MAP.put(INFO_TYPE_TZGG, "通知公告");
		INFO_TYPE_MAP.put(INFO_TYPE_ZCSD, "政策速递");
		INFO_TYPE_MAP.put(INFO_TYPE_NSZD, "纳税指导");
	}

	public Info() {
	}

	public Info(String title) {
		this.title = title;
	}

	public Info(String type, String source, String title, String content, String memo, String creator, Timestamp createTime, String state) {
		this.type = type;
		this.source = source;
		this.title = title;
		this.content = content;
		this.memo = memo;
		this.creator = creator;
		this.createTime = createTime;
		this.state = state;
	}


	public String getInfoId() {
		return this.infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	

}
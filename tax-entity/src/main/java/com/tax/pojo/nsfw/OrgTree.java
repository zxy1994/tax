package com.tax.pojo.nsfw;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OrgTree(用于测试EasyUI的Tree)
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 上午9:24:54
 * @version  v1.0
 */
@Entity
@Table(name="org_tree")
public class OrgTree implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 6661807356404913246L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;
	@Column
	private String title;
	@Column(name="p_id")
	private Integer pId;
	@Column(name="is_parent")
	private boolean isParent;

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
	
	// 这个2个get方法主要用于tree的数据，tree数据需要 {"id":1,"text":"root","state":"closed"}
	public String getText() {
		return getTitle();
	}
	public String getState() {
		return getIsParent() ? "closed" : "open";
	}
	
}

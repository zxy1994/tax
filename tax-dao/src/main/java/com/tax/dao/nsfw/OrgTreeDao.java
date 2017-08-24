package com.tax.dao.nsfw;

import java.util.List;

import com.tax.core.dao.BaseDao;
import com.tax.pojo.nsfw.OrgTree;

/**
 * OrgTreeDao
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 上午11:43:04
 * @version  v1.0
 */

public interface OrgTreeDao extends BaseDao<OrgTree> {
	
	/**
	 * 通过父id查询子元素
	 * @param pId
	 * @return 子元素集合
	 */
	List<OrgTree> findListByPId(Integer pId);

}

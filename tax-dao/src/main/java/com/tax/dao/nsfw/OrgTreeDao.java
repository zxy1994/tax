package com.tax.dao.nsfw;

import java.util.List;

import org.hibernate.Session;

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

	/**
	 * 保存节点树，如果名字存在则抛异常
	 * @param orgTree
	 */
	void saveOrgTree(OrgTree orgTree);
	
	/**
	 * 更新节点树，如果名字存在则抛异常
	 * @param orgTree
	 */
	void updateOrgTree(OrgTree orgTree);

	/**
	 * 批量删除
	 * @param idList
	 */
	void batchDelete(List<Integer> idList);


	/**
	 * 统计子节点个数
	 * @param pId
	 * @return
	 */
	long findCountByPId(Integer pId);

}

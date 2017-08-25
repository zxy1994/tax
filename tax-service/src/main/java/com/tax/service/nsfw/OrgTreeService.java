package com.tax.service.nsfw;

import java.util.List;

import com.tax.pojo.nsfw.OrgTree;

/**
 * OrgTreeService
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 下午1:56:57
 * @version  v1.0
 */

public interface OrgTreeService {

	/**
	 * 通过父id查询子元素
	 * @param pId
	 * @return 子元素集合
	 */
	List<OrgTree> findListByPId(Integer pId);

	/**
	 * 通过id查询
	 * @param id
	 * @return 实体
	 */
	OrgTree findById(Integer id);

	/**
	 * 保存实体
	 * @param orgTree
	 */
	void save(OrgTree orgTree);

	/**
	 * 更新实体
	 * @param orgTree
	 */
	void update(OrgTree orgTree);

}

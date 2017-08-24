package com.tax.service.nsfw.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.dao.nsfw.OrgTreeDao;
import com.tax.pojo.nsfw.OrgTree;
import com.tax.service.nsfw.OrgTreeService;

/**
 * OrgTreeServiceImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 下午1:57:25
 * @version  v1.0
 */
@Service("orgTreeService")
public class OrgTreeServiceImpl implements OrgTreeService {
	@Autowired
	private OrgTreeDao orgTreeDao;
	
	/**
	 * 通过父id查询子元素
	 * @param pId
	 * @return 子元素集合
	 */
	@Override
	public List<OrgTree> findListByPId(Integer pId) {
		return orgTreeDao.findListByPId(pId);
	}
	
}

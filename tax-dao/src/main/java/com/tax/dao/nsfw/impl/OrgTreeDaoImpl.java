package com.tax.dao.nsfw.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.tax.core.dao.impl.BaseDaoImpl;
import com.tax.dao.nsfw.OrgTreeDao;
import com.tax.pojo.nsfw.OrgTree;

/**
 * OrgTreeDaoImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 上午11:43:46
 * @version  v1.0
 */

@Repository("orgTreeDao")
public class OrgTreeDaoImpl extends BaseDaoImpl<OrgTree> implements OrgTreeDao {
	
	/**
	 * 通过父id查询子元素
	 * @param pId
	 * @return 子元素集合
	 */
	@Override
	public List<OrgTree> findListByPId(Integer pId) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(OrgTree.class);
		criteria.add(Restrictions.eq("pId", pId));
		return criteria.list();
	}


}

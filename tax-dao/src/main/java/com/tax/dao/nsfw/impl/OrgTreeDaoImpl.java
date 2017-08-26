package com.tax.dao.nsfw.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
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
	
	/**
	 * 保存节点树，如果名字存在则抛异常
	 * @param orgTree
	 */
	@Override
	public void saveOrgTree(OrgTree orgTree) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(OrgTree.class);
		// 判断：同一个父节点下，子节点名字不能重复
		List list = criteria.add(Restrictions.eq("title", orgTree.getTitle()))
				.add(Restrictions.eq("pId", orgTree.getpId())).list();
		if(null != list && list.size() > 0){
			throw new RuntimeException("title重复了！");
		}
		// 保存节点
		session.save(orgTree);
		// 更新父节点的状态
		OrgTree parentTree = (OrgTree) session.createCriteria(OrgTree.class)
				.add(Restrictions.eq("id", orgTree.getpId())).setMaxResults(1).list().get(0);
		parentTree.setIsParent(true);
		session.update(parentTree);
	}
	
	/**
	 * 更新节点树，如果名字存在则抛异常
	 * @param orgTree
	 */
	@Override
	public void updateOrgTree(OrgTree orgTree) {
		Session session = this.getCurrentSession();
		// 判断：同一个父节点下，子节点名字不能重复
		Criteria criteria = session.createCriteria(OrgTree.class);
		List list = criteria.add(Restrictions.eq("title", orgTree.getTitle()))
				.add(Restrictions.eq("pId", orgTree.getpId()))
				.add(Restrictions.ne("id", orgTree.getId())).list();
		if(null != list && list.size() > 0){
			throw new RuntimeException("title重复了！");
		}
		// 更新标题
		OrgTree treeFromDb = session.get(OrgTree.class, orgTree.getId());
		treeFromDb.setTitle(orgTree.getTitle());
	}

	/**
	 * 批量删除
	 * @param orgTree
	 */
	@Override
	public void batchDelete(List<Integer> idList) {
		Session session = this.getCurrentSession();
		StringBuffer hql = new StringBuffer("DELETE OrgTree where id in (");
		for (int i = 0; i < idList.size(); i++) {
			hql.append( (i == 0) ? "?" :",?");
		}
		hql.append(")");
		Query query = session.createQuery(hql.toString());
		for (int i = 0; i < idList.size(); i++) {
			query.setParameter(i, idList.get(i));
		}
		query.executeUpdate();
	}


	@Override
	public long findCountByPId(Integer pId) {
		Session session = this.getCurrentSession();
		Query query = session.createQuery("select count(pId) from OrgTree where pId = ?");
		query.setParameter(0, pId);
		return (long) query.uniqueResult();
	}


}

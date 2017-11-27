package com.tax.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.tax.core.dao.BaseDao;
import com.tax.core.util.PageResult;
import com.tax.core.util.QueryHelper;

/**
 * BaseDaoImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年6月29日 下午12:23:16
 * @version  v1.0
 */
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	private Class<T> clazz;
	
	/** 按照类型自动注入SessionFactory; 在实例化的时候,Spring按照形参的类型自动注入 */
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	
	public BaseDaoImpl() {
		//this表示当前被实例化的对象；如UserDaoImpl
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();//BaseDaoImpl<User> 
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
	}
	
	/**
	 * 获取session
	 * @return session
	 */
	public Session getCurrentSession(){
		Session session = null;
		try {
			session = getSessionFactory().getCurrentSession();
		} catch (HibernateException e) {
			throw new RuntimeException("getCurrentSession error", e);
			//session = getSessionFactory().openSession();
		}
		return session;
	}
	
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
	
	@Override
	public void deleteById(Serializable id) {
		getHibernateTemplate().delete(findById(id));
	}
	
	@Override
	public T findById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}
	@Override
	public List<T> findAll() {
		Session session = getCurrentSession();
		Query query = session.createQuery("FROM "+ clazz.getSimpleName());
		return query.list();
	}


	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		Session session = getCurrentSession();
		Query query = session.createQuery(hql);
		if(parameters != null && parameters.size() > 0) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		return query.list();
	}
	
	/**
	 * 条件查询--使用查询助手
	 * @param qh 		查询助手对象
	 * @return List      list集合
	 */
	@Override
	public List<T> findObjects(QueryHelper qh) {
		return this.findObjects(qh.getListQueryHql(), qh.getParameters());
	}
	
	/**
	 * 分页查询
	 * @param qh 查询助手
	 * @param pageNo 当前页
	 * @param pageSize 页大小
	 * @return 分页结果对象
	 */
	@Override
	public PageResult<T> findByPage(QueryHelper qh, int pageNo, int pageSize) {
		Session session = this.getCurrentSession();
		List<T> list = new ArrayList<>();
		/**1.先查询出总记录数 */
		List<Object> parameters = qh.getParameters();
		Query countQuery = session.createQuery(qh.getCountHql());
		if(null != parameters){
			int parametesSize = parameters.size();
			for (int i = 0; i < parametesSize; i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		long totalCount = (long) countQuery.uniqueResult();
		if(totalCount > 0){
			/**2.查询出当前页数据 */
			Query listQuery = session.createQuery(qh.getListQueryHql());
			
			if(null != parameters){
				int parametesSize = parameters.size();
				for (int i = 0; i < parametesSize; i++) {
					listQuery.setParameter(i, parameters.get(i));
				}
			}
			if(pageNo == 0) {
				pageNo = 1;
			}
			listQuery.setFirstResult((pageNo - 1) * pageSize);
			listQuery.setMaxResults(pageSize);
			// 当前页的数据
			list = listQuery.list();
		}
		return new PageResult<>(pageNo, totalCount, pageSize, list);
	}
	
}

package com.tax.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.tax.core.dao.BaseDao;

/**
 * BaseDaoImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年6月29日 下午12:23:16
 * @version  v1.0
 */
public abstract class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {
	
	private Class<T> clazz;
	
	// 按照类型自动注入SessionFactory; 在实例化的时候,Spring按照形参的类型自动注入
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
		for (int i = 0; i < parameters.size(); i++) {
			query.setParameter(i, parameters.get(i));
		}
		return query.list();
	}
	
	
}

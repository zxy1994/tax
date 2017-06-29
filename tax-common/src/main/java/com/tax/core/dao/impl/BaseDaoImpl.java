package com.tax.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private Logger log = LogManager.getLogger(getClass());
	
	// 按照类型自动注入SessionFactory
	@Autowired
	public void setMySessionFactory(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
		log.info("注入了SessionFactory");
	}
	
	public BaseDaoImpl() {
		//this表示当前被实例化的对象；如UserDaoImpl
		ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();//BaseDaoImpl<User> 
		clazz = (Class<T>)pt.getActualTypeArguments()[0];
		log.info("实例化了BaseDaoImpl");
		
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
			session = getSessionFactory().openSession();
		}
		return session;
	}
	
	/**
	 * 新增
	 * @param entity
	 */
	@Override
	public void save(T entity) {
		getHibernateTemplate().save(entity);
	}
	
	/**
	 * 更新
	 * @param entity
	 */
	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}
	
	/**
	 * 根据id删除
	 * @param id
	 */
	@Override
	public void delete(Serializable id) {
		getHibernateTemplate().delete(findById(id));
	}
	
	/**
	 * 通过id查找
	 * @param id
	 * @return 实体
	 */
	@Override
	public T findById(Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 查找所有
	 * @return List集合
	 */
	@Override
	public List<T> findAll() {
		Session session = getCurrentSession();
		Query query = session.createQuery("FROM "+ clazz.getSimpleName());
		return query.list();
	}

}

package com.tax.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.tax.core.dao.BaseDao;
import com.tax.core.service.BaseService;
import com.tax.core.util.PageResult;
import com.tax.core.util.QueryHelper;

/**
 * BaseServiceImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年10月29日 下午4:53:48
 * @version v1.0
 */

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao; // 后续接收子类dao
	/** 该方法给子类调用 */
	protected void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	
	
	@Override
	public void save(T entity) {
		baseDao.save(entity);
	}

	@Override
	public void update(T entity) {
		baseDao.update(entity);
	}

	@Override
	public void deleteById(Serializable id) {
		baseDao.deleteById(id);
	}

	@Override
	public T findById(Serializable id) {
		return baseDao.findById(id);
	}

	@Override
	public List<T> findAll() {
		return baseDao.findAll();
	}
	
	
	/**
	 * 条件查询
	 * @param hql		 hql语句
	 * @param parameters 参数集合
	 * @return List      list集合
	 */
	@Override
	public List<T> findObjects(String hql, List<Object> parameters) {
		return baseDao.findObjects(hql, parameters);
	}
	
	/**
	 * 条件查询--使用查询助手
	 * @param qh 		查询助手对象
	 * @return List      list集合
	 */
	public List<T> findObjects(QueryHelper qh) {
		return baseDao.findObjects(qh);
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
		return baseDao.findByPage(qh, pageNo, pageSize);
	}
	
	
	

}

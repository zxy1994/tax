package com.tax.core.service.impl;

import java.io.Serializable;
import java.util.List;

import com.tax.core.dao.BaseDao;
import com.tax.core.service.BaseService;

/**
 * BaseServiceImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年10月29日 下午4:53:48
 * @version v1.0
 */

public abstract class BaseServiceImpl<T> implements BaseService<T> {
	private BaseDao<T> baseDao; // 后续接收子类dao
	
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
	
	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}
	

}

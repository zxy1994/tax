package com.tax.core.dao;

import java.io.Serializable;
import java.util.List;

/**
 * BaseDao
 * @author ZENG.XIAO.YAN
 * @date 2017年6月29日 上午10:36:57
 * @version v1.0
 */
public interface BaseDao<T> {

	/**
	 * 新增
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 更新
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 根据id删除
	 * @param id
	 */
	public void delete(Serializable id);

	
	/**
	 * 通过id查找
	 * @param id
	 * @return 实体
	 */
	public T findById(Serializable id);

	
	/**
	 * 查找所有
	 * @return List集合
	 */
	public List<T> findAll();
}

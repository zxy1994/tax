package com.tax.core.dao;

import java.io.Serializable;
import java.util.List;

import com.tax.core.util.PageResult;
import com.tax.core.util.QueryHelper;

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
	public void deleteById(Serializable id);

	
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
	
	
	/**
	 * 条件查询
	 * @param hql		 hql语句
	 * @param parameters 参数集合
	 * @return List      list集合
	 */
	public List<T> findObjects(String hql, List<Object> parameters);
	
	/**
	 * 条件查询--使用查询助手
	 * @param qh 		查询助手对象
	 * @return List      list集合
	 */
	public List<T> findObjects(QueryHelper qh);
	
	/**
	 * 分页查询
	 * @param qh 查询助手
	 * @param pageNo 当前页
	 * @param pageSize 页大小
	 * @return 分页结果对象
	 */
	public PageResult<T> findByPage(QueryHelper qh, int pageNo, int pageSize);
	
}

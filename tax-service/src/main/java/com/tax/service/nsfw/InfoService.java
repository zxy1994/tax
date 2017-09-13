package com.tax.service.nsfw;

import java.io.Serializable;
import java.util.List;

import com.tax.pojo.nsfw.Info;

/**
 * InfoService
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年9月4日 下午10:05:39
 * @version v1.0
 */
public interface InfoService {
	
	/**
	 * 新增
	 * @param info
	 */
	public void save(Info info);

	/**
	 * 更新
	 * @param info
	 */
	public void update(Info info);

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
	public Info findById(Serializable id);

	
	/**
	 * 查找所有
	 * @return List集合
	 */
	public List<Info> findAll();

	/**
	 * 修改状态
	 * @param info
	 */
	public void changeStatus(Info info);

	
}

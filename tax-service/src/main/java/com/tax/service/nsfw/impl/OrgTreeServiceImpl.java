package com.tax.service.nsfw.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.dao.nsfw.OrgTreeDao;
import com.tax.pojo.nsfw.OrgTree;
import com.tax.service.nsfw.OrgTreeService;

/**
 * OrgTreeServiceImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年8月24日 下午1:57:25
 * @version  v1.0
 */
@Service("orgTreeService")
public class OrgTreeServiceImpl implements OrgTreeService {
	@Autowired
	private OrgTreeDao orgTreeDao;
	
	/**
	 * 通过父id查询子元素
	 * @param pId
	 * @return 子元素集合
	 */
	@Override
	public List<OrgTree> findListByPId(Integer pId) {
		return orgTreeDao.findListByPId(pId);
	}
	
	/**
	 * 通过id查询
	 * @param id
	 * @return 实体
	 */
	@Override
	public OrgTree findById(Integer id) {
		return orgTreeDao.findById(id);
	}
	
	/**
	 * 保存实体
	 * @param orgTree
	 */
	@Override
	public void save(OrgTree orgTree) {
		try {
			orgTreeDao.saveOrgTree(orgTree);
		} catch (Exception e) {
			throw new RuntimeException("保存树的方法出现异常", e);
		}
	}
	
	/**
	 * 更新实体
	 * @param orgTree
	 */
	@Override
	public void update(OrgTree orgTree) {
		try {
			orgTreeDao.updateOrgTree(orgTree);
		} catch (Exception e) {
			throw new RuntimeException("更新树的方法出现异常", e);
		}
	}
	
	/**
	 * 批量删除
	 * @param idList
	 */
	@Override
	public void delete(List<Integer> idList) {
		Integer pId = orgTreeDao.findById(idList.get(0)).getpId();//先查出父节点，第3步有用
		// 1.查出id集合中的所有子节点，将子节点id也加入到集合中
		idList.addAll(this.findChildId(idList));
		// 2.根据集合中的id删除对应数据库数据
		orgTreeDao.batchDelete(idList);
		// 3.判断父节点有没有子节点，没有就将父节点变成子节点
		long count = orgTreeDao.findCountByPId(pId);
		if(count == 0){
			OrgTree parentTree = orgTreeDao.findById(pId);
			parentTree.setIsParent(false);
			orgTreeDao.update(parentTree);
		}
	}

	
	/**
	 * 查询出传入的id集合的所有子节点id
	 * @param idList id集合
	 * @return	子节点id集合
	 */
	private List<Integer> findChildId(List<Integer> idList) {
		List<Integer> list = new ArrayList<>();
		// 这个for循环相当于把传进来的id集合的一级子节点查出来放到list中了
		for (Integer id : idList) {
			OrgTree tree = this.findById(id);
			// 如果是父节点，就把子节点查出来，加到集合里面
			if(tree.getIsParent()){
				List<OrgTree> listByPId = this.findListByPId(id);
				for (OrgTree orgTree : listByPId) {
					list.add(orgTree.getId());
				}
			}
		}
		/** 这个list装的是传进来的节点的一级子节点，因此用addAll，对查出的子节点再递归查询子节点 。直到没有子节点结束*/;
		// 这里要判断，否则死循环
		if(list.size() > 0){
			list.addAll(findChildId(list));
		}
		return list;
	}
	
}

package com.tax.service.nsfw.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.dao.nsfw.InfoDao;
import com.tax.pojo.nsfw.Info;
import com.tax.service.nsfw.InfoService;
/**
 * InfoServiceImpl
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年9月4日 下午10:08:05
 * @version v1.0
 */

@Service("infoService")
public class InfoServiceImpl implements InfoService {
	@Autowired
	private InfoDao infoDao;
	
	@Override
	public void save(Info info) {
		infoDao.save(info);
	}

	@Override
	public void update(Info info) {
		infoDao.update(info);
	}

	@Override
	public void deleteById(Serializable id) {
		infoDao.deleteById(id);
	}

	@Override
	public Info findById(Serializable id) {
		return infoDao.findById(id);
	}

	@Override
	public List<Info> findAll() {
		return infoDao.findAll();
	}

}

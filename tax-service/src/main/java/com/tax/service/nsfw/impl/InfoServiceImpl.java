package com.tax.service.nsfw.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tax.core.service.impl.BaseServiceImpl;
import com.tax.core.util.SpringConfigTool;
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
public class InfoServiceImpl extends BaseServiceImpl<Info> implements InfoService {
	private InfoDao infoDao;
	@Autowired
	public void setInfoDao(InfoDao infoDao) {
		super.setBaseDao(infoDao);
		this.infoDao = infoDao;
	}

	/**
	 * 修改状态
	 * @param info
	 */
	@Override
	public void changeStatus(Info info) {
		try {
			Info info2 = infoDao.findById(info.getInfoId());
			info2.setState(info.getState());
			infoDao.update(info2);
		} catch (Exception e) {
			throw new RuntimeException("异步发布service发生异常", e);
		}
	}

}


package com.tax.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tax.dao.nsfw.UserDao;
import com.tax.service.NsfwService;

/**
 * NsfwServiceImpl
 * @author   ZENG.XIAO.YAN
 * @date 	 2017年7月1日 下午3:19:22
 * @version  v1.0
 */

@Service("nsfwService")
public class NsfwServiceImpl implements NsfwService {
	
	private Logger log = LogManager.getLogger(getClass());
	@Autowired
	private UserDao userDao; 
	
	
}

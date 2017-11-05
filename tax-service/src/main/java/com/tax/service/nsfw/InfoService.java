package com.tax.service.nsfw;

import com.tax.core.service.BaseService;
import com.tax.pojo.nsfw.Info;

/**
 * InfoService
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年9月4日 下午10:05:39
 * @version v1.0
 */
public interface InfoService extends BaseService<Info> {

	/**
	 * 修改状态
	 * @param info
	 */
	public void changeStatus(Info info);

	
}

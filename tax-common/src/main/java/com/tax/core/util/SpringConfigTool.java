package com.tax.core.util;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * SpringConfigTool  用来获取Spring容器里面的对象的工具类
 * @author 	ZENG.XIAO.YAN
 * @date 	2017年11月13日 下午9:50:59
 * @version v1.0
 */
public class SpringConfigTool implements ApplicationContextAware {// extends ApplicationObjectSupport{

	private static ApplicationContext ac = null;
	private static SpringConfigTool springConfigTool = null;

	public synchronized static SpringConfigTool init() {
		if (springConfigTool == null) {
			springConfigTool = new SpringConfigTool();
		}
		return springConfigTool;
	}

	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		ac = applicationContext;
	}

	public synchronized static Object getBean(String beanName) {
		return ac.getBean(beanName);
	}
}
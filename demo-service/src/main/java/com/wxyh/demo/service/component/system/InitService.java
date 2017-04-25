package com.wxyh.demo.service.component.system;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.wxyh.demo.common.utils.ApplicationContextUtils;
/**
 * 初始化服务类<br>
 * 可用于设置applicationContext工具类；初始化缓存数据等。
 * @author wxyh
 *
 */
@Service
public class InitService implements ApplicationContextAware, InitializingBean {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(applicationContext, "ApplicationContext must not be null.");
		ApplicationContextUtils.setApplicationContext(applicationContext);
	}

	
	
}

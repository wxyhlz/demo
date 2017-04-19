package com.wxyh.demo.service.component.context;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
/**
 * 应用上下文服务类
 * @author wxyh
 *
 */
@Service
public class WebApplicationContextService implements ApplicationContextAware {

	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	public Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}
	
	public boolean containsBean(String name) {
		return this.applicationContext.containsBean(name);
	}
	
}

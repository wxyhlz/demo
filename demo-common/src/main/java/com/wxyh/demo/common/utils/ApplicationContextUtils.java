package com.wxyh.demo.common.utils;

import org.springframework.context.ApplicationContext;
/**
 * 应用上下文工具类
 * @author wxyh
 *
 */
public class ApplicationContextUtils {

	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ApplicationContextUtils.applicationContext = applicationContext;
	}
	
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	public static Object getBean(String beanName) {
		return applicationContext.getBean(beanName);
	}
	
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return applicationContext.getBean(beanName, clazz);
	}
	
	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}
	
}

package com.wxyh.demo.web.interceptors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.Assert;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;
import org.springframework.web.servlet.support.RequestContextUtils;
/**
 * 自定义拦截器
 * @author wxyh
 * 
 *
 */
@SuppressWarnings("deprecation")
public class MyInterceptor extends HandlerInterceptorAdapter {
	
	private static final Log LOGGER = LogFactory.getLog(MyInterceptor.class);

	private static final String[] EXCLUDE_PARAM_TYPES = 
			new String[]{"HttpServletRequest", "HttpServletResponse", "HttpSession"};
	
	private RequestMappingHandlerAdapter adapter;
	
	private final List<HandlerMethodArgumentResolver> argumentResolvers;
	
	private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
			new ConcurrentHashMap<MethodParameter, HandlerMethodArgumentResolver>(256);
	
	private final Map<Class<?>, Set<Method>> initBinderCache = new ConcurrentHashMap<Class<?>, Set<Method>>(64);

	/**
	 * 拦截器拦截uri配置
	 */
	private Properties configProps;
	
	@Autowired
	public MyInterceptor(RequestMappingHandlerAdapter adapter) {
		this.adapter = adapter;
		argumentResolvers = adapter.getArgumentResolvers();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 这里是通过配置文件实现的，也可以通过注解的方式实现
		if (!needHandle(request)) {
			return;
		}
		
		HandlerMethod hm = (HandlerMethod) handler;
		String actionRes = hm.getBeanType().getSimpleName() + "." + hm.getMethod().getName();
		LOGGER.info("业务处理...！actionRes=" + actionRes);
		
		// 获取参数MAP
		Map<String, Object> paramsValueMap = resolveParamsValue(request, response, hm);
		
		doBusiHandle(paramsValueMap);
		
	}
	
	
	/**
	 * 业务处理方法
	 * @param paramsValueMap
	 */
	protected void doBusiHandle(Map<String, Object> paramsValueMap) {
		// TODO Auto-generated method stub
		
	}

	private boolean needHandle(HttpServletRequest request) {
		String ctxPath = StringUtils.trimToEmpty(request.getContextPath()).replace("/", "");
		String uri = StringUtils.trimToEmpty(request.getRequestURI());
		uri = uri.substring(uri.indexOf(ctxPath) + ctxPath.length());
		return configProps != null && configProps.containsKey(uri);
	}

	/**
	 * 解析请求参数
	 * @param request
	 * @param response
	 * @param hm
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> resolveParamsValue(HttpServletRequest request, HttpServletResponse response, 
			HandlerMethod hm) throws Exception {
		Map<String, Object> paramsValueMap = new HashMap<String, Object>();
		ServletWebRequest webRequest = new ServletWebRequest(request, response);
		MethodParameter[] parameters = hm.getMethodParameters();
		for (MethodParameter parameter : parameters) {
			String paramType = parameter.getParameterType().getSimpleName();
			if (ArrayUtils.contains(EXCLUDE_PARAM_TYPES, paramType)) {
				continue;
			}
			HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter);
			Assert.notNull(resolver, "Unknown parameter type [" + parameter.getParameterType().getName() + "]");
			ModelAndViewContainer mavContainer = new ModelAndViewContainer();
			mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
			WebDataBinderFactory webDataBinderFactory = getDataBinderFactory(hm);
			Object value = resolver.resolveArgument(parameter, mavContainer, webRequest, webDataBinderFactory);
			paramsValueMap.put(parameter.getParameterName(), value);
		}
		return paramsValueMap;
	}
	
	private WebDataBinderFactory getDataBinderFactory(HandlerMethod handlerMethod) throws Exception {
		Class<?> handlerType = handlerMethod.getBeanType();
		Set<Method> methods = this.initBinderCache.get(handlerType);
		if (methods == null) {
			methods = HandlerMethodSelector.selectMethods(handlerType, RequestMappingHandlerAdapter.INIT_BINDER_METHODS);
			this.initBinderCache.put(handlerType, methods);
		}
		List<InvocableHandlerMethod> initBinderMethods = new ArrayList<InvocableHandlerMethod>();
		for (Method method : methods) {
			Object bean = handlerMethod.getBean();
			initBinderMethods.add(new InvocableHandlerMethod(bean, method));
		}
		return new ServletRequestDataBinderFactory(initBinderMethods, adapter.getWebBindingInitializer());
	}

	private HandlerMethodArgumentResolver getArgumentResolver(MethodParameter methodParameter) {
		HandlerMethodArgumentResolver resolver = this.argumentResolverCache.get(methodParameter);
		if (resolver == null) {
			for (HandlerMethodArgumentResolver argumentResolver : this.argumentResolvers) {
				if (argumentResolver.supportsParameter(methodParameter)) {
					resolver = argumentResolver;
					this.argumentResolverCache.put(methodParameter, resolver);
					break;
				}
			}
		}
		return resolver;
	}

	public void setConfigProps(Properties configProps) {
		this.configProps = configProps;
	}

}

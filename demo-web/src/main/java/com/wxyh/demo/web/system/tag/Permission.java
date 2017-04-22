package com.wxyh.demo.web.system.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 权限标签类
 * @author wxyh
 *
 */
public class Permission extends SimpleTagSupport {
	
	/**
	 * 资源ID
	 */
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (hasAuth()) {
			this.getJspBody().invoke(null);
		}
	}
	
	private boolean hasAuth() {
//		HttpServletRequest request = 
//				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		WebApplicationContext webAct = WebApplicationContextUtils
//				.getWebApplicationContext(request.getSession().getServletContext());

		return true;
	}
	
}

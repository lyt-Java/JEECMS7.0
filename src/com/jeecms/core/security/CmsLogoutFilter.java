package com.jeecms.core.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;

/**
 * CmsUserFilter
 */
public class CmsLogoutFilter extends LogoutFilter {
	/**
	 * 返回URL
	 */
	public static final String RETURN_URL = "returnUrl";

	protected String getRedirectUrl(ServletRequest req, ServletResponse resp,Subject subject) {
		HttpServletRequest request = (HttpServletRequest) req;
		String redirectUrl = request.getParameter(RETURN_URL);
		if (StringUtils.isBlank(redirectUrl)) {
			if (request.getRequestURI().startsWith(request.getContextPath() + getAdminPrefix())) {
				redirectUrl = getAdminLogin();
			} else {
				redirectUrl = getRedirectUrl();
			}
		}
		return redirectUrl;
	}

	
	private String adminPrefix;
	private String adminLogin;

	public String getAdminPrefix() {
		return adminPrefix;
	}

	public void setAdminPrefix(String adminPrefix) {
		this.adminPrefix = adminPrefix;
	}

	public String getAdminLogin() {
		return adminLogin;
	}

	public void setAdminLogin(String adminLogin) {
		this.adminLogin = adminLogin;
	}

	
}

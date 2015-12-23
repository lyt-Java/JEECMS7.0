package com.jeecms.cms.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.web.util.CmsUtils;

/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {
	public static final String SITE_COOKIE = "_site_id_cookie";
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
		CmsSite site = null;
		List<CmsSite> list = cmsSiteMng.getListFromCache();
		int size = list.size();
		if (size == 0) {
			throw new RuntimeException("no site record in database!");
		} else if (size == 1) {
			site = list.get(0);
		} else {
			String server = request.getServerName();
			String alias, redirect;
			for (CmsSite s : list) {
				// 检查域名
				if (s.getDomain().equals(server)) {
					site = s;
					break;
				}
				// 检查域名别名
				alias = s.getDomainAlias();
				if (!StringUtils.isBlank(alias)) {
					for (String a : StringUtils.split(alias, ',')) {
						if (a.equals(server)) {
							site = s;
							break;
						}
					}
				}
				// 检查重定向
				redirect = s.getDomainRedirect();
				if (!StringUtils.isBlank(redirect)) {
					for (String r : StringUtils.split(redirect, ',')) {
						if (r.equals(server)) {
							try {
								response.sendRedirect(s.getUrl());
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
							return false;
						}
					}
				}
			}
			if (site == null) {
				throw new SiteNotFoundException(server);
			}
		}
		
		CmsUtils.setSite(request, site);
		CmsThreadVariable.setSite(site);
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()|| subject.isRemembered()) {
			String username =  (String) subject.getPrincipal();
			CmsUser user = cmsUserMng.findByUsername(username);
			CmsUtils.setUser(request, user);
			// Site加入线程变量
			CmsThreadVariable.setUser(user);
		}
		createJsessionId(request, response, site);
		return true;
	}
	
	
	private void createJsessionId(HttpServletRequest request,HttpServletResponse response,CmsSite site){
		 String JSESSIONID = request.getSession().getId();//获取当前JSESSIONID （不管是从主域还是二级域访问产生）
		 Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
		 cookie.setDomain(site.getBaseDomain()); //关键在这里，将cookie设成主域名访问，确保不同域之间都能获取到该cookie的值，从而确保session统一
		 response.addCookie(cookie);  //将cookie返回到客户端
	}

	private CmsSiteMng cmsSiteMng;
	private CmsUserMng cmsUserMng;


	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}
}
package com.jeecms.cms.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.jeecms.common.web.CookieUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.security.CmsAuthorizingRealm;
import com.jeecms.core.web.util.CmsUtils;

/**
 * CMS上下文信息拦截器
 * 
 * 包括登录信息、权限信息、站点信息
 */
public class AdminContextInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = Logger.getLogger(AdminContextInterceptor.class);
	
	public static final String SITE_PARAM = "_site_id_param";
	public static final String SITE_COOKIE = "_site_id_cookie";
	public static final String SITE_PATH_PARAM = "path";
	public static final String PERMISSION_MODEL = "_permission_key";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 获得站点
		CmsSite oldSite=getByCookie(request);
		CmsSite site = getSite(request, response);
		CmsUtils.setSite(request, site);
		// Site加入线程变量
		CmsThreadVariable.setSite(site);
		// 获得用户
		CmsUser user = null;
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			String username =  (String) subject.getPrincipal();
			user = cmsUserMng.findByUsername(username);
		}
		// 此时用户可以为null
		CmsUtils.setUser(request, user);
		// User加入线程变量
		CmsThreadVariable.setUser(user);

		String uri = getURI(request);
		if (exclude(uri)) {
			return true;
		}
		//切换站点移除shiro缓存
		if(oldSite!=null&&!oldSite.equals(site)&&user!=null){
			authorizingRealm.removeUserAuthorizationInfoCache(user.getUsername().toString());
		}
		createJsessionId(request, response, site);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler, ModelAndView mav)
			throws Exception {
		CmsUser user = CmsUtils.getUser(request);
		CmsSite site=CmsUtils.getSite(request);
		// 不控制权限时perm为null，PermistionDirective标签将以此作为依据不处理权限问题。
		if (auth && user != null && !user.isSuper() && mav != null
				&& mav.getModelMap() != null && mav.getViewName() != null
				&& !mav.getViewName().startsWith("redirect:")) {
			mav.getModelMap().addAttribute(PERMISSION_MODEL, getUserPermission(site, user));
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// Sevlet容器有可能使用线程池，所以必须手动清空线程变量。
		CmsThreadVariable.removeUser();
		CmsThreadVariable.removeSite();
	}

	/**
	 * 按参数、cookie、域名、默认。
	 * 
	 * @param request
	 * @return 不会返回null，如果站点不存在，则抛出异常。
	 */
	private CmsSite getSite(HttpServletRequest request,
			HttpServletResponse response) {
		CmsSite site = getByParams(request, response);
		if (site == null) {
			site = getByCookie(request);
		}
		if (site == null) {
			site = getByDomain(request);
		}
		if (site == null) {
			site = getByDefault();
		}
		if (site == null) {
			throw new RuntimeException("cannot get site!");
		} else {
			return site;
		}
	}
	

	private CmsSite getByParams(HttpServletRequest request,
			HttpServletResponse response) {
		String p = request.getParameter(SITE_PARAM);
		if (!StringUtils.isBlank(p)) {
			try {
				Integer siteId = Integer.parseInt(p);
				CmsSite site = cmsSiteMng.findById(siteId);
				if (site != null) {
					// 若使用参数选择站点，则应该把站点保存至cookie中才好。
					CookieUtils.addCookie(request, response, SITE_COOKIE, site
							.getId().toString(), null, null);
					return site;
				}
			} catch (NumberFormatException e) {
				log.warn("param site id format exception", e);
			}
		}
		return null;
	}

	private CmsSite getByCookie(HttpServletRequest request) {
		Cookie cookie = CookieUtils.getCookie(request, SITE_COOKIE);
		if (cookie != null) {
			String v = cookie.getValue();
			if (!StringUtils.isBlank(v)) {
				try {
					Integer siteId = Integer.parseInt(v);
					return cmsSiteMng.findById(siteId);
				} catch (NumberFormatException e) {
					log.warn("cookie site id format exception", e);
				}
			}
		}
		return null;
	}
	
	private CmsSite getByDomain(HttpServletRequest request) {
		String domain = request.getServerName();
		if (!StringUtils.isBlank(domain)) {
			return cmsSiteMng.findByDomain(domain);
		}
		return null;
	}

	private CmsSite getByDefault() {
		List<CmsSite> list = cmsSiteMng.getListFromCache();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	private boolean exclude(String uri) {
		if (excludeUrls != null) {
			for (String exc : excludeUrls) {
				if (exc.equals(uri)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void createJsessionId(HttpServletRequest request,HttpServletResponse response,CmsSite site){
		 String JSESSIONID = request.getSession().getId();//获取当前JSESSIONID （不管是从主域还是二级域访问产生）
		 Cookie cookie = new Cookie("JSESSIONID", JSESSIONID);
		 cookie.setDomain(site.getBaseDomain()); //关键在这里，将cookie设成主域名访问，确保不同域之间都能获取到该cookie的值，从而确保session统一
		 response.addCookie(cookie);  //将cookie返回到客户端
	}
	
	/**
	 * 获得第三个路径分隔符的位置
	 * 
	 * @param request
	 * @throws IllegalStateException
	 *             访问路径错误，没有三(四)个'/'
	 */
	private static String getURI(HttpServletRequest request)
			throws IllegalStateException {
		UrlPathHelper helper = new UrlPathHelper();
		String uri = helper.getOriginatingRequestUri(request);
		String ctxPath = helper.getOriginatingContextPath(request);
		int start = 0, i = 0, count = 2;
		if (!StringUtils.isBlank(ctxPath)) {
			count++;
		}
		while (i < count && start != -1) {
			start = uri.indexOf('/', start + 1);
			i++;
		}
		
		if (start <= 0) {
			throw new IllegalStateException(
					"admin access path not like '/jeeadmin/jeecms/...' pattern: "
							+ uri);
		}
		return uri.substring(start);
	}
	
	
	private Set<String>getUserPermission(CmsSite site,CmsUser user){
		Set<String>viewPermissionSet=new HashSet<String>();
		Set<String> perms = user.getPerms(site.getId(),viewPermissionSet);
		Set<String> userPermission=new HashSet<String>();
		for(String perm:perms){
			perm="/"+perm;
			if(perm.contains(":")){
				perm=perm.replace(":", "/").replace("*", "");
			}
			userPermission.add(perm);
		}
		return userPermission;
	}
	private CmsSiteMng cmsSiteMng;
	private CmsUserMng cmsUserMng;
	private boolean auth = true;
	private String[] excludeUrls;
	
	@Autowired
	private CmsAuthorizingRealm authorizingRealm;

	@Autowired
	public void setCmsSiteMng(CmsSiteMng cmsSiteMng) {
		this.cmsSiteMng = cmsSiteMng;
	}

	@Autowired
	public void setCmsUserMng(CmsUserMng cmsUserMng) {
		this.cmsUserMng = cmsUserMng;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public void setExcludeUrls(String[] excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
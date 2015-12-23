package com.jeecms.cms.action.front;

import static com.jeecms.cms.Constants.TPLDIR_CSI;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsAdvertising;
import com.jeecms.cms.entity.assist.CmsAdvertisingSpace;
import com.jeecms.cms.manager.assist.CmsAdvertisingMng;
import com.jeecms.cms.manager.assist.CmsAdvertisingSpaceMng;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.FrontUtils;

/**
 * 广告Action
 */
@Controller
public class AdvertisingAct {
	// private static final Logger log = LoggerFactory
	// .getLogger(AdvertisingAct.class);

	public static final String TPL_AD = "tpl.advertising";
	public static final String TPL_ADSPACE = "tpl.adspace";

	@RequestMapping(value = "/ad.jspx")
	public String ad(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (id != null) {
			CmsAdvertising ad = cmsAdvertisingMng.findById(id);
			model.addAttribute("ad", ad);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, TPL_AD);
	}

	@RequestMapping(value = "/adspace.jspx")
	public String adspace(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		if (id != null) {
			CmsAdvertisingSpace adspace = cmsAdvertisingSpaceMng.findById(id);
			List<CmsAdvertising> adList = cmsAdvertisingMng.getList(id, true);
			model.addAttribute("adspace", adspace);
			model.addAttribute("adList", adList);
		}
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, TPL_ADSPACE);
	}

	@RequestMapping(value = "/ad_display.jspx")
	public void display(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.display(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	@RequestMapping(value = "/ad_click.jspx")
	public void click(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		if (id != null) {
			cmsAdvertisingMng.click(id);
		}
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}
	
	@RequestMapping(value = "/login_pass.jspx")
	public void login(String username, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		//@ToDo需要检测其他登录来源的有效性
		PrincipalCollection principals = new SimplePrincipalCollection(username, "MobileRealm");  
		Builder builder = new WebSubject.Builder( request,response);  
		builder.principals(principals);  
		builder.authenticated(true);  
		WebSubject subject = builder.buildWebSubject();  
		ThreadContext.bind(subject); 
	}


	@Autowired
	private CmsAdvertisingMng cmsAdvertisingMng;
	@Autowired
	private CmsAdvertisingSpaceMng cmsAdvertisingSpaceMng;
}

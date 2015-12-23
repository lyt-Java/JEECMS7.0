package com.jeecms.cms.action.admin;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsUserMenu;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.Content.ContentStatus;
import com.jeecms.cms.manager.assist.CmsUserMenuMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.web.AdminContextInterceptor;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class WelcomeAct {
	@RequiresPermissions("index")
	@RequestMapping("/index.do")
	public String index(HttpServletRequest request) {
		return "index";
	}

	@RequiresPermissions("map")
	@RequestMapping("/map.do")
	public String map() {
		return "map";
	}

	@RequiresPermissions("top")
	@RequestMapping("/top.do")
	public String top(HttpServletRequest request, ModelMap model) {
		// 需要获得站点列表
		List<CmsSite> siteList = cmsSiteMng.getList();
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		model.addAttribute("siteList", siteList);
		model.addAttribute("site", site);
		model.addAttribute("siteParam", AdminContextInterceptor.SITE_PARAM);
		model.addAttribute("user", user);
		return "top";
	}

	@RequiresPermissions("main")
	@RequestMapping("/main.do")
	public String main() {
		return "main";

	}

	@RequiresPermissions("left")
	@RequestMapping("/left.do")
	public String left(HttpServletRequest request, ModelMap model) {
		CmsUser user = CmsUtils.getUser(request);
		List<CmsUserMenu>menus=userMenuMng.getList(user.getId(),10);
		model.addAttribute("menus", menus);
		return "left";
	}
	
	@RequiresPermissions("right")
	@RequestMapping("/right.do")
	public String right(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String version = site.getConfig().getVersion();
		Properties props = System.getProperties();
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;
		//最新10条待审内容
		List<Content>contents=(List<Content>) contentMng.getPageByRight(null, null, user.getId(), null, false, false, ContentStatus.prepared, user.getCheckStep(site.getId()), site.getId(), null, user.getId(), 0, 1, 10).getList();
		List<Content>newcontents=(List<Content>)contentMng.getPageByRight(null, null,  user.getId(), null, false, false, ContentStatus.checked,  user.getCheckStep(site.getId()), site.getId(), null,user.getId(), 0, 1, 10).getList();
		model.addAttribute("props", props);
		model.addAttribute("freeMemoery", freeMemoery);
		model.addAttribute("totalMemory", totalMemory);
		model.addAttribute("usedMemory", usedMemory);
		model.addAttribute("maxMemory", maxMemory);
		model.addAttribute("useableMemory", useableMemory);
		model.addAttribute("version", version);
		model.addAttribute("user", user);
		model.addAttribute("site", site);
		model.addAttribute("contents", contents);
		model.addAttribute("newcontents", newcontents);
		return "right";
	}
	

	@Autowired
	private CmsSiteMng cmsSiteMng;
	@Autowired
	private ContentMng contentMng;
	@Autowired
	private CmsUserMenuMng userMenuMng;
}

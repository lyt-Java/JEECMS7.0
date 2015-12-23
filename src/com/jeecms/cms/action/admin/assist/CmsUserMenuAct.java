package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsUserMenu;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.cms.manager.assist.CmsUserMenuMng;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.WebErrors;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.ResponseUtils;

@Controller
public class CmsUserMenuAct {
	private static final Logger log = LoggerFactory.getLogger(CmsUserMenuAct.class);
	
	@RequiresPermissions("menu:v_list")
	@RequestMapping("/menu/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		CmsUser user= CmsUtils.getUser(request);
		Pagination pagination = manager.getPage(user.getId(),cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "menu/list";
	}

	@RequiresPermissions("menu:v_ajax_edit")
	@RequestMapping("/menu/v_ajax_edit.do")
	public void ajaxEdit(Integer id, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		CmsUserMenu menu=manager.findById(id);
		if(menu!=null){
			object.put("id", menu.getId());
			object.put("name", menu.getName());
			object.put("priority", menu.getPriority());
			object.put("url", menu.getUrl());
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@RequiresPermissions("menu:o_save")
	@RequestMapping("/menu/o_save.do")
	public String save(CmsUserMenu bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setUser(CmsUtils.getUser(request));
		bean = manager.save(bean);
		log.info("save CmsUserMenu id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("menu:o_update")
	@RequestMapping("/menu/o_update.do")
	public String update(CmsUserMenu bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsUserMenu id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("menu:o_delete")
	@RequestMapping("/menu/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUserMenu[] beans = manager.deleteByIds(ids);
		for (CmsUserMenu bean : beans) {
			log.info("delete CmsUserMenu id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(CmsUserMenu bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsUserMenu entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsUserMenu.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsUserMenuMng manager;
}
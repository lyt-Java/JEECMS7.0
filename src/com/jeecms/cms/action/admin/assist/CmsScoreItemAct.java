package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsScoreItem;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.cms.manager.assist.CmsScoreGroupMng;
import com.jeecms.cms.manager.assist.CmsScoreItemMng;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.WebErrors;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;

@Controller
public class CmsScoreItemAct {
	private static final Logger log = LoggerFactory.getLogger(CmsScoreItemAct.class);

	@RequiresPermissions("scoreitem:v_list")
	@RequestMapping("/scoreitem/v_list.do")
	public String list(Integer groupId,Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(groupId,cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		model.addAttribute("groupId",groupId);
		return "scoreitem/list";
	}

	@RequiresPermissions("scoreitem:v_add")
	@RequestMapping("/scoreitem/v_add.do")
	public String add(Integer groupId,ModelMap model) {
		model.addAttribute("groupId",groupId);
		return "scoreitem/add";
	}

	@RequiresPermissions("scoreitem:v_edit")
	@RequestMapping("/scoreitem/v_edit.do")
	public String edit(Integer id, Integer groupId,Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("item", manager.findById(id));
		model.addAttribute("groupId",groupId);
		model.addAttribute("pageNo",pageNo);
		return "scoreitem/edit";
	}

	@RequiresPermissions("scoreitem:o_save")
	@RequestMapping("/scoreitem/o_save.do")
	public String save(CmsScoreItem bean,Integer groupId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setGroup(scoreGroupMng.findById(groupId));
		bean = manager.save(bean);
		log.info("save CmsScoreItem id={}", bean.getId());
		return "redirect:v_list.do?groupId="+groupId;
	}

	@RequiresPermissions("scoreitem:o_update")
	@RequestMapping("/scoreitem/o_update.do")
	public String update(CmsScoreItem bean,Integer groupId, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsScoreItem id={}.", bean.getId());
		return list(groupId,pageNo, request, model);
	}

	@RequiresPermissions("scoreitem:o_delete")
	@RequestMapping("/scoreitem/o_delete.do")
	public String delete(Integer groupId,Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsScoreItem[] beans = manager.deleteByIds(ids);
		for (CmsScoreItem bean : beans) {
			log.info("delete CmsScoreItem id={}", bean.getId());
		}
		return list(groupId,pageNo, request, model);
	}

	private WebErrors validateSave(CmsScoreItem bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}
	
	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
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
		CmsScoreItem entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsScoreItem.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsScoreItemMng manager;
	@Autowired
	private CmsScoreGroupMng scoreGroupMng;
}
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

import com.jeecms.cms.entity.assist.CmsScoreGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.cms.manager.assist.CmsScoreGroupMng;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.WebErrors;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;

@Controller
public class CmsScoreGroupAct {
	private static final Logger log = LoggerFactory.getLogger(CmsScoreGroupAct.class);

	@RequiresPermissions("scoregroup:v_list")
	@RequestMapping("/scoregroup/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "scoregroup/list";
	}

	@RequiresPermissions("scoregroup:v_add")
	@RequestMapping("/scoregroup/v_add.do")
	public String add(ModelMap model) {
		return "scoregroup/add";
	}

	@RequiresPermissions("scoregroup:v_edit")
	@RequestMapping("/scoregroup/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("group", manager.findById(id));
		model.addAttribute("pageNo",pageNo);
		return "scoregroup/edit";
	}

	@RequiresPermissions("scoregroup:o_save")
	@RequestMapping("/scoregroup/o_save.do")
	public String save(CmsScoreGroup bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setSite(CmsUtils.getSite(request));
		bean = manager.save(bean);
		log.info("save CmsScoreGroup id={}", bean.getId());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("scoregroup:o_update")
	@RequestMapping("/scoregroup/o_update.do")
	public String update(CmsScoreGroup bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsScoreGroup id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("scoregroup:o_delete")
	@RequestMapping("/scoregroup/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsScoreGroup[] beans = manager.deleteByIds(ids);
		for (CmsScoreGroup bean : beans) {
			log.info("delete CmsScoreGroup id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(CmsScoreGroup bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
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
		CmsScoreGroup entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsScoreGroup.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsScoreGroup.class, id);
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsScoreGroupMng manager;
}
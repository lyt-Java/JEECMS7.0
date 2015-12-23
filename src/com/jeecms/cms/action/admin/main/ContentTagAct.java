package com.jeecms.cms.action.admin.main;

import static com.jeecms.common.page.SimplePage.cpn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.ContentTag;
import com.jeecms.cms.manager.main.ContentTagMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class ContentTagAct {
	private static final Logger log = LoggerFactory.getLogger(ContentTagAct.class);

	@RequiresPermissions("tag:v_list")
	@RequestMapping("/tag/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryName = RequestUtils.getQueryParam(request, "queryName");
		Pagination pagination = manager.getPage(queryName, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		if (!StringUtils.isBlank(queryName)) {
			model.addAttribute("queryName", queryName);
		}
		return "tag/list";
	}

	@RequiresPermissions("tag:v_add")
	@RequestMapping("/tag/v_add.do")
	public String add(ModelMap model) {
		return "tag/add";
	}

	@RequiresPermissions("tag:v_edit")
	@RequestMapping("/tag/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("contentTag", manager.findById(id));
		String queryName = RequestUtils.getQueryParam(request, "queryName");
		if (!StringUtils.isBlank(queryName)) {
			model.addAttribute("queryName", queryName);
		}
		return "tag/edit";
	}
	
	@RequiresPermissions("tag:v_ajax_edit")
	@RequestMapping("/tag/v_ajax_edit.do")
	public void ajaxEdit(Integer id, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		ContentTag tag=manager.findById(id);
		String queryName = RequestUtils.getQueryParam(request, "queryName");
		if (!StringUtils.isBlank(queryName)) {
			model.addAttribute("queryName", queryName);
		}
		if(tag!=null){
			object.put("id", tag.getId());
			object.put("name", tag.getName());
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@RequiresPermissions("tag:o_save")
	@RequestMapping("/tag/o_save.do")
	public String save(ContentTag bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save ContentTag id={}", bean.getId());
		cmsLogMng.operating(request, "contentTag.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("tag:o_update")
	@RequestMapping("/tag/o_update.do")
	public String update(ContentTag bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update ContentTag id={}.", bean.getId());
		cmsLogMng.operating(request, "contentTag.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("tag:o_delete")
	@RequestMapping("/tag/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		ContentTag[] beans = manager.deleteByIds(ids);
		for (ContentTag bean : beans) {
			log.info("delete ContentTag id={}", bean.getId());
			cmsLogMng.operating(request, "contentTag.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(ContentTag bean, HttpServletRequest request) {
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
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		ContentTag entity = manager.findById(id);
		if (errors.ifNotExist(entity, ContentTag.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private ContentTagMng manager;
}
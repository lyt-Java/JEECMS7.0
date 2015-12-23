package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jeecms.cms.entity.assist.CmsOrigin;
import com.jeecms.cms.manager.assist.CmsOriginMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsOriginAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsOriginAct.class);
	
	@RequiresPermissions("origin:v_list")
	@RequestMapping("/origin/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryName = RequestUtils.getQueryParam(request, "queryName");
		Pagination pagination = manager.getPage(cpn(pageNo),CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		if (!StringUtils.isBlank(queryName)) {
			model.addAttribute("queryName", queryName);
		}
		return "origin/list";
	}

	@RequiresPermissions("origin:v_ajax_list")
	@RequestMapping("/origin/v_ajax_list.do")
	public void ajaxList(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		Map<String,String>originMap=new HashMap<String, String>();
		String origin=RequestUtils.getQueryParam(request, "term");
		List<CmsOrigin>origins=manager.getList(origin);
		for(CmsOrigin ori:origins){
			originMap.put(ori.getName(), ori.getName());
		}
		object.put("origin", originMap);
		ResponseUtils.renderJson(response, object.get("origin").toString());
	}
	
	@RequiresPermissions("origin:v_ajax_edit")
	@RequestMapping("/origin/v_ajax_edit.do")
	public void ajaxEdit(Integer id, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		CmsOrigin tag=manager.findById(id);
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

	@RequiresPermissions("origin:o_save")
	@RequestMapping("/origin/o_save.do")
	public String save(CmsOrigin bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setRefCount(0);
		bean = manager.save(bean);
		log.info("save CmsOrigin id={}", bean.getId());
		cmsLogMng.operating(request, "CmsOrigin.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("origin:o_update")
	@RequestMapping("/origin/o_update.do")
	public String update(CmsOrigin bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsOrigin id={}.", bean.getId());
		cmsLogMng.operating(request, "CmsOrigin.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("origin:o_delete")
	@RequestMapping("/origin/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsOrigin[] beans = manager.deleteByIds(ids);
		for (CmsOrigin bean : beans) {
			log.info("delete CmsOrigin id={}", bean.getId());
			cmsLogMng.operating(request, "CmsOrigin.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(CmsOrigin bean, HttpServletRequest request) {
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
		CmsOrigin entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsOrigin.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsOriginMng manager;
	@Autowired
	private CmsLogMng cmsLogMng;
}
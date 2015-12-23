package com.jeecms.cms.action.admin.assist;

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

import com.jeecms.cms.entity.assist.CmsSearchWords;
import com.jeecms.cms.manager.assist.CmsSearchWordsMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.util.ChineseCharToEn;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsSearchWordsAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsSearchWordsAct.class);
	
	@RequiresPermissions("searchwords:v_list")
	@RequestMapping("/searchwords/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryName = RequestUtils.getQueryParam(request, "queryName");
		Pagination pagination = manager.getPage(cpn(pageNo),CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		if (!StringUtils.isBlank(queryName)) {
			model.addAttribute("queryName", queryName);
		}
		return "searchwords/list";
	}
	
	@RequiresPermissions("searchwords:v_ajax_edit")
	@RequestMapping("/searchwords/v_ajax_edit.do")
	public void ajaxEdit(Integer id, HttpServletRequest request,HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject object = new JSONObject();
		CmsSearchWords word=manager.findById(id);
		if(word!=null){
			object.put("id", word.getId());
			object.put("name", word.getName());
			object.put("priority", word.getPriority());
		}
		ResponseUtils.renderJson(response, object.toString());
	}

	@RequiresPermissions("searchwords:o_save")
	@RequestMapping("/searchwords/o_save.do")
	public String save(CmsSearchWords bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setHitCount(0);
		bean.setNameInitial(ChineseCharToEn.getAllFirstLetter(bean.getName()));
		bean = manager.save(bean);
		log.info("save CmsSearchWords id={}", bean.getId());
		cmsLogMng.operating(request, "CmsSearchWords.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("searchwords:o_update")
	@RequestMapping("/searchwords/o_update.do")
	public String update(CmsSearchWords bean, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.setNameInitial(ChineseCharToEn.getAllFirstLetter(bean.getName()));
		bean = manager.update(bean);
		log.info("update CmsSearchWords id={}.", bean.getId());
		cmsLogMng.operating(request, "CmsSearchWords.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("searchwords:o_delete")
	@RequestMapping("/searchwords/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSearchWords[] beans = manager.deleteByIds(ids);
		for (CmsSearchWords bean : beans) {
			log.info("delete CmsSearchWords id={}", bean.getId());
			cmsLogMng.operating(request, "CmsSearchWords.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(CmsSearchWords bean, HttpServletRequest request) {
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
		CmsSearchWords entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsSearchWords.class, id)) {
			return true;
		}
		return false;
	}
	
	@Autowired
	private CmsSearchWordsMng manager;
	@Autowired
	private CmsLogMng cmsLogMng;
}
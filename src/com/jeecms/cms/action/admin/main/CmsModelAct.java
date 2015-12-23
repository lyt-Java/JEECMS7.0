package com.jeecms.cms.action.admin.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.CmsModel;
import com.jeecms.cms.manager.main.CmsModelMng;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;

@Controller
public class CmsModelAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsModelAct.class);

	@RequiresPermissions("model:v_list")
	@RequestMapping("/model/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<CmsModel> list = manager.getList(true,null);
		model.addAttribute("list", list);
		return "model/list";
	}

	@RequiresPermissions("model:v_add")
	@RequestMapping("/model/v_add.do")
	public String add(ModelMap model) {
		return "model/add";
	}

	@RequiresPermissions("model:v_edit")
	@RequestMapping("/model/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("cmsModel", manager.findById(id));
		return "model/edit";
	}

	@RequiresPermissions("model:o_save")
	@RequestMapping("/model/o_save.do")
	public String save(CmsModel bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save CmsModel id={}", bean.getId());
		cmsLogMng.operating(request, "cmsModel.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("model:o_update")
	@RequestMapping("/model/o_update.do")
	public String update(CmsModel bean, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean);
		log.info("update CmsModel id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsModel.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequiresPermissions("model:o_delete")
	@RequestMapping("/model/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsModel[] beans = manager.deleteByIds(ids);
		for (CmsModel bean : beans) {
			log.info("delete CmsModel id={}", bean.getId());
			cmsLogMng.operating(request, "cmsModel.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequiresPermissions("model:o_priority")
	@RequestMapping("/model/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority,
			Boolean[] disabled, Integer defId, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, disabled, defId,
				request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority, disabled, defId);
		model.addAttribute("message", "global.success");
		return list(request, model);
	}

	private WebErrors validateSave(CmsModel bean, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (vldExist(id, errors)) {
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private WebErrors validatePriority(Integer[] wids, Integer[] priority,
			Boolean[] disabled, Integer defId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(wids, "wids")) {
			return errors;
		}
		if (errors.ifEmpty(priority, "priority")) {
			return errors;
		}
		if (wids.length != priority.length || wids.length != disabled.length) {
			String s = "wids length not equals priority length or disabled length";
			errors.addErrorString(s);
			return errors;
		}
		for (int i = 0, len = wids.length; i < len; i++) {
			if (vldExist(wids[i], errors)) {
				return errors;
			}
			if (priority[i] == null) {
				priority[i] = 0;
			}
			if (disabled[i] == null) {
				disabled[i] = false;
			}
		}
		if (vldExist(defId, errors)) {
			return errors;
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsModel entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsModel.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsModelMng manager;
}
package com.jeecms.cms.action.admin.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.core.entity.Ftp;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.FtpMng;
import com.jeecms.core.web.WebErrors;

@Controller
public class FtpAct {
	private static final Logger log = LoggerFactory.getLogger(FtpAct.class);

	@RequiresPermissions("ftp:v_list")
	@RequestMapping("/ftp/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		List<Ftp> list = manager.getList();
		model.addAttribute("list", list);
		return "ftp/list";
	}

	@RequiresPermissions("ftp:v_add")
	@RequestMapping("/ftp/v_add.do")
	public String add(ModelMap model) {
		return "ftp/add";
	}

	@RequiresPermissions("ftp:v_edit")
	@RequestMapping("/ftp/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		model.addAttribute("ftp", manager.findById(id));
		return "ftp/edit";
	}

	@RequiresPermissions("ftp:o_save")
	@RequestMapping("/ftp/o_save.do")
	public String save(Ftp bean, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean);
		log.info("save Ftp id={}", bean.getId());
		cmsLogMng.operating(request, "ftp.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("ftp:o_update")
	@RequestMapping("/ftp/o_update.do")
	public String update(Ftp bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		//留空不修改
		if(StringUtils.isBlank(bean.getPassword())){	
			bean.setPassword(manager.findById(bean.getId()).getPassword());
		}
		bean = manager.update(bean);
		log.info("update Ftp id={}.", bean.getId());
		cmsLogMng.operating(request, "ftp.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("ftp:o_delete")
	@RequestMapping("/ftp/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Ftp[] beans = manager.deleteByIds(ids);
		for (Ftp bean : beans) {
			log.info("delete Ftp id={}", bean.getId());
			cmsLogMng.operating(request, "ftp.log.delete", "id=" + bean.getId()
					+ ";name=" + bean.getName());
		}
		return list(pageNo, request, model);
	}

	private WebErrors validateSave(Ftp bean, HttpServletRequest request) {
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

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Ftp entity = manager.findById(id);
		if (errors.ifNotExist(entity, Ftp.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private FtpMng manager;
}
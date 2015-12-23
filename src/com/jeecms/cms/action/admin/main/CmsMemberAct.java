package com.jeecms.cms.action.admin.main;

import static com.jeecms.common.page.SimplePage.cpn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsConfigItem;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.CmsUserExt;
import com.jeecms.core.manager.CmsConfigItemMng;
import com.jeecms.core.manager.CmsGroupMng;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsMemberAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsMemberAct.class);

	@RequiresPermissions("member:v_list")
	@RequestMapping("/member/v_list.do")
	public String list(String queryUsername, String queryEmail,
			Integer queryGroupId, Boolean queryDisabled, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(queryUsername, queryEmail,
				null, queryGroupId, queryDisabled, false, null, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);

		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);

		return "member/list";
	}

	@RequiresPermissions("member:v_add")
	@RequestMapping("/member/v_add.do")
	public String add(ModelMap model,HttpServletRequest request) {
		CmsSite site=CmsUtils.getSite(request);
		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		model.addAttribute("registerItems", registerItems);
		model.addAttribute("groupList", groupList);
		return "member/add";
	}

	@RequiresPermissions("member:v_edit")
	@RequestMapping("/member/v_edit.do")
	public String edit(Integer id, Integer queryGroupId, Boolean queryDisabled,
			HttpServletRequest request, ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		CmsSite site=CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser user=manager.findById(id);
		List<CmsGroup> groupList = cmsGroupMng.getList();
		List<CmsConfigItem>registerItems=cmsConfigItemMng.getList(site.getConfig().getId(), CmsConfigItem.CATEGORY_REGISTER);
		List<String>userAttrValues=new ArrayList<String>();
		for(CmsConfigItem item:registerItems){
			userAttrValues.add(user.getAttr().get(item.getField()));
		}
		model.addAttribute("queryUsername", queryUsername);
		model.addAttribute("queryEmail", queryEmail);
		model.addAttribute("queryGroupId", queryGroupId);
		model.addAttribute("queryDisabled", queryDisabled);
		model.addAttribute("groupList", groupList);
		model.addAttribute("cmsMember", user);
		model.addAttribute("registerItems", registerItems);
		model.addAttribute("userAttrValues", userAttrValues);
		return "member/edit";
	}

	@RequiresPermissions("member:o_save")
	@RequestMapping("/member/o_save.do")
	public String save(CmsUser bean, CmsUserExt ext, String username,
			String email, String password, Integer groupId,Integer grain,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		String ip = RequestUtils.getIpAddr(request);
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		bean = manager.registerMember(username, email, password, ip, groupId,grain,false,ext,attrs);
		log.info("save CmsMember id={}", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.save", "id=" + bean.getId()
				+ ";username=" + bean.getUsername());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("member:o_update")
	@RequestMapping("/member/o_update.do")
	public String update(Integer id, String email, String password,
			Boolean disabled, CmsUserExt ext, Integer groupId,Integer grain,
			String queryUsername, String queryEmail, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		CmsUser bean = manager.updateMember(id, email, password, disabled, ext,groupId,grain,attrs);
		log.info("update CmsMember id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsMember.log.update", "id="
				+ bean.getId() + ";username=" + bean.getUsername());

		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequiresPermissions("member:o_delete")
	@RequestMapping("/member/o_delete.do")
	public String delete(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser[] beans = manager.deleteByIds(ids);
		for (CmsUser bean : beans) {
			log.info("delete CmsMember id={}", bean.getId());
			cmsLogMng.operating(request, "cmsMember.log.delete", "id="
					+ bean.getId() + ";username=" + bean.getUsername());
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}
	
	@RequiresPermissions("member:o_check")
	@RequestMapping("/member/o_check.do")
	public String check(Integer[] ids, Integer queryGroupId,
			Boolean queryDisabled, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		String queryUsername = RequestUtils.getQueryParam(request,
				"queryUsername");
		String queryEmail = RequestUtils.getQueryParam(request, "queryEmail");
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		for(Integer id:ids){
			CmsUser user=manager.findById(id);
			user.setDisabled(false);
			manager.updateUser(user);
			log.info("check CmsMember id={}", user.getId());
			cmsLogMng.operating(request, "cmsMember.log.delete", "id="
					+ user.getId() + ";username=" + user.getUsername());
		}
		return list(queryUsername, queryEmail, queryGroupId, queryDisabled,
				pageNo, request, model);
	}

	@RequiresPermissions("member:v_check_username")
	@RequestMapping(value = "/member/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = manager.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}

	private WebErrors validateSave(CmsUser bean, HttpServletRequest request) {
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
		// TODO 验证是否为管理员，管理员不允许修改。
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if(!errors.ifEmpty(ids, "ids")){
			for (Integer id : ids) {
				vldExist(id, errors);
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsUser entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsUser.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsUserMng manager;
	@Autowired
	private CmsConfigItemMng cmsConfigItemMng;
}
package com.jeecms.cms.action.admin.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsGroupMng;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.web.WebErrors;

@Controller
public class CmsGroupAct {
	private static final Logger log = LoggerFactory.getLogger(CmsGroupAct.class);

	@RequiresPermissions("group:v_list")
	@RequestMapping("/group/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		List<CmsGroup> list = manager.getList();
		model.addAttribute("list", list);
		return "group/list";
	}

	@RequiresPermissions("group:v_add")
	@RequestMapping("/group/v_add.do")
	public String add(HttpServletRequest request,ModelMap model) {
		List<CmsSite> siteList = cmsSiteMng.getList();
		model.addAttribute("siteList", siteList);
		return "group/add";
	}

	@RequiresPermissions("group:v_edit")
	@RequestMapping("/group/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsGroup group=manager.findById(id);
		List<CmsSite> siteList = cmsSiteMng.getList();
		Map<String, Set<Integer>>viewChannelsMap=new HashMap<String, Set<Integer>>();
		Map<String, Set<Integer>>contriChannelsMap=new HashMap<String, Set<Integer>>();
		for(CmsSite site:siteList){
			viewChannelsMap.put(site.getId().toString(), group.getViewChannelIds(site.getId()));
			contriChannelsMap.put(site.getId().toString(), group.getContriChannelIds(site.getId()));
		}
		model.addAttribute("siteList", siteList);
		model.addAttribute("cmsGroup", group);
		model.addAttribute("viewGroupIds", viewChannelsMap);
		model.addAttribute("contriGroupIds",contriChannelsMap);
		return "group/edit";
	}

	@RequiresPermissions("group:o_save")
	@RequestMapping("/group/o_save.do")
	public String save(CmsGroup bean,  Integer[] viewGroupIds, Integer[] contriGroupIds,
		HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.save(bean,viewGroupIds,contriGroupIds);
		log.info("save CmsGroup id={}", bean.getId());
		cmsLogMng.operating(request, "cmsGroup.log.save", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("group:o_update")
	@RequestMapping("/group/o_update.do")
	public String update(CmsGroup bean, Integer[] viewGroupIds, Integer[] contriGroupIds,
			HttpServletRequest request,ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean,viewGroupIds,contriGroupIds);
		log.info("update CmsGroup id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsGroup.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequiresPermissions("group:o_delete")
	@RequestMapping("/group/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsGroup[] beans = manager.deleteByIds(ids);
		for (CmsGroup bean : beans) {
			log.info("delete CmsGroup id={}", bean.getId());
			cmsLogMng.operating(request, "cmsGroup.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequiresPermissions("group:o_priority")
	@RequestMapping("/group/o_priority.do")
	public String priority(Integer[] wids, Integer[] priority,
			Integer regDefId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority);
		manager.updateRegDef(regDefId);
		model.addAttribute("message", "global.success");
		return list(request, model);
	}
	
	@RequiresPermissions("group:v_channels_add")
	@RequestMapping(value = "/group/v_channels_add.do")
	public String channelsAdd(Integer siteId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return channelsAddJson(siteId, request, response, model);
	}

	@RequiresPermissions("group:v_channels_edit")
	@RequestMapping(value = "/group/v_channels_edit.do")
	public String channelsEdit(Integer groupId, Integer siteId,Integer type,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		return channelsEditJson(groupId, siteId,type, request, response, model);
	}
	private String channelsAddJson(Integer siteId,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		model.addAttribute("channelList", channelList);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "group/channels_add";
	}

	private String channelsEditJson(Integer groupId, Integer siteId,Integer type,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		List<Channel> channelList = channelMng.getTopList(siteId, false);
		CmsGroup group = manager.findById(groupId);
		model.addAttribute("channelList", channelList);
		if(type.equals(1)){
			model.addAttribute("channelIds", group.getViewChannelIds(siteId));
		}else{
			model.addAttribute("channelIds", group.getContriChannelIds(siteId));
		}
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "group/channels_edit";
	}

	private WebErrors validateSave(CmsGroup bean, HttpServletRequest request) {
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
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldExist(id, errors);
		}
		return errors;
	}

	private WebErrors validatePriority(Integer[] wids, Integer[] priority,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (errors.ifEmpty(wids, "wids")) {
			return errors;
		}
		if (errors.ifEmpty(priority, "priority")) {
			return errors;
		}
		if (wids.length != priority.length) {
			errors.addErrorString("wids length not equals priority length");
			return errors;
		}
		for (int i = 0, len = wids.length; i < len; i++) {
			if (vldExist(wids[i], errors)) {
				return errors;
			}
			if (priority[i] == null) {
				priority[i] = 0;
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsGroup entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsGroup.class, id)) {
			return true;
		}
		return false;
	}

	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsGroupMng manager;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	protected CmsSiteMng cmsSiteMng;
}
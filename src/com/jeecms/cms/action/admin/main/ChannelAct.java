package com.jeecms.cms.action.admin.main;

import java.util.Collection;
import java.util.HashMap;
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

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ChannelExt;
import com.jeecms.cms.entity.main.ChannelTxt;
import com.jeecms.cms.entity.main.CmsModel;
import com.jeecms.cms.entity.main.CmsModelItem;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsModelItemMng;
import com.jeecms.cms.manager.main.CmsModelMng;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.manager.CmsGroupMng;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.tpl.TplManager;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.CoreUtils;

@Controller
public class ChannelAct {
	private static final Logger log = LoggerFactory.getLogger(ChannelAct.class);
	
	@RequiresPermissions("channel:channel_main")
	@RequestMapping("/channel/channel_main.do")
	public String channelMain(ModelMap model) {
		return "channel/channel_main";
	}
	
	@RequiresPermissions("channel:v_left")
	@RequestMapping("/channel/v_left.do")
	public String left() {
		return "channel/left";
	}

	@RequiresPermissions("channel:v_tree")
	@RequestMapping(value = "/channel/v_tree.do")
	public String tree(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		log.debug("tree path={}", root);
		boolean isRoot;
		// jquery treeview的根请求为root=source
		if (StringUtils.isBlank(root) || "source".equals(root)) {
			isRoot = true;
		} else {
			isRoot = false;
		}
		model.addAttribute("isRoot", isRoot);
		WebErrors errors = validateTree(root, request);
		if (errors.hasErrors()) {
			log.error(errors.getErrors().get(0));
			ResponseUtils.renderJson(response, "[]");
			return null;
		}
		List<Channel> list;
		if (isRoot) {
			CmsSite site = CmsUtils.getSite(request);
			list = manager.getTopList(site.getId(), false);
		} else {
			Integer rootId = Integer.valueOf(root);
			list = manager.getChildList(rootId, false);
		}
		model.addAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "channel/tree";
	}

	@RequiresPermissions("channel:v_list")
	@RequestMapping("/channel/v_list.do")
	public String list(Integer root, HttpServletRequest request, ModelMap model) {
		List<Channel> list;
		if (root == null) {
			list = manager.getTopList(CmsUtils.getSiteId(request), false);
		} else {
			list = manager.getChildList(root, false);
		}
		model.addAttribute("modelList", cmsModelMng.getList(false,null));
		model.addAttribute("root", root);
		model.addAttribute("list", list);
		return "channel/list";
	}

	@RequiresPermissions("channel:v_add")
	@RequestMapping("/channel/v_add.do")
	public String add(Integer root, Integer modelId,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Channel parent = null;
		if (root != null) {
			parent = manager.findById(root);
			model.addAttribute("parent", parent);
			model.addAttribute("root", root);
		}
		// 模型
		CmsModel m = cmsModelMng.findById(modelId);
		// 栏目模板列表
		List<String> channelTplList = getTplChannel(site, m, null);
		// 内容模板列表
		List<String> contentTplList = getTplContent(site, m, null);
		List<CmsModel> models=cmsModelMng.getList(false,true);
		Map<String,List<String>>modelTplMap=new HashMap<String, List<String>>();
		for(CmsModel tempModel:models){
			List<String> modelTplList = getTplContent(site, tempModel, null);
			modelTplMap.put(tempModel.getId().toString(), modelTplList);
		}
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(modelId, true,
				false);
		List<CmsGroup> groupList = cmsGroupMng.getList();
		// 浏览会员组列表
		List<CmsGroup> viewGroups = groupList;
		// 投稿会员组列表
		Collection<CmsGroup> contriGroups;
		if (parent != null) {
			contriGroups = parent.getContriGroups();
		} else {
			contriGroups = groupList;
		}
		// 管理员列表
		Collection<CmsUser> users;
		if (parent != null) {
			users = parent.getUsers();
		} else {
			users = cmsUserMng.getAdminList(site.getId(), false, false, null);
		}
		model.addAttribute("channelTplList", channelTplList);
		model.addAttribute("contentTplList", contentTplList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("viewGroups", viewGroups);
		model.addAttribute("contriGroups", contriGroups);
		model.addAttribute("contriGroupIds", CmsGroup.fetchIds(contriGroups));
		model.addAttribute("users", users);
		model.addAttribute("userIds", CmsUser.fetchIds(users));
		model.addAttribute("model", m);
		model.addAttribute("models", models);
		model.addAttribute("modelTplMap", modelTplMap);
		return "channel/add";
	}

	@RequiresPermissions("channel:v_edit")
	@RequestMapping("/channel/v_edit.do")
	public String edit(Integer id, Integer root, HttpServletRequest request,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if (root != null) {
			model.addAttribute("root", root);
		}
		// 栏目
		Channel channel = manager.findById(id);
		// 当前模板，去除基本路径
		int tplPathLength = site.getTplPath().length();
		String tplChannel = channel.getTplChannel();
		if (!StringUtils.isBlank(tplChannel)) {
			tplChannel = tplChannel.substring(tplPathLength);
		}
		String tplContent = channel.getTplContent();
		if (!StringUtils.isBlank(tplContent)) {
			tplContent = tplContent.substring(tplPathLength);
		}
		// 父栏目
		Channel parent = channel.getParent();
		// 模型
		CmsModel m = channel.getModel();
		// 栏目列表
		List<Channel> topList = manager.getTopList(site.getId(), false);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				channel, false);

		// 栏目模板列表
		List<String> channelTplList = getTplChannel(site, m, channel.getTplChannel());
		// 内容模板列表
		List<String> contentTplList = getTplContent(site, m, channel.getTplContent());
		//模型列表和各个模型模板
		List<CmsModel> models=cmsModelMng.getList(false,true);
		Map<String,List<String>>modelTplMap=new HashMap<String, List<String>>();
		for(CmsModel tempModel:models){
			List<String> modelTplList = getTplContent(site, tempModel, null);
			modelTplMap.put(tempModel.getId().toString(), modelTplList);
		}
		List<CmsGroup> groupList = cmsGroupMng.getList();
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), true,
				false);
		// 浏览会员组列表、浏览会员组IDS
		List<CmsGroup> viewGroups = groupList;
		Integer[] viewGroupIds = CmsGroup.fetchIds(channel.getViewGroups());
		// 投稿会员组列表
		Collection<CmsGroup> contriGroups;
		if (parent != null) {
			contriGroups = parent.getContriGroups();
		} else {
			contriGroups = groupList;
		}
		// 投稿会员组IDS
		Integer[] contriGroupIds = CmsGroup.fetchIds(channel.getContriGroups());
		// 管理员列表
		Collection<CmsUser> users;
		if (parent != null) {
			users = parent.getUsers();
		} else {
			users = cmsUserMng.getAdminList(site.getId(), false, false, null);
		}
		// 管理员IDS
		Integer[] userIds = channel.getUserIds();
		model.addAttribute("channelList", channelList);
		model.addAttribute("modelList", cmsModelMng.getList(false,null));
		model.addAttribute("tplChannel", tplChannel);
		model.addAttribute("tplContent", tplContent);
		model.addAttribute("channelTplList", channelTplList);
		model.addAttribute("contentTplList", contentTplList);
		model.addAttribute("itemList", itemList);
		model.addAttribute("viewGroups", viewGroups);
		model.addAttribute("viewGroupIds", viewGroupIds);
		model.addAttribute("contriGroups", contriGroups);
		model.addAttribute("contriGroupIds", contriGroupIds);
		model.addAttribute("users", users);
		model.addAttribute("userIds", userIds);
		model.addAttribute("channel", channel);
		model.addAttribute("model", m);
		model.addAttribute("models", models);
		model.addAttribute("modelTplMap", modelTplMap);
		return "channel/edit";
	}

	@RequiresPermissions("channel:o_save")
	@RequestMapping("/channel/o_save.do")
	public String save(Integer root, Channel bean, ChannelExt ext,
			ChannelTxt txt, Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer modelId,
			Integer[] modelIds,String[] tpls, HttpServletRequest request,ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		// 加上模板前缀
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplChannel())) {
			ext.setTplChannel(tplPath + ext.getTplChannel());
		}
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		if(tpls!=null&&tpls.length>0){
			for(int t=0;t<tpls.length;t++){
				if (!StringUtils.isBlank(tpls[t])) {
					tpls[t]=tplPath+tpls[t];
				}
			}
		}
		bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
		bean = manager.save(bean, ext, txt, viewGroupIds, contriGroupIds,
				userIds, CmsUtils.getSiteId(request), root, modelId,modelIds,tpls);
		log.info("save Channel id={}, name={}", bean.getId(), bean.getName());
		cmsLogMng.operating(request, "channel.log.save", "id=" + bean.getId()
				+ ";title=" + bean.getTitle());
		model.addAttribute("root", root);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("channel:o_update")
	@RequestMapping("/channel/o_update.do")
	public String update(Integer root, Channel bean, ChannelExt ext,
			ChannelTxt txt, Integer[] viewGroupIds, Integer[] contriGroupIds,
			Integer[] userIds, Integer parentId,
			Integer[] modelIds,String[] tpls,HttpServletRequest request,ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		// 加上模板前缀
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplChannel())) {
			ext.setTplChannel(tplPath + ext.getTplChannel());
		}
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		if(tpls!=null&&tpls.length>0){
			for(int t=0;t<tpls.length;t++){
				if (!StringUtils.isBlank(tpls[t])&&!tpls[t].startsWith(tplPath)) {
					tpls[t]=tplPath+tpls[t];
				}
			}
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		bean = manager.update(bean, ext, txt, viewGroupIds, contriGroupIds,
				userIds, parentId, attr,modelIds,tpls);
		log.info("update Channel id={}.", bean.getId());
		cmsLogMng.operating(request, "channel.log.update", "id=" + bean.getId()
				+ ";name=" + bean.getName());
		return list(root, request, model);
	}

	@RequiresPermissions("channel:o_delete")
	@RequestMapping("/channel/o_delete.do")
	public String delete(Integer root, Integer[] ids,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Channel[] beans = manager.deleteByIds(ids);
		for (Channel bean : beans) {
			log.info("delete Channel id={}", bean.getId());
			cmsLogMng.operating(request, "channel.log.delete", "id="
					+ bean.getId() + ";title=" + bean.getTitle());
		}
		return list(root, request, model);
	}

	@RequiresPermissions("channel:o_priority")
	@RequestMapping("/channel/o_priority.do")
	public String priority(Integer root, Integer[] wids, Integer[] priority,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validatePriority(wids, priority, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		manager.updatePriority(wids, priority);
		model.addAttribute("message", "global.success");
		return list(root, request, model);
	}
	
	@RequiresPermissions("channel:v_check_path")
	@RequestMapping(value = "/channel/v_check_path.do")
	public void checkPath(Integer cid,String path,HttpServletRequest request, HttpServletResponse response) {
		String pass;
		if (StringUtils.isBlank(path)) {
			pass = "false";
		} else {
			Channel c = manager.findByPath(path, CmsUtils.getSiteId(request));
			if(c==null){
				pass="true" ;
			}else{
				if(c.getId().equals(cid)){
					pass= "true";
				}else{
					pass="false";
				}
			}
		}
		ResponseUtils.renderJson(response, pass);
	}

	private List<String> getTplChannel(CmsSite site, CmsModel model, String tpl) {
		String sol = site.getSolutionPath();
		List<String> tplList = tplManager.getNameListByPrefix(model.getTplChannel(sol, false));
		return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
	}

	private List<String> getTplContent(CmsSite site, CmsModel model, String tpl) {
		String sol = site.getSolutionPath();
		List<String> tplList = tplManager.getNameListByPrefix(model
				.getTplContent(sol, false));
		return CoreUtils.tplTrim(tplList, site.getTplPath(), tpl);
	}

	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateSave(Channel bean, HttpServletRequest request) {
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
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			if (vldExist(id, site.getId(), errors)) {
				return errors;
			}
			// 检查是否可以删除
			String code = manager.checkDelete(id);
			if (code != null) {
				errors.addErrorCode(code);
				return errors;
			}
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Channel entity = manager.findById(id);
		if (errors.ifNotExist(entity, Channel.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(Channel.class, id);
			return true;
		}
		return false;
	}

	private WebErrors validatePriority(Integer[] wids, Integer[] priority,
			HttpServletRequest request) {
		CmsSite site = CmsUtils.getSite(request);
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
			if (vldExist(wids[i], site.getId(), errors)) {
				return errors;
			}
			if (priority[i] == null) {
				priority[i] = 0;
			}
		}
		return errors;
	}

	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemMng cmsModelItemMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private TplManager tplManager;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private ChannelMng manager;
}
package com.jeecms.cms.action.admin.main;

import static com.jeecms.common.page.SimplePage.cpn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.CmsModel;
import com.jeecms.cms.entity.main.CmsModelItem;
import com.jeecms.cms.entity.main.CmsTopic;
import com.jeecms.cms.entity.main.Content;
import com.jeecms.cms.entity.main.ContentCheck;
import com.jeecms.cms.entity.main.ContentExt;
import com.jeecms.cms.entity.main.ContentTxt;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.entity.main.Content.ContentStatus;
import com.jeecms.cms.manager.assist.CmsFileMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.CmsModelItemMng;
import com.jeecms.cms.manager.main.CmsModelMng;
import com.jeecms.cms.manager.main.CmsTopicMng;
import com.jeecms.cms.manager.main.ContentMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.cms.service.ImageSvc;
import com.jeecms.cms.service.WeiXinSvc;
import com.jeecms.cms.staticpage.exception.ContentNotCheckedException;
import com.jeecms.cms.staticpage.exception.GeneratedZeroStaticPageException;
import com.jeecms.cms.staticpage.exception.StaticPageNotOpenException;
import com.jeecms.cms.staticpage.exception.TemplateNotFoundException;
import com.jeecms.cms.staticpage.exception.TemplateParseException;
import com.jeecms.common.image.ImageUtils;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.upload.FileRepository;
import com.jeecms.common.util.StrUtils;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.common.web.springmvc.MessageResolver;
import com.jeecms.core.entity.CmsGroup;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;
import com.jeecms.core.entity.Ftp;
import com.jeecms.core.manager.CmsGroupMng;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.manager.CmsSiteMng;
import com.jeecms.core.manager.CmsUserMng;
import com.jeecms.core.manager.DbFileMng;
import com.jeecms.core.tpl.TplManager;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;
import com.jeecms.core.web.util.CoreUtils;

@Controller
public class ContentAct{
	private static final Logger log = LoggerFactory.getLogger(ContentAct.class);

	@RequiresPermissions("content:v_left")
	@RequestMapping("/content/v_left.do")
	public String left(String source, ModelMap model) {
		model.addAttribute("source", source);
		return "content/left";
	}

	/**
	 * 栏目导航
	 * 
	 * @param root
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("content:v_tree")
	@RequestMapping(value = "/content/v_tree.do")
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
		Integer siteId = CmsUtils.getSiteId(request);
		Integer userId = CmsUtils.getUserId(request);
		List<Channel> list;
		if (isRoot) {
			list = channelMng.getTopListByRigth(userId, siteId, true);
		} else {
			list = channelMng.getChildListByRight(userId, siteId, Integer
					.parseInt(root), true);
		}
		
		model.addAttribute("list", list);
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		return "content/tree";
	}

	/**
	 * 副栏目树
	 * 
	 * @param root
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("content:v_tree_channels")
	@RequestMapping(value = "/content/v_tree_channels.do")
	public String treeChannels(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		tree(root, request, response, model);
		return "content/tree_channels";
	}

	@RequiresPermissions("content:v_list")
	@RequestMapping("/content/v_list.do")
	public String list(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		long time = System.currentTimeMillis();
		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		queryTitle = StringUtils.trim(queryTitle);
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		queryInputUsername = StringUtils.trim(queryInputUsername);
		if (queryTopLevel == null) {
			queryTopLevel = false;
		}
		if (queryRecommend == null) {
			queryRecommend = false;
		}
		if (queryOrderBy == null) {
			queryOrderBy = 4;
		}
		ContentStatus status;
		if (!StringUtils.isBlank(queryStatus)) {
			status = ContentStatus.valueOf(queryStatus);
		} else {
			status = ContentStatus.all;
		}
		Integer queryInputUserId = null;
		if (!StringUtils.isBlank(queryInputUsername)) {
			CmsUser u = cmsUserMng.findByUsername(queryInputUsername);
			if (u != null) {
				queryInputUserId = u.getId();
			} else {
				// 用户名不存在，清空。
				queryInputUsername = null;
			}
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		byte currStep = user.getCheckStep(siteId);
		Pagination p = manager.getPageByRight(queryTitle, queryTypeId,user.getId(),
				queryInputUserId, queryTopLevel, queryRecommend, status, user
						.getCheckStep(siteId), siteId, cid, userId,
				queryOrderBy, cpn(pageNo), CookieUtils.getPageSize(request));
		List<ContentType> typeList = contentTypeMng.getList(true);
		List<CmsModel>models=cmsModelMng.getList(false, true);
		if(cid!=null){
			Channel c=channelMng.findById(cid);
			models=c.getModels(models);
		}
		model.addAttribute("pagination", p);
		model.addAttribute("cid", cid);
		model.addAttribute("typeList", typeList);
		model.addAttribute("currStep", currStep);
		model.addAttribute("site", site);
		model.addAttribute("models", models);
		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);
		time = System.currentTimeMillis() - time;
		return "content/list";
	}

	@RequiresPermissions("content:v_add")
	@RequestMapping("/content/v_add.do")
	public String add(Integer cid,Integer modelId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateAdd(cid,modelId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		Integer userId = user.getId();
		// 栏目
		Channel c;
		if (cid != null) {
			c = channelMng.findById(cid);
		} else {
			c = null;
		}
		// 模型
		CmsModel m;
		if(modelId==null){
			if (c != null) {
				m = c.getModel();
			} else {
				m = cmsModelMng.getDefModel();
				// TODO m==null给出错误提示
				if (m == null) {
					throw new RuntimeException("default model not found!");
				}
			}
		}else{
			m=cmsModelMng.findById(modelId);
		}
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), false,
				false);
		// 栏目列表
		List<Channel> channelList;
		Set<Channel> rights;
		if (user.getUserSite(siteId).getAllChannel()) {
			// 拥有所有栏目权限
			rights = null;
		} else {
			rights = user.getChannels(siteId);
		}
		if (c != null) {
			channelList = c.getListForSelect(rights, true);
		} else {
			List<Channel> topList = channelMng.getTopListByRigth(userId,siteId, true);
			channelList = Channel.getListForSelect(topList, rights, true);
		}

		// 专题列表
		List<CmsTopic> topicList;
		if (c != null) {
			topicList = cmsTopicMng.getListByChannel(c.getId());
		} else {
			topicList = new ArrayList<CmsTopic>();
		}
		// 内容模板列表
		List<String> tplList = getTplContent(site, m, null);
		// 会员组列表
		List<CmsGroup> groupList = cmsGroupMng.getList();
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		model.addAttribute("site",CmsUtils.getSite(request));
		model.addAttribute("model", m);
		model.addAttribute("itemList", itemList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("topicList", topicList);
		model.addAttribute("tplList", tplList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("typeList", typeList);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		if (c != null) {
			model.addAttribute("channel", c);
		}
		return "content/add";
	}

	@RequiresPermissions("content:v_view")
	@RequestMapping("/content/v_view.do")
	public String view(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo, Integer cid, Integer id,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		byte currStep = user.getCheckStep(site.getId());
		Content content = manager.findById(id);

		model.addAttribute("content", content);
		model.addAttribute("currStep", currStep);
		model.addAttribute("site", site);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);
		return "content/view";
	}

	@RequiresPermissions("content:v_edit")
	@RequestMapping("/content/v_edit.do")
	public String edit(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo, Integer cid, Integer id,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsUser user = CmsUtils.getUser(request);
		// 内容
		Content content = manager.findById(id);
		// 栏目
		Channel channel = content.getChannel();
		// 模型
		CmsModel m=content.getModel();
		// 模型项列表
		List<CmsModelItem> itemList = cmsModelItemMng.getList(m.getId(), false,
				false);
		// 栏目列表
		Set<Channel> rights;
		if (user.getUserSite(siteId).getAllChannel()) {
			// 拥有所有栏目权限
			rights = null;
		} else {
			rights = user.getChannels(siteId);
		}

		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, rights,
				true);

		// 专题列表
		List<CmsTopic> topicList = cmsTopicMng
				.getListByChannel(channel.getId());
		Set<CmsTopic> topics = content.getTopics();
		for (CmsTopic t : topics) {
			if (!topicList.contains(t)) {
				topicList.add(t);
			}
		}
		Integer[] topicIds = CmsTopic.fetchIds(content.getTopics());
		// 内容模板列表
		List<String> tplList = getTplContent(site, m, content.getTplContent());
		// 会员组列表
		List<CmsGroup> groupList = cmsGroupMng.getList();
		Integer[] groupIds = CmsGroup.fetchIds(content.getViewGroups());
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 当前模板，去除基本路径
		int tplPathLength = site.getTplPath().length();
		String tplContent = content.getTplContent();
		if (!StringUtils.isBlank(tplContent)) {
			tplContent = tplContent.substring(tplPathLength);
		}
		model.addAttribute("site",CmsUtils.getSite(request));
		model.addAttribute("content", content);
		model.addAttribute("channel", channel);
		model.addAttribute("model", m);
		model.addAttribute("itemList", itemList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("topicList", topicList);
		model.addAttribute("topicIds", topicIds);
		model.addAttribute("tplList", tplList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("groupIds", groupIds);
		model.addAttribute("typeList", typeList);
		model.addAttribute("tplContent", tplContent);
		if (cid != null) {
			model.addAttribute("cid", cid);
		}

		String queryTitle = RequestUtils.getQueryParam(request, "queryTitle");
		String queryInputUsername = RequestUtils.getQueryParam(request,
				"queryInputUsername");
		addAttibuteForQuery(model, queryTitle, queryInputUsername, queryStatus,
				queryTypeId, queryTopLevel, queryRecommend, queryOrderBy,
				pageNo);

		return "content/edit";
	}

	@RequiresPermissions("content:o_save")
	@RequestMapping("/content/o_save.do")
	public String save(Content bean, ContentExt ext, ContentTxt txt,
			Boolean copyimg,Integer sendType,Integer selectImg,String weixinImg,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths, String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid, Integer modelId,HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, channelId, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		bean.setAttr(RequestUtils.getRequestMap(request, "attr_"));
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
				.getMessage(request, "content.tagStr.split"));
		if(txt!=null&&copyimg!=null&&copyimg){
			txt=copyContentTxtImg(txt, site);
		}
		bean = manager.save(bean, ext, txt,channelIds, topicIds, viewGroupIds,
				tagArr, attachmentPaths, attachmentNames, attachmentFilenames,
				picPaths, picDescs, channelId, typeId, draft,false, user, false);
		//微信消息发送
		weiXinSvc.sendMessage(sendType, selectImg, weixinImg, bean, ext, txt);
		//处理附件
		fileMng.updateFileByPaths(attachmentPaths,picPaths,ext.getMediaPath(),ext.getTitleImg(),ext.getTypeImg(),ext.getContentImg(),true,bean);
		log.info("save Content id={}", bean.getId());
		cmsLogMng.operating(request, "content.log.save", "id=" + bean.getId()
				+ ";title=" + bean.getTitle());
		if (cid != null) {
			model.addAttribute("cid", cid);
		}
		model.addAttribute("message", "global.success");
		return add(cid,modelId, request, model);
	}

	@RequiresPermissions("content:o_update")
	@RequestMapping("/content/o_update.do")
	public String update(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Content bean, ContentExt ext, ContentTxt txt,
			Boolean copyimg,Integer sendType,Integer selectImg,String weixinImg,
			Integer[] channelIds, Integer[] topicIds, Integer[] viewGroupIds,
			String[] attachmentPaths, String[] attachmentNames,
			String[] attachmentFilenames, String[] picPaths,String[] picDescs,
			Integer channelId, Integer typeId, String tagStr, Boolean draft,
			Integer cid,String[]oldattachmentPaths,String[] oldpicPaths,
			String oldTitleImg,String oldContentImg,String oldTypeImg,
			Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		// 加上模板前缀
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String tplPath = site.getTplPath();
		if (!StringUtils.isBlank(ext.getTplContent())) {
			ext.setTplContent(tplPath + ext.getTplContent());
		}
		String[] tagArr = StrUtils.splitAndTrim(tagStr, ",", MessageResolver
				.getMessage(request, "content.tagStr.split"));
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		if(txt!=null&&copyimg!=null&&copyimg){
			txt=copyContentTxtImg(txt, site);
		}
		bean = manager.update(bean, ext, txt,tagArr, channelIds, topicIds,
				viewGroupIds, attachmentPaths, attachmentNames,
				attachmentFilenames, picPaths, picDescs, attr, channelId,
				typeId, draft, user, false);
		//微信消息发送
		weiXinSvc.sendMessage(sendType, selectImg, weixinImg, bean, ext, txt);
		//处理之前的附件有效性
		fileMng.updateFileByPaths(oldattachmentPaths,oldpicPaths,null,oldTitleImg,oldTypeImg,oldContentImg,false,bean);
		//处理更新后的附件有效性
		fileMng.updateFileByPaths(attachmentPaths,picPaths,ext.getMediaPath(),ext.getTitleImg(),ext.getTypeImg(),ext.getContentImg(),true,bean);
		log.info("update Content id={}.", bean.getId());
		cmsLogMng.operating(request, "content.log.update", "id=" + bean.getId()
				+ ";title=" + bean.getTitle());
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequiresPermissions("content:o_delete")
	@RequestMapping("/content/o_delete.do")
	public String delete(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Content[] beans;
		// 是否开启回收站
		if (site.getResycleOn()) {
			beans = manager.cycle(ids);
			for (Content bean : beans) {
				log.info("delete to cycle, Content id={}", bean.getId());
			}
		} else {
			for(Integer id:ids){
				Content c=manager.findById(id);
				//处理附件
				manager.updateFileByContent(c, false);
			}
			beans = manager.deleteByIds(ids);
			for (Content bean : beans) {
				log.info("delete Content id={}", bean.getId());
				cmsLogMng.operating(request, "content.log.delete", "id="
						+ bean.getId() + ";title=" + bean.getTitle());
			}
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}
	
	@RequiresPermissions("content:o_check")
	@RequestMapping("/content/o_check.do")
	public String check(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateCheck(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser user = CmsUtils.getUser(request);
		Content[] beans = manager.check(ids, user);
		for (Content bean : beans) {
			log.info("check Content id={}", bean.getId());
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}
	
	@RequiresPermissions("content:o_check")
	@RequestMapping("/content/o_ajax_check.do")
	public void ajaxCheck(Integer[] ids, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws JSONException {
		WebErrors errors = validateCheck(ids, request);
		JSONObject json=new JSONObject();
		if (errors.hasErrors()) {
			json.put("error", errors.getErrors().get(0));
			json.put("success", false);
		}
		CmsUser user = CmsUtils.getUser(request);
		manager.check(ids, user);
		json.put("success", true);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions("content:o_static")
	@RequestMapping("/content/o_static.do")
	public String contentStatic(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateStatic(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		try {
			Content[] beans = manager.contentStatic(ids);
			for (Content bean : beans) {
				log.info("static Content id={}", bean.getId());
			}
			model.addAttribute("message", errors.getMessage(
					"content.staticGenerated", beans.length));
		} catch (TemplateNotFoundException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (TemplateParseException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (GeneratedZeroStaticPageException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(), e
					.getGenerated()));
		} catch (StaticPageNotOpenException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		} catch (ContentNotCheckedException e) {
			model.addAttribute("message", errors.getMessage(e.getMessage(),
					new Object[] { e.getErrorTitle(), e.getGenerated() }));
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequiresPermissions("content:o_reject")
	@RequestMapping("/content/o_reject.do")
	public String reject(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Byte rejectStep,
			String rejectOpinion, Integer pageNo, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateReject(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsUser user = CmsUtils.getUser(request);
		Content[] beans = manager.reject(ids, user,rejectStep, rejectOpinion);
		for (Content bean : beans) {
			log.info("reject Content id={}", bean.getId());
		}
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}
	
	@RequiresPermissions("content:o_reject")
	@RequestMapping("/content/o_ajax_reject.do")
	public void ajaxReject(Integer[] ids,Byte rejectStep, String rejectOpinion, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws JSONException {
		WebErrors errors = validateReject(ids, request);
		JSONObject json=new JSONObject();
		if (errors.hasErrors()) {
			json.put("error", errors.getErrors().get(0));
			json.put("success", false);
		}
		CmsUser user = CmsUtils.getUser(request);
		manager.reject(ids, user, rejectStep,rejectOpinion);
		json.put("success", true);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("content:v_tree_radio")
	@RequestMapping(value = "/content/v_tree_radio.do")
	public String move_tree(String root, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		tree(root, request, response, model);
		return "content/tree_move";
	}
	
	@RequiresPermissions("content:o_move")
	@RequestMapping("/content/o_move.do")
		public void move(Integer contentIds[], Integer channelId,HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		Boolean pass = true;
		if (contentIds != null && channelId != null) {
			Channel channel=channelMng.findById(channelId);
			for(Integer contentId:contentIds){
				Content bean=manager.findById(contentId);
				if(bean!=null&&channel!=null){
					bean.removeSelfAddToChannels(channelMng.findById(channelId));
					bean.setChannel(channel);
					manager.update(bean);
				}
			}
		}
		json.put("pass", pass);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("content:o_copy")
	@RequestMapping("/content/o_copy.do")
		public void copy(Integer contentIds[],Integer channelId,Integer siteId,HttpServletRequest request,HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		CmsUser user=CmsUtils.getUser(request);
		Boolean pass = true;
		if (contentIds != null) {
			for(Integer contentId:contentIds){
				Content bean=manager.findById(contentId);
				Content beanCopy= new Content();
				ContentExt extCopy=new ContentExt();
				ContentTxt txtCopy=new ContentTxt();
				beanCopy=bean.cloneWithoutSet();
				beanCopy.setChannel(channelMng.findById(channelId));
				//复制到别站
				if(siteId!=null){
					beanCopy.setSite(siteMng.findById(siteId));
				}
				boolean draft=false;
				if(bean.getStatus().equals(ContentCheck.DRAFT)){
					draft=true;
				}
				BeanUtils.copyProperties(bean.getContentExt(), extCopy);
				if(bean.getContentTxt()!=null){
					BeanUtils.copyProperties(bean.getContentTxt(), txtCopy);
				}
				manager.save(beanCopy, extCopy, txtCopy, null,
						bean.getTopicIds(), bean.getViewGroupIds(), bean.getTagArray(), bean.getAttachmentPaths(), bean.getAttachmentNames(),
						bean.getAttachmentFileNames(), bean.getPicPaths(), bean.getPicDescs(), channelId, bean.getType().getId(), draft,false, user, false);
			}
		}
		json.put("pass", pass);
		ResponseUtils.renderJson(response, json.toString());
	}
	/**
	 * 引用
	 * @param contentIds
	 * @param channelId
	 */
	@RequiresPermissions("content:o_refer")
	@RequestMapping("/content/o_refer.do")
		public void refer(Integer contentIds[],Integer channelId,HttpServletRequest request,HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		CmsUser user=CmsUtils.getUser(request);
		Boolean pass = true;
		if(user==null){
			ResponseUtils.renderJson(response, "false");
		}
		if (contentIds != null) {
			for(Integer contentId:contentIds){
				manager.updateByChannelIds(contentId, new Integer[]{channelId});
			}
		}else{
			ResponseUtils.renderJson(response, "false");
		}
		json.put("pass", pass);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("content:o_priority")
	@RequestMapping("/content/o_priority.do")
	public String priority(Integer[] wids, Byte[] topLevel,
			String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		for(int i=0;i<wids.length;i++){
			Content c=manager.findById(wids[i]);
			c.setTopLevel(topLevel[i]);
			manager.update(c);
		}
		log.info("update CmsFriendlink priority.");
		return list(queryStatus, queryTypeId, queryTopLevel, queryRecommend, queryOrderBy, cid, pageNo, request, model);
	}
	
	/**
	 * 推送至专题
	 * @param contentIds
	 * @param topicIds
	 */
	@RequiresPermissions("content:o_send_to_topic")
	@RequestMapping("/content/o_send_to_topic.do")
		public void refer(Integer contentIds[],Integer[] topicIds,HttpServletRequest request,HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		CmsUser user=CmsUtils.getUser(request);
		Boolean pass = true;
		if(user==null){
			ResponseUtils.renderJson(response, "false");
		}
		if (contentIds != null) {
			for(Integer contentId:contentIds){
				manager.addContentToTopics(contentId,topicIds);
			}
		}else{
			ResponseUtils.renderJson(response, "false");
		}
		json.put("pass", pass);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions("content:o_upload_attachment")
	@RequestMapping("/content/o_upload_attachment.do")
	public String uploadAttachment(
			@RequestParam(value = "attachmentFile", required = false) MultipartFile file,
			String attachmentNum, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user= CmsUtils.getUser(request);
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		WebErrors errors = validateUpload(file,request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "content/attachment_iframe";
		}
		// TODO 检查允许上传的后缀
		try {
			String fileUrl;
			if (site.getConfig().getUploadToDb()) {
				String dbFilePath = site.getConfig().getDbFileUri();
				fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext, file
						.getInputStream());
				// 加上访问地址
				fileUrl = request.getContextPath() + dbFilePath + fileUrl;
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
						.getInputStream());
				// 加上url前缀
				fileUrl = ftpUrl + fileUrl;
			} else {
				String ctx = request.getContextPath();
				fileUrl = fileRepository.storeByExt(site.getUploadPath(), ext,
						file);
				// 加上部署路径
				fileUrl = ctx + fileUrl;
			}
			cmsUserMng.updateUploadSize(user.getId(), Integer.parseInt(String.valueOf(file.getSize()/1024)));
			fileMng.saveFileByPath(fileUrl, origName, false);
			model.addAttribute("attachmentPath", fileUrl);
			model.addAttribute("attachmentName", origName);
			model.addAttribute("attachmentNum", attachmentNum);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "content/attachment_iframe";
	}

	@RequiresPermissions("content:o_upload_media")
	@RequestMapping("/content/o_upload_media.do")
	public String uploadMedia(
			@RequestParam(value = "mediaFile", required = false) MultipartFile file,
			String filename, HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		CmsUser user = CmsUtils.getUser(request);
		String origName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(origName).toLowerCase(
				Locale.ENGLISH);
		WebErrors errors = validateUpload(file, request);
		if (errors.hasErrors()) {
			model.addAttribute("error", errors.getErrors().get(0));
			return "content/media_iframe";
		}
		// TODO 检查允许上传的后缀
		try {
			String fileUrl;
			if (site.getConfig().getUploadToDb()) {
				String dbFilePath = site.getConfig().getDbFileUri();
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(dbFilePath.length());
					fileUrl = dbFileMng.storeByFilename(filename, file
							.getInputStream());
				} else {
					fileUrl = dbFileMng.storeByExt(site.getUploadPath(), ext,
							file.getInputStream());
					// 加上访问地址
					fileUrl = request.getContextPath() + dbFilePath + fileUrl;
				}
			} else if (site.getUploadFtp() != null) {
				Ftp ftp = site.getUploadFtp();
				String ftpUrl = ftp.getUrl();
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(ftpUrl.length());
					fileUrl = ftp.storeByFilename(filename, file
							.getInputStream());
				} else {
					fileUrl = ftp.storeByExt(site.getUploadPath(), ext, file
							.getInputStream());
					// 加上url前缀
					fileUrl = ftpUrl + fileUrl;
				}
			} else {
				String ctx = request.getContextPath();
				if (!StringUtils.isBlank(filename)
						&& FilenameUtils.getExtension(filename).equals(ext)) {
					filename = filename.substring(ctx.length());
					fileUrl = fileRepository.storeByFilename(filename, file);
				} else {
					fileUrl = fileRepository.storeByExt(site.getUploadPath(),
							ext, file);
				}
				// 加上部署路径
				fileUrl = ctx + fileUrl;
			}
			cmsUserMng.updateUploadSize(user.getId(), Integer.parseInt(String.valueOf(file.getSize()/1024)));
			if(fileMng.findByPath(fileUrl)!=null){
				fileMng.saveFileByPath(fileUrl, origName, false);
			}
			model.addAttribute("mediaPath", fileUrl);
			model.addAttribute("mediaExt", ext);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		} catch (IOException e) {
			model.addAttribute("error", e.getMessage());
			log.error("upload file error!", e);
		}
		return "content/media_iframe";
	}
	
	@RequiresPermissions("content_cycle:v_list")
	@RequestMapping("/content_cycle/v_list.do")
	public String cycleList(Integer queryTypeId, Boolean queryTopLevel,
			Boolean queryRecommend, Integer queryOrderBy, Integer cid,
			Integer pageNo, HttpServletRequest request, ModelMap model) {
		list(ContentStatus.recycle.toString(), queryTypeId, queryTopLevel,
				queryRecommend, queryOrderBy, cid, pageNo, request, model);
		return "content/cycle_list";
	}

	@RequiresPermissions("content_cycle:o_recycle")
	@RequestMapping("/content_cycle/o_recycle.do")
	public String cycleRecycle(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Content[] beans = manager.recycle(ids);
		for (Content bean : beans) {
			log.info("delete Content id={}", bean.getId());
		}
		return cycleList(queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequiresPermissions("content_cycle:o_delete")
	@RequestMapping("/content_cycle/o_delete.do")
	public String cycleDelete(String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer[] ids, Integer cid, Integer pageNo,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		for(Integer id:ids){
			Content c=manager.findById(id);
			//处理附件
			manager.updateFileByContent(c, false);
		}
		Content[] beans = manager.deleteByIds(ids);
		for (Content bean : beans) {
			log.info("delete Content id={}", bean.getId());
		}
		return cycleList(queryTypeId, queryTopLevel, queryRecommend,
				queryOrderBy, cid, pageNo, request, model);
	}

	@RequiresPermissions("content:o_generateTags")
	@RequestMapping("/content/o_generateTags.do")
	public void generateTags(String title,HttpServletResponse response) throws JSONException {
		JSONObject json = new JSONObject();
		String tags="";
		if(StringUtils.isNotBlank(title)){
			tags=StrUtils.getKeywords(title, true);
		}
		json.put("tags", tags);
		ResponseUtils.renderJson(response, json.toString());
	}
	
	@RequiresPermissions("content:rank_list")
	@RequestMapping(value = "/content/rank_list.do")
	public String contentRankList(Integer orderBy,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("pageNo", cpn(pageNo));
		model.addAttribute("pageSize", CookieUtils.getPageSize(request));
		model.addAttribute("site", CmsUtils.getSite(request));
		return "content/ranklist";
	}

	private void addAttibuteForQuery(ModelMap model, String queryTitle,
			String queryInputUsername, String queryStatus, Integer queryTypeId,
			Boolean queryTopLevel, Boolean queryRecommend,
			Integer queryOrderBy, Integer pageNo) {
		if (!StringUtils.isBlank(queryTitle)) {
			model.addAttribute("queryTitle", queryTitle);
		}
		if (!StringUtils.isBlank(queryInputUsername)) {
			model.addAttribute("queryInputUsername", queryInputUsername);
		}
		if (queryTypeId != null) {
			model.addAttribute("queryTypeId", queryTypeId);
		}
		if (queryStatus != null) {
			model.addAttribute("queryStatus", queryStatus);
		}
		if (queryTopLevel != null) {
			model.addAttribute("queryTopLevel", queryTopLevel);
		}
		if (queryRecommend != null) {
			model.addAttribute("queryRecommend", queryRecommend);
		}
		if (queryOrderBy != null) {
			model.addAttribute("queryOrderBy", queryOrderBy);
		}
		if (pageNo != null) {
			model.addAttribute("pageNo", pageNo);
		}
	}

	private List<String> getTplContent(CmsSite site, CmsModel model, String tpl) {
		String sol = site.getSolutionPath();
		String tplPath = site.getTplPath();
		List<String> tplList = tplManager.getNameListByPrefix(model
				.getTplContent(sol, false));
		tplList = CoreUtils.tplTrim(tplList, tplPath, tpl);
		return tplList;
	}
	
	private WebErrors validateTree(String path, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		// if (errors.ifBlank(path, "path", 255)) {
		// return errors;
		// }
		return errors;
	}

	private WebErrors validateAdd(Integer cid,Integer modelId, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		if (cid == null) {
			return errors;
		}
		Channel c = channelMng.findById(cid);
		if (errors.ifNotExist(c, Channel.class, cid)) {
			return errors;
		}
		//所选发布内容模型不在栏目模型范围内
		if(modelId!=null){
			CmsModel m=cmsModelMng.findById(modelId);
			if(errors.ifNotExist(m, CmsModel.class, modelId)){
				return errors;
			}
			//默认没有配置的情况下modelIds为空 则允许添加
			if(c.getModelIds().size()>0&&!c.getModelIds().contains(modelId.toString())){
				errors.addErrorCode("channel.modelError", c.getName(),m.getName());
			}
		}
		Integer siteId = CmsUtils.getSiteId(request);
		if (!c.getSite().getId().equals(siteId)) {
			errors.notInSite(Channel.class, cid);
			return errors;
		}
		return errors;
	}

	private WebErrors validateSave(Content bean, Integer channelId,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		bean.setSite(site);
		if (errors.ifNull(channelId, "channelId")) {
			return errors;
		}
		Channel channel = channelMng.findById(channelId);
		if (errors.ifNotExist(channel, Channel.class, channelId)) {
			return errors;
		}
		if (channel.getChild().size() > 0) {
			errors.addErrorCode("content.error.notLeafChannel");
		}
		//所选发布内容模型不在栏目模型范围内
		if(bean.getModel().getId()!=null){
			CmsModel m=bean.getModel();
			if(errors.ifNotExist(m, CmsModel.class, bean.getModel().getId())){
				return errors;
			}
			//默认没有配置的情况下modelIds为空 则允许添加
			if(channel.getModelIds().size()>0&&!channel.getModelIds().contains(bean.getModel().getId().toString())){
				errors.addErrorCode("channel.modelError", channel.getName(),m.getName());
			}
		}
		return errors;
	}

	private WebErrors validateEdit(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		// Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		return errors;
	}

	private WebErrors validateUpdate(Integer id, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (vldExist(id, site.getId(), errors)) {
			return errors;
		}
		Content content = manager.findById(id);
		// TODO 是否有编辑的数据权限。
		// 是否有审核后更新权限。
		if (!content.isHasUpdateRight()) {
			errors.addErrorCode("content.error.afterCheckUpdate");
			return errors;
		}
		return errors;
	}

	private WebErrors validateDelete(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
//		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		if(ids!=null&&ids.length>0){
			for (Integer id : ids) {
				/*
				if (vldExist(id, site.getId(), errors)) {
					return errors;
				}
				*/
				Content content = manager.findById(id);
				// TODO 是否有编辑的数据权限。
				// 是否有审核后删除权限。
				if (!content.isHasDeleteRight()) {
					errors.addErrorCode("content.error.afterCheckDelete");
					return errors;
				}

			}
		}
		return errors;
	}

	private WebErrors validateCheck(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateStatic(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateReject(Integer[] ids, HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		errors.ifEmpty(ids, "ids");
		for (Integer id : ids) {
			vldExist(id, site.getId(), errors);
		}
		return errors;
	}

	private WebErrors validateUpload(MultipartFile file,
			HttpServletRequest request) {
		String origName = file.getOriginalFilename();
		CmsUser user= CmsUtils.getUser(request);
		String ext = FilenameUtils.getExtension(origName).toLowerCase(Locale.ENGLISH);
		int fileSize = (int) (file.getSize() / 1024);
		WebErrors errors = WebErrors.create(request);
		if (errors.ifNull(file, "file")) {
			return errors;
		}
		if(origName!=null&&(origName.contains("/")||origName.contains("\\")||origName.indexOf("\0")!=-1)){
			errors.addErrorCode("upload.error.filename", origName);
		}
		//非允许的后缀
		if(!user.isAllowSuffix(ext)){
			errors.addErrorCode("upload.error.invalidsuffix", ext);
			return errors;
		}
		//超过附件大小限制
		if(!user.isAllowMaxFile((int)(file.getSize()/1024))){
			errors.addErrorCode("upload.error.toolarge",origName,user.getGroup().getAllowMaxFile());
			return errors;
		}
		//超过每日上传限制
		if (!user.isAllowPerDay(fileSize)) {
			long laveSize=user.getGroup().getAllowPerDay()-user.getUploadSize();
			if(laveSize<0){
				laveSize=0;
			}
			errors.addErrorCode("upload.error.dailylimit", laveSize);
		}
		return errors;
	}

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		Content entity = manager.findById(id);
		if (errors.ifNotExist(entity, Content.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(Content.class, id);
			return true;
		}
		return false;
	}
	
	private ContentTxt copyContentTxtImg(ContentTxt txt,CmsSite site){
		if(StringUtils.isNotBlank(txt.getTxt())){
			txt.setTxt(copyTxtHmtlImg(txt.getTxt(), site));
		}
		if(StringUtils.isNotBlank(txt.getTxt1())){
			txt.setTxt1(copyTxtHmtlImg(txt.getTxt1(), site));
		}	
		if(StringUtils.isNotBlank(txt.getTxt2())){
			txt.setTxt2(copyTxtHmtlImg(txt.getTxt2(), site));
		}
		if(StringUtils.isNotBlank(txt.getTxt3())){
			txt.setTxt3(copyTxtHmtlImg(txt.getTxt3(), site));
		}
		return txt;
	}
	
	private String copyTxtHmtlImg(String txtHtml,CmsSite  site){
		List<String>imgUrls=ImageUtils.getImageSrc(txtHtml);
		for(String img:imgUrls){
			txtHtml=txtHtml.replace(img, imageSvc.crawlImg(img,site));
		}
		return txtHtml;
	}

	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private CmsUserMng cmsUserMng;
	@Autowired
	private CmsModelMng cmsModelMng;
	@Autowired
	private CmsModelItemMng cmsModelItemMng;
	@Autowired
	private CmsTopicMng cmsTopicMng;
	@Autowired
	private CmsGroupMng cmsGroupMng;
	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private TplManager tplManager;
	@Autowired
	private FileRepository fileRepository;
	@Autowired
	private DbFileMng dbFileMng;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private ContentMng manager;
	@Autowired
	private CmsFileMng fileMng;
	@Autowired
	private CmsSiteMng siteMng;
	@Autowired
	private ImageSvc imageSvc;
	@Autowired
	private WeiXinSvc weiXinSvc;
}
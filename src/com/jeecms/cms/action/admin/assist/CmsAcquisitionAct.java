package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.cms.entity.assist.CmsAcquisitionHistory;
import com.jeecms.cms.entity.assist.CmsAcquisitionTemp;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.entity.main.ContentType;
import com.jeecms.cms.manager.assist.CmsAcquisitionHistoryMng;
import com.jeecms.cms.manager.assist.CmsAcquisitionMng;
import com.jeecms.cms.manager.assist.CmsAcquisitionTempMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.cms.manager.main.ContentTypeMng;
import com.jeecms.cms.service.AcquisitionSvc;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.ResponseUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.manager.CmsLogMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsAcquisitionAct {
	private static final Logger log = LoggerFactory
			.getLogger(CmsAcquisitionAct.class);
	
	@RequiresPermissions("acquisition:v_list")
	@RequestMapping("/acquisition/v_list.do")
	public String list(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		List<CmsAcquisition> list = manager.getList(site.getId());
		model.addAttribute("list", list);
		return "acquisition/list";
	}

	@RequiresPermissions("acquisition:v_add")
	@RequestMapping("/acquisition/v_add.do")
	public String add(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		return "acquisition/add";
	}

	@RequiresPermissions("acquisition:v_edit")
	@RequestMapping("/acquisition/v_edit.do")
	public String edit(Integer id, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsSite site = CmsUtils.getSite(request);
		// 内容类型
		List<ContentType> typeList = contentTypeMng.getList(false);
		// 栏目列表
		List<Channel> topList = channelMng.getTopList(site.getId(), true);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				true);
		model.addAttribute("channelList", channelList);
		model.addAttribute("typeList", typeList);
		model.addAttribute("cmsAcquisition", manager.findById(id));
		return "acquisition/edit";
	}

	@RequiresPermissions("acquisition:o_save")
	@RequestMapping("/acquisition/o_save.do")
	public String save(CmsAcquisition bean, Integer channelId, Integer typeId,
			HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Integer siteId = CmsUtils.getSiteId(request);
		Integer userId = CmsUtils.getUserId(request);
		bean = manager.save(bean, channelId, typeId, userId, siteId);
		log.info("save CmsAcquisition id={}", bean.getId());
		cmsLogMng.operating(request, "cmsAcquisition.log.save", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return "redirect:v_list.do";
	}

	@RequiresPermissions("acquisition:o_update")
	@RequestMapping("/acquisition/o_update.do")
	public String update(CmsAcquisition bean, Integer channelId,
			Integer typeId, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean = manager.update(bean, channelId, typeId);
		log.info("update CmsAcquisition id={}.", bean.getId());
		cmsLogMng.operating(request, "cmsAcquisition.log.update", "id="
				+ bean.getId() + ";name=" + bean.getName());
		return list(request, model);
	}

	@RequiresPermissions("acquisition:o_delete")
	@RequestMapping("/acquisition/o_delete.do")
	public String delete(Integer[] ids, HttpServletRequest request,
			ModelMap model) {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAcquisition[] beans = manager.deleteByIds(ids);
		for (CmsAcquisition bean : beans) {
			log.info("delete CmsAcquisition id={}", bean.getId());
			cmsLogMng.operating(request, "cmsAcquisition.log.delete", "id="
					+ bean.getId() + ";name=" + bean.getName());
		}
		return list(request, model);
	}

	@RequiresPermissions("acquisition:o_start")
	@RequestMapping("/acquisition/o_start.do")
	public String start(Integer[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Integer siteId = CmsUtils.getSiteId(request);
		WebErrors errors = validateStart(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		Integer queueNum = manager.hasStarted(siteId);
		if(queueNum==0){
			acquisitionSvc.start(ids[0]);
		}
		manager.addToQueue(ids, queueNum);
		log.info("start CmsAcquisition ids={}", Arrays.toString(ids));
		return "acquisition/progress";
	}

	@RequiresPermissions("acquisition:o_end")
	@RequestMapping("/acquisition/o_end.do")
	public String end(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		manager.end(id);
		CmsAcquisition acqu = manager.popAcquFromQueue(siteId);
		if (acqu != null) {
			Integer acquId = acqu.getId();
			acquisitionSvc.start(acquId);
		}
		log.info("end CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("acquisition:o_pause")
	@RequestMapping("/acquisition/o_pause.do")
	public String pause(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		manager.pause(id);
		log.info("pause CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("acquisition:o_cancel")
	@RequestMapping("/acquisition/o_cancel.do")
	public String cancel(Integer id, Integer sortId, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		WebErrors errors = WebErrors.create(request);
		Integer siteId = CmsUtils.getSiteId(request);
		if (vldExist(id, siteId, errors)) {
			return errors.showErrorPage(model);
		}
		manager.cancel(siteId, id);
		log.info("cancel CmsAcquisition id={}", id);
		return "redirect:v_list.do";
	}

	@RequiresPermissions("acquisition:v_check_complete")
	@RequestMapping("/acquisition/v_check_complete.do")
	public void checkComplete(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws JSONException {
		JSONObject json = new JSONObject();
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsAcquisition acqu = manager.getStarted(siteId);
		json.put("completed", acqu == null ? true : false);
		ResponseUtils.renderJson(response, json.toString());
	}

	@RequiresPermissions("acquisition:v_progress_data")
	@RequestMapping("/acquisition/v_progress_data.do")
	public String progressData(Integer id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsAcquisition acqu = manager.getStarted(siteId);
		List<CmsAcquisitionTemp> list = cmsAcquisitionTempMng.getList(siteId);
		model.put("percent", cmsAcquisitionTempMng.getPercent(siteId));
		model.put("acqu", acqu);
		model.put("list", list);
		return "acquisition/progress_data";
	}

	@RequiresPermissions("acquisition:v_progress")
	@RequestMapping("/acquisition/v_progress.do")
	public String progress(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		CmsAcquisition acqu = manager.getStarted(siteId);
		if (acqu == null) {
			cmsAcquisitionTempMng.clear(siteId);
		}
		return "acquisition/progress";
	}

	@RequiresPermissions("acquisition:v_history")
	@RequestMapping("/acquisition/v_history.do")
	public String history(Integer acquId, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		Integer siteId = site.getId();
		Pagination pagination = cmsAcquisitionHistoryMng.getPage(siteId,
				acquId, cpn(pageNo), CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pageNo", pagination.getPageNo());
		return "acquisition/history";
	}

	@RequiresPermissions("acquisition:o_delete_history")
	@RequestMapping("/acquisition/o_delete_history.do")
	public String deleteHistory(Integer[] ids, Integer pageNo,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {
		WebErrors errors = validateHistoryDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsAcquisitionHistory[] beans = cmsAcquisitionHistoryMng
				.deleteByIds(ids);
		for (CmsAcquisitionHistory bean : beans) {
			log.info("delete CmsAcquisitionHistory id={}", bean.getId());
			cmsLogMng.operating(request, "cmsAcquisitionHistory.log.delete",
					"id=" + bean.getId() + ";name=" + bean.getTitle());
		}
		return history(null, pageNo, request, response, model);
	}

	private WebErrors validateSave(CmsAcquisition bean,
			HttpServletRequest request) {
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
	
	private WebErrors validateStart(Integer[] ids, HttpServletRequest request) {
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
		CmsAcquisition entity = manager.findById(id);
		if (errors.ifNotExist(entity, CmsAcquisition.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsAcquisition.class, id);
			return true;
		}
		return false;
	}

	private WebErrors validateHistoryDelete(Integer[] ids,
			HttpServletRequest request) {
		WebErrors errors = WebErrors.create(request);
		CmsSite site = CmsUtils.getSite(request);
		if (errors.ifEmpty(ids, "ids")) {
			return errors;
		}
		for (Integer id : ids) {
			vldHistoryExist(id, site.getId(), errors);
		}
		return errors;
	}

	private boolean vldHistoryExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsAcquisitionHistory entity = cmsAcquisitionHistoryMng.findById(id);
		if (errors.ifNotExist(entity, CmsAcquisitionHistory.class, id)) {
			return true;
		}
		if (!entity.getAcquisition().getSite().getId().equals(siteId)) {
			errors.notInSite(CmsAcquisitionHistory.class, id);
			return true;
		}
		return false;
	}

	@Autowired
	private ContentTypeMng contentTypeMng;
	@Autowired
	private ChannelMng channelMng;
	@Autowired
	private AcquisitionSvc acquisitionSvc;
	@Autowired
	private CmsLogMng cmsLogMng;
	@Autowired
	private CmsAcquisitionMng manager;
	@Autowired
	private CmsAcquisitionHistoryMng cmsAcquisitionHistoryMng;
	@Autowired
	private CmsAcquisitionTempMng cmsAcquisitionTempMng;
}
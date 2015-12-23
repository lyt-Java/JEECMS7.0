package com.jeecms.cms.action.admin.assist;

import static com.jeecms.common.page.SimplePage.cpn;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeecms.cms.entity.assist.CmsAcquisition;
import com.jeecms.cms.entity.assist.CmsTask;
import com.jeecms.cms.entity.main.Channel;
import com.jeecms.cms.manager.assist.CmsAcquisitionMng;
import com.jeecms.cms.manager.assist.CmsTaskMng;
import com.jeecms.cms.manager.main.ChannelMng;
import com.jeecms.common.page.Pagination;
import com.jeecms.common.web.CookieUtils;
import com.jeecms.common.web.RequestUtils;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.Ftp;
import com.jeecms.core.manager.FtpMng;
import com.jeecms.core.web.WebErrors;
import com.jeecms.core.web.util.CmsUtils;

@Controller
public class CmsTaskAct {
	private static final Logger log = LoggerFactory.getLogger(CmsTaskAct.class);

	@RequiresPermissions("task:v_list")
	@RequestMapping("/task/v_list.do")
	public String list(Integer pageNo, HttpServletRequest request, ModelMap model) {
		Pagination pagination = manager.getPage(CmsUtils.getSiteId(request),cpn(pageNo), CookieUtils
				.getPageSize(request));
		model.addAttribute("pagination",pagination);
		model.addAttribute("pageNo",pagination.getPageNo());
		return "task/list";
	}

	@RequiresPermissions("task:v_add")
	@RequestMapping("/task/v_add.do")
	public String add(HttpServletRequest request,ModelMap model) {
		List<Ftp>ftpList=ftpMng.getList();
		List<CmsAcquisition>acquList=acquisitionMng.getList(CmsUtils.getSiteId(request));
		// 栏目列表
		CmsSite site = CmsUtils.getSite(request);
		List<Channel> topList = channelMng.getTopList(site.getId(), false);
		List<Channel> channelList = Channel.getListForSelect(topList, null,
				null, false);
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("acquList", acquList);
		model.addAttribute("channelList", channelList);
		return "task/add";
	}

	@RequiresPermissions("task:v_edit")
	@RequestMapping("/task/v_edit.do")
	public String edit(Integer id, Integer pageNo, HttpServletRequest request, ModelMap model) {
		WebErrors errors = validateEdit(id, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsTask task=manager.findById(id);
		List<Ftp>ftpList=ftpMng.getList();
		List<CmsAcquisition>acquList=acquisitionMng.getList(CmsUtils.getSiteId(request));
		// 栏目列表
		CmsSite site = CmsUtils.getSite(request);
		List<Channel> topList = channelMng.getTopList(site.getId(), false);
		List<Channel> channelList = Channel.getListForSelect(topList, null,null, false);
		Map<String,String>params=task.getAttr();
		//获取分发文件夹
		List<String>folders=getFolderParams(params);
		model.addAttribute("ftpList", ftpList);
		model.addAttribute("acquList", acquList);
		model.addAttribute("channelList", channelList);
		model.addAttribute("task", task);
		model.addAttribute("folders", folders);
		model.addAttribute("pageNo",pageNo);
		return "task/edit";
	}

	@RequiresPermissions("task:o_save")
	@RequestMapping("/task/o_save.do")
	public String save(CmsTask bean, HttpServletRequest request, ModelMap model) throws ParseException, SchedulerException, ClassNotFoundException {
		WebErrors errors = validateSave(bean, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		bean.init(CmsUtils.getSite(request),CmsUtils.getUser(request));
		Map<String,String>attrs=RequestUtils.getRequestMap(request, "attr_");
		UUID uuid=UUID.randomUUID();
		bean.setTaskCode(uuid.toString());
		//静态化任务添加站点id参数
		String siteId= CmsUtils.getSite(request).getId().toString();
		if(bean.getType().equals(CmsTask.TASK_STATIC_INDEX)){
			attrs.put(CmsTask.TASK_PARAM_SITE_ID,siteId);
			bean.setAttr(attrs);
		}else if(bean.getType().equals(CmsTask.TASK_STATIC_CHANNEL)||bean.getType().equals(CmsTask.TASK_STATIC_CONTENT)){
			attrs.put(CmsTask.TASK_PARAM_SITE_ID, siteId);
		}
		bean.setAttr(attrs);
		bean = manager.save(bean);
		//启用则启动任务
		if(bean.getEnable()){
			startTask(bean,uuid.toString());
		}
		log.info("save CmsTask id={}", bean.getId());
		return "redirect:v_list.do";
	}
	

	@RequiresPermissions("task:o_update")
	@RequestMapping("/task/o_update.do")
	public String update(CmsTask bean, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws SchedulerException, ParseException, ClassNotFoundException {
		WebErrors errors = validateUpdate(bean.getId(), request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		if(bean.getExecycle().equals(CmsTask.EXECYCLE_DEFINE)){
			bean.setCronExpression(null);
		}else if(bean.getExecycle().equals(CmsTask.EXECYCLE_CRON)){
			bean.setIntervalUnit(null);
		}
		Map<String, String> attr = RequestUtils.getRequestMap(request, "attr_");
		//静态化任务添加站点id参数
		String siteId= CmsUtils.getSite(request).getId().toString();
		if(bean.getType().equals(CmsTask.TASK_STATIC_INDEX)){
			attr.put(CmsTask.TASK_PARAM_SITE_ID,siteId);
			bean.setAttr(attr);
		}else if(bean.getType().equals(CmsTask.TASK_STATIC_CHANNEL)||bean.getType().equals(CmsTask.TASK_STATIC_CONTENT)){
			attr.put(CmsTask.TASK_PARAM_SITE_ID, siteId);
		}
		//结束之前的任务，开始新任务调度
		String oldTaskCode=manager.findById(bean.getId()).getTaskCode();
		endTask(oldTaskCode);
		UUID uuid=UUID.randomUUID();
		bean.setTaskCode(uuid.toString());
		bean = manager.update(bean,attr);
		if(bean.getEnable()){
			startTask(bean,uuid.toString());
		}
		log.info("update CmsTask id={}.", bean.getId());
		return list(pageNo, request, model);
	}

	@RequiresPermissions("task:o_delete")
	@RequestMapping("/task/o_delete.do")
	public String delete(Integer[] ids, Integer pageNo, HttpServletRequest request,
			ModelMap model) throws SchedulerException {
		WebErrors errors = validateDelete(ids, request);
		if (errors.hasErrors()) {
			return errors.showErrorPage(model);
		}
		CmsTask[] beans = manager.deleteByIds(ids);
		for (CmsTask bean : beans) {
			//删除结束任务
			endTask(bean.getTaskCode());
			log.info("delete CmsTask id={}", bean.getId());
		}
		return list(pageNo, request, model);
	}
	
	/**
	 * 开始任务调度
	 * @param task 任务
	 * @param taskCode 任务名称
	 * @throws ParseException
	 * @throws SchedulerException
	 * @throws ClassNotFoundException
	 */
	private void startTask(CmsTask task,String taskCode) throws ParseException, SchedulerException, ClassNotFoundException{
		String cronExpress=manager.getCronExpressionFromDB(task.getId());
		if(cronExpress.indexOf("null")==-1){
			JobDetail jobDetail = new JobDetail();
			jobDetail.setName(taskCode);
			jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
			jobDetail.setJobClass(getClassByTask(task.getJobClass()));
			//任务需要参数attr属性 
			jobDetail.setJobDataMap(getJobDataMap(task.getAttr()));
			CronTrigger cronTrigger = new CronTrigger("cron_" + taskCode,Scheduler.DEFAULT_GROUP, jobDetail.getName(),Scheduler.DEFAULT_GROUP);
			cronTrigger.setCronExpression(cronExpress);
			scheduler.scheduleJob(jobDetail, cronTrigger); 
		}
	}
	
	/**
	 * 结束任务调度
	 * @param taskName 
	 */
	private void endTask(String taskName) throws SchedulerException{
		scheduler.deleteJob(taskName, Scheduler.DEFAULT_GROUP);
	}
	
	private JobDataMap getJobDataMap(Map<String,String> params){
		JobDataMap jdm=new JobDataMap();
		Set<String>keySet=params.keySet();
		Iterator<String>it=keySet.iterator();
		while(it.hasNext()){
			String key=it.next();
			jdm.put(key, params.get(key));
		}
		return jdm;
	}
	
	@SuppressWarnings("unchecked")
	private Class getClassByTask(String taskClassName) throws ClassNotFoundException{
		return Class.forName(taskClassName);
	}
	
	private List<String>getFolderParams(Map<String,String>params){
		List<String>folders=new ArrayList<String>();
		Set<String>keySet=params.keySet();
		Iterator<String>it=keySet.iterator();
		while(it.hasNext()){
			String key=it.next();
			if(key.startsWith(CmsTask.TASK_PARAM_FOLDER_PREFIX)){
				folders.add(params.get(key));
			}
		}
		return folders;
	}

	private WebErrors validateSave(CmsTask bean, HttpServletRequest request) {
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

	private boolean vldExist(Integer id, Integer siteId, WebErrors errors) {
		if (errors.ifNull(id, "id")) {
			return true;
		}
		CmsTask entity = manager.findById(id);
		if(errors.ifNotExist(entity, CmsTask.class, id)) {
			return true;
		}
		if (!entity.getSite().getId().equals(siteId)) {
			errors.notInSite(CmsTask.class, id);
			return true;
		}
		return false;
	}
	@Autowired
	private Scheduler scheduler;
	@Autowired
	private CmsTaskMng manager;
	@Autowired
	private FtpMng ftpMng;
	@Autowired
	private CmsAcquisitionMng acquisitionMng;
	@Autowired
	private ChannelMng channelMng;
}
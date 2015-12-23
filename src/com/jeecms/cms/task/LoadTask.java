package com.jeecms.cms.task;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeecms.cms.entity.assist.CmsTask;
import com.jeecms.cms.manager.assist.CmsTaskMng;

/**
 * @author Tom
 */
public class LoadTask{
	/**
	 * 系统初始加载任务
	 */
	public void loadTask(){
		List<CmsTask>tasks=taskMng.getList();
		if(tasks.size()>0){
			for (int i = 0; i < tasks.size(); i++) {
				CmsTask task=tasks.get(i);
				//任务开启状态 执行任务调度
				if(task.getEnable()){
					try {
						JobDetail jobDetail = new JobDetail();
						//设置任务名称
						if(StringUtils.isNotBlank(task.getTaskCode())){
							jobDetail.setName(task.getTaskCode());
						}else{
							UUID uuid=UUID.randomUUID();
							jobDetail.setName(uuid.toString());
							task.setTaskCode(uuid.toString());
							taskMng.update(task, task.getAttr());
						}
						jobDetail.setGroup(Scheduler.DEFAULT_GROUP);
						//设置任务执行类
						jobDetail.setJobClass(getClassByTask(task.getJobClass()));
						//添加任务参数
						jobDetail.setJobDataMap(getJobDataMap(task.getAttr()));
						CronTrigger cronTrigger = new CronTrigger("cron_" + i,Scheduler.DEFAULT_GROUP, jobDetail.getName(),Scheduler.DEFAULT_GROUP);
						//设置任务规则
						cronTrigger.setCronExpression(taskMng.getCronExpressionFromDB(task.getId()));
						//调度任务
						scheduler.scheduleJob(jobDetail, cronTrigger); 
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SchedulerException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param params 任务参数
	 * @return
	 */
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
	
	/**
	 * 
	 * @param taskClassName 任务执行类名
	 * @return
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private Class getClassByTask(String taskClassName) throws ClassNotFoundException{
		return Class.forName(taskClassName);
	}
	@Autowired
	private CmsTaskMng taskMng;
	@Autowired
	private Scheduler scheduler;
}

package com.jeecms.cms.task.job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jeecms.cms.entity.assist.CmsTask;
import com.jeecms.cms.service.AcquisitionSvc;
/**
 * @Description 采集job类
* @author tom
 */
public class AcquisiteJob extends QuartzJobBean {
	private static final Logger log = LoggerFactory.getLogger(AcquisiteJob.class);
	protected void executeInternal(JobExecutionContext context)throws JobExecutionException {
	try {
		SchedulerContext schCtx = context.getScheduler().getContext();
		JobDataMap jdm=context.getJobDetail().getJobDataMap();
		//获取采集源
		this.acquId=Integer.parseInt((String) jdm.get(CmsTask.TASK_PARAM_ACQU_ID));
		// 获取Spring中的上下文
		ApplicationContext appCtx = (ApplicationContext) schCtx.get("applicationContext");
		this.acquisitionSvc = (AcquisitionSvc) appCtx.getBean("acquisitionSvc");
	} catch (SchedulerException e1) {
		// TODO 尚未处理异常
		e1.printStackTrace();
	}
		acquStart();
	}
	
	public void acquStart() {
		log.info("task acquisite "+acquId);
		acquisitionSvc.start(this.acquId);
	}
	
	private AcquisitionSvc acquisitionSvc;
	private Integer acquId;
}

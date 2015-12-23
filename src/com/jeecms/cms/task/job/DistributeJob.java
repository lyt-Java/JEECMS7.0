package com.jeecms.cms.task.job;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.jeecms.common.web.springmvc.RealPathResolver;
import com.jeecms.core.entity.Ftp;
import com.jeecms.core.manager.FtpMng;


/**
 * @Description 分发job类
* @author tom
 */
public class DistributeJob extends QuartzJobBean {
	private static final Logger log = LoggerFactory.getLogger(DistributeJob.class);

	/**
	 * 分发调度任务执行方法
	 */
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			SchedulerContext schCtx = context.getScheduler().getContext();
			JobDataMap jdm=context.getJobDetail().getJobDataMap();
			//获取任务参数-分发FTP ID
			this.ftpId=Integer.parseInt((String) jdm.get(CmsTask.TASK_PARAM_FTP_ID));
			//获取任务参数-待分发文件夹
			this.folders=getFolderValues(jdm);
			// 获取Spring中的上下文
			ApplicationContext appCtx = (ApplicationContext) schCtx.get("applicationContext");
			this.ftpMng = (FtpMng) appCtx.getBean("ftpMng");
			this.realPathResolver=(RealPathResolver) appCtx.getBean("realPathResolver");
			distribute();
		} catch (SchedulerException e1) {
			// TODO 尚未处理异常
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 分发文件夹到指定FTP
	 */
	public void distribute() throws FileNotFoundException, IOException {
		Ftp ftp=ftpMng.findById(ftpId);
		for(String folder:this.folders){
			log.info("distribute folder  "+folder);
			String folderPath=realPathResolver.get(folder);
			String rootPath=realPathResolver.get("");
			if(StringUtils.isNotBlank(folder)&&StringUtils.isNotBlank(folderPath)){
				ftp.storeByFloder(folderPath,rootPath);
			}
		}
	}
	
	/**
	 * 
	 * @param jdm
	 * @return 要分发的文件夹名称列表
	 */
	private List<String> getFolderValues(JobDataMap jdm){
		List<String> folderKeys=getFolderdKeys(jdm);
		List<String>folderValues=new ArrayList<String>();
		for(String key:folderKeys){
			folderValues.add(getJobDataValue(jdm, key));
		}
		return folderValues;
	}
	/**
	 * 
	 * @param jdm
	 * @return jdm中key参数（文件夹名称）
	 */
	@SuppressWarnings("unchecked")
	private List<String> getFolderdKeys(JobDataMap jdm){
		List<String>keys=new ArrayList<String>();
		Set<String>keySet=jdm.keySet();
		Iterator<String>it=keySet.iterator();
		while(it.hasNext()){
			String key=it.next();
			if(key.startsWith(CmsTask.TASK_PARAM_FOLDER_PREFIX)){
				keys.add(key);
			}
		}
		return keys;
	}
	
	/**
	 * 
	 * @param jdm
	 * @param key文件夹key
	 * @return 文件夹
	 */
	private String getJobDataValue(JobDataMap jdm,String key){
		if(StringUtils.isBlank(key)){
			return null;
		}
		return (String) jdm.get(key);
	}

	private FtpMng ftpMng;
	private RealPathResolver realPathResolver;
	private Integer ftpId;
	private List<String>folders;
}

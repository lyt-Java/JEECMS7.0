package com.jeecms.cms.entity.assist;

import java.util.Calendar;

import com.jeecms.cms.entity.assist.base.BaseCmsTask;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.entity.CmsUser;



public class CmsTask extends BaseCmsTask {
	/**
	 * 任务执行周期cron表达式
	 */
	public static int EXECYCLE_CRON=2;
	/**
	 * 任务执行周期自定义
	 */
	public static int EXECYCLE_DEFINE=1;
	/**
	 * 执行周期-分钟
	 */
	public static int EXECYCLE_MINUTE=1;
	/**
	 * 执行周期-小时
	 */
	public static int EXECYCLE_HOUR=2;
	/**
	 * 执行周期-日
	 */
	public static int EXECYCLE_DAY=3;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_WEEK=4;
	/**
	 * 执行周期-月
	 */
	public static int EXECYCLE_MONTH=5;
	/**
	 * 首页静态任务
	 */
	public static int TASK_STATIC_INDEX=1;
	/**
	 * 栏目页静态化任务
	 */
	public static int TASK_STATIC_CHANNEL=2;
	/**
	 * 内容页静态化任务
	 */
	public static int TASK_STATIC_CONTENT=3;
	/**
	 * 采集类任务
	 */
	public static int TASK_ACQU=4;
	/**
	 * 分发类任务
	 */
	public static int TASK_DISTRIBUTE=5;
	/**
	 * 采集源ID
	 */
	public static String TASK_PARAM_ACQU_ID="acqu_id";
	/**
	 * 分发FTP ID
	 */
	public static String TASK_PARAM_FTP_ID="ftp_id";
	/**
	 * 站点 ID
	 */
	public static String TASK_PARAM_SITE_ID="site_id";
	/**
	 * 栏目 ID
	 */
	public static String TASK_PARAM_CHANNEL_ID="channel_id";
	/**
	 * 分发到FTP 的文件夹参数前缀
	 */
	public static String TASK_PARAM_FOLDER_PREFIX="floder_";
	
	private static final long serialVersionUID = 1L;
	public void init(CmsSite site,CmsUser user){
		if(getCreateTime()==null){
			setCreateTime(Calendar.getInstance().getTime());
		}
		if(getUser()==null){
			setUser(user);
		}
		if(getSite()==null){
			setSite(site);
		}
	}
	public boolean getEnable () {
		return super.isEnable();
	}

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsTask () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsTask (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsTask (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser user,
		com.jeecms.core.entity.CmsSite site,
		java.lang.Integer type,
		java.lang.String name,
		java.lang.String jobClass,
		boolean enable,
		java.util.Date createTime) {

		super (
			id,
			user,
			site,
			type,
			name,
			jobClass,
			enable,
			createTime);
	}

/*[CONSTRUCTOR MARKER END]*/


}
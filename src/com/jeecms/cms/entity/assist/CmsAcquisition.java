package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.assist.base.BaseCmsAcquisition;

public class CmsAcquisition extends BaseCmsAcquisition {
	private static final long serialVersionUID = 1L;
	/**
	 * 动态页翻页页号
	 */
	public static final String PAGE = "[page]";
	/**
	 * 停止状态
	 */
	public static final int STOP = 0;
	/**
	 * 采集状态
	 */
	public static final int START = 1;
	/**
	 * 暂停状态
	 */
	public static final int PAUSE = 2;

	public static enum AcquisitionResultType {
		SUCCESS, TITLESTARTNOTFOUND, TITLEENDNOTFOUND, CONTENTSTARTNOTFOUND, CONTENTENDNOTFOUND,VIEWSTARTNOTFOUND,VIEWENDNOTFOUND,AUTHORSTARTNOTFOUND,AUTHORENDNOTFOUND,ORIGINSTARTNOTFOUND,ORIGINENDNOTFOUND,DESCRISTARTNOTFOUND,DESCRIENDNOTFOUND, RELEASESTARTNOTFOUND,RELEASEENDNOTFOUND,VIEWIDSTARTNOTFOUND,VIEWIDENDNOTFOUND,TITLEEXIST, URLEXIST, UNKNOW
	}
	
	public static enum AcquisitionRepeatCheckType{
		NONE, TITLE, URL
	}

	/**
	 * 是否停止
	 * 
	 * @return
	 */
	public boolean isStop() {
		int status = getStatus();
		return status == 0;
	}

	/**
	 * 是否暂停
	 * 
	 * @return
	 */
	public boolean isPuase() {
		int status = getStatus();
		return status == 2;
	}

	/**
	 * 是否中断。停止和暂停都需要中断。
	 * 
	 * @return
	 */
	public boolean isBreak() {
		int status = getStatus();
		return status == 0 || status == 2;
	}

	public String[] getPlans() {
		String plan = getPlanList();
		if (!StringUtils.isBlank(plan)) {
			return StringUtils.split(plan);
		} else {
			return new String[0];
		}
	}

	public String[] getAllPlans() {
		String[] plans = getPlans();
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null && start >= 0 && end >= start) {
			int plansLen = plans.length;
			String[] allPlans = new String[plansLen + end - start + 1];
			for (int i = 0; i < plansLen; i++) {
				allPlans[i] = plans[i];
			}
			for (int i = 0, len = end - start + 1; i < len; i++) {
				allPlans[plansLen + i] = getDynamicAddrByNum(start + i);
			}
			return allPlans;
		} else {
			return plans;
		}
	}

	public String getDynamicAddrByNum(int num) {
		return StringUtils.replace(getDynamicAddr(), PAGE, String.valueOf(num));
	}

	public int getTotalNum() {
		int count = 0;
		Integer start = getDynamicStart();
		Integer end = getDynamicEnd();
		if (!StringUtils.isBlank(getDynamicAddr()) && start != null
				&& end != null) {
			count = end - start + 1;
		}
		if (!StringUtils.isBlank(getPlanList())) {
			count += getPlans().length;
		}
		return count;
	}

	public void init() {
		if (getStatus() == null) {
			setStatus(STOP);
		}
		if (getCurrNum() == null) {
			setCurrNum(0);
		}
		if (getCurrItem() == null) {
			setCurrItem(0);
		}
		if (getTotalItem() == null) {
			setTotalItem(0);
		}
		if (getPauseTime() == null) {
			setPauseTime(0);
		}
		if(getQueue()==null){
			setQueue(0);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsAcquisition () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAcquisition (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAcquisition (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser user,
		com.jeecms.cms.entity.main.ContentType type,
		com.jeecms.core.entity.CmsSite site,
		com.jeecms.cms.entity.main.Channel channel,
		java.lang.String name,
		java.lang.Integer status,
		java.lang.Integer currNum,
		java.lang.Integer currItem,
		java.lang.Integer totalItem,
		java.lang.Integer pauseTime,
		java.lang.String pageEncoding,
		java.lang.Integer queue) {

		super (
			id,
			user,
			type,
			site,
			channel,
			name,
			status,
			currNum,
			currItem,
			totalItem,
			pauseTime,
			pageEncoding,
			queue);
	}

	/* [CONSTRUCTOR MARKER END] */

}
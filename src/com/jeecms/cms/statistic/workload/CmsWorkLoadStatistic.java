package com.jeecms.cms.statistic.workload;

import java.util.Date;

import com.jeecms.cms.entity.main.Channel;
import com.jeecms.core.entity.CmsUser;

/**
 * @author Tom
 */
public class CmsWorkLoadStatistic {
	/**
	 * 
	 * 统计模式
	 * 
	 */
	public static enum CmsWorkLoadStatisticGroup {
		day,week,month,	year
	}
	public static enum CmsWorkLoadStatisticDateKind {
		release,check
	}

	
	public CmsWorkLoadStatistic() {
		super();
	}

	public CmsWorkLoadStatistic(Channel channel, Date date, Long count) {
		super();
		this.channel = channel;
		this.date = date;
		this.count = count;
	}

	public CmsWorkLoadStatistic(Channel channel, CmsUser author,
			CmsUser reviewer, Long count) {
		super();
		this.channel = channel;
		this.author = author;
		this.reviewer = reviewer;
		this.count = count;
	}

	public CmsWorkLoadStatistic(CmsUser author, CmsUser reviewer, Date date,
			Long count) {
		super();
		this.author = author;
		this.reviewer = reviewer;
		this.date = date;
		this.count = count;
	}

	public CmsWorkLoadStatistic(Channel channel, CmsUser author,
			CmsUser reviewer, Date date, Long count) {
		super();
		this.channel = channel;
		this.author = author;
		this.reviewer = reviewer;
		this.date = date;
		this.count = count;
	}

	private Channel channel;
	private CmsUser author;
	private CmsUser reviewer;
	private Date date;
	private Long count;


	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public CmsUser getAuthor() {
		return author;
	}

	public void setAuthor(CmsUser author) {
		this.author = author;
	}

	public CmsUser getReviewer() {
		return reviewer;
	}

	public void setReviewer(CmsUser reviewer) {
		this.reviewer = reviewer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}

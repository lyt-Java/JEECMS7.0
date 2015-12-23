package com.jeecms.cms.entity.main;

import com.jeecms.cms.entity.main.base.BaseContentCount;

public class ContentCount extends BaseContentCount {
	private static final long serialVersionUID = 1L;
	
	public enum ContentViewCount{
		viewTotal,viewMonth,viewWeek,viewDay
	}

	public void init() {
		short zero = 0;
		if (getDowns() == null) {
			setDowns(0);
		}
		if (getViews() == null) {
			setViews(0);
		}
		if (getViewsMonth() == null) {
			setViewsMonth(0);
		}
		if (getViewsWeek() == null) {
			setViewsWeek(0);
		}
		if (getViewsDay() == null) {
			setViewsDay(0);
		}
		if (getComments() == null) {
			setComments(0);
		}
		if (getCommentsMonth() == null) {
			setCommentsMonth(0);
		}
		if (getCommentsWeek() == null) {
			setCommentsWeek(zero);
		}
		if (getCommentsDay() == null) {
			setCommentsDay(zero);
		}
		if (getDownloads() == null) {
			setDownloads(0);
		}
		if (getDownloadsMonth() == null) {
			setDownloadsMonth(0);
		}
		if (getDownloadsWeek() == null) {
			setDownloadsWeek(zero);
		}
		if (getDownloadsDay() == null) {
			setDownloadsDay(zero);
		}
		if (getUps() == null) {
			setUps(0);
		}
		if (getUpsMonth() == null) {
			setUpsMonth(0);
		}
		if (getUpsWeek() == null) {
			setUpsWeek(zero);
		}
		if (getUpsDay() == null) {
			setUpsDay(zero);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentCount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentCount (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentCount (
		java.lang.Integer id,
		java.lang.Integer views,
		java.lang.Integer viewsMonth,
		java.lang.Integer viewsWeek,
		java.lang.Integer viewsDay,
		java.lang.Integer comments,
		java.lang.Integer commentsMonth,
		java.lang.Short commentsWeek,
		java.lang.Short commentsDay,
		java.lang.Integer downloads,
		java.lang.Integer downloadsMonth,
		java.lang.Short downloadsWeek,
		java.lang.Short downloadsDay,
		java.lang.Integer ups,
		java.lang.Integer upsMonth,
		java.lang.Short upsWeek,
		java.lang.Short upsDay,
		java.lang.Integer downs) {

		super (
			id,
			views,
			viewsMonth,
			viewsWeek,
			viewsDay,
			comments,
			commentsMonth,
			commentsWeek,
			commentsDay,
			downloads,
			downloadsMonth,
			downloadsWeek,
			downloadsDay,
			ups,
			upsMonth,
			upsWeek,
			upsDay,
			downs);
	}

	/* [CONSTRUCTOR MARKER END] */

}
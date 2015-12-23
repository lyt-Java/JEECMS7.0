package com.jeecms.cms.entity.main;

import com.jeecms.cms.entity.main.base.BaseChannelCount;



public class ChannelCount extends BaseChannelCount {
	private static final long serialVersionUID = 1L;
	public void init() {
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
	}

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ChannelCount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChannelCount (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ChannelCount (
		java.lang.Integer id,
		java.lang.Integer views,
		java.lang.Integer viewsMonth,
		java.lang.Integer viewsWeek,
		java.lang.Integer viewsDay) {

		super (
			id,
			views,
			viewsMonth,
			viewsWeek,
			viewsDay);
	}

/*[CONSTRUCTOR MARKER END]*/


}
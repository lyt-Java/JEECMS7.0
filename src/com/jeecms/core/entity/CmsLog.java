package com.jeecms.core.entity;

import com.jeecms.core.entity.base.BaseCmsLog;

public class CmsLog extends BaseCmsLog {
	private static final long serialVersionUID = 1L;
	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILURE = 2;
	public static final int OPERATING = 3;
	
	public static final String LOGIN_SUCCESS_TITLE = "login success";
	public static final String LOGIN_FAILURE_TITLE = "login failure";

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsLog () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsLog (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsLog (
		java.lang.Integer id,
		java.lang.Integer category,
		java.util.Date time) {

		super (
			id,
			category,
			time);
	}

	/* [CONSTRUCTOR MARKER END] */

}
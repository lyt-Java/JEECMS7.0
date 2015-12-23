package com.jeecms.cms.entity.main;

import com.jeecms.cms.entity.main.base.BaseCmsThirdAccount;



public class CmsThirdAccount extends BaseCmsThirdAccount {
	private static final long serialVersionUID = 1L;
	
	public static final String QQ_KEY="openId";
	public static final String SINA_KEY="uid";
	public static final String QQ_PLAT="QQ";
	public static final String SINA_PLAT="SINA";
	public static final String QQ_WEBO_KEY="weboOpenId";
	public static final String QQ_WEBO_PLAT="QQWEBO";
/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsThirdAccount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsThirdAccount (java.lang.Long id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsThirdAccount (
		java.lang.Long id,
		java.lang.String accountKey,
		java.lang.String username,
		java.lang.String source) {

		super (
			id,
			accountKey,
			username,
			source);
	}

/*[CONSTRUCTOR MARKER END]*/


}
package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsSiteAccessCount;



public class CmsSiteAccessCount extends BaseCmsSiteAccessCount {
	private static final long serialVersionUID = 1L;
	

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteAccessCount () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSiteAccessCount (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSiteAccessCount (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.Integer pageCount,
		java.lang.Integer visitors,
		java.util.Date statisticDate) {

		super (
			id,
			site,
			pageCount,
			visitors,
			statisticDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}
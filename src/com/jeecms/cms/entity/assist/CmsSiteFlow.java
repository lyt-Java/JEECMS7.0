package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsSiteFlow;



public class CmsSiteFlow extends BaseCmsSiteFlow {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteFlow () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSiteFlow (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSiteFlow (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String accessIp,
		java.lang.String accessDate,
		java.lang.String accessPage,
		java.lang.String sessionId) {

		super (
			id,
			site,
			accessIp,
			accessDate,
			accessPage,
			sessionId);
	}

/*[CONSTRUCTOR MARKER END]*/


}
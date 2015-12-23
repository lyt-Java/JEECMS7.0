package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsVoteRecord;



public class CmsVoteRecord extends BaseCmsVoteRecord {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsVoteRecord () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsVoteRecord (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsVoteRecord (
		java.lang.Integer id,
		com.jeecms.cms.entity.assist.CmsVoteTopic topic,
		java.util.Date time,
		java.lang.String ip,
		java.lang.String cookie) {

		super (
			id,
			topic,
			time,
			ip,
			cookie);
	}

/*[CONSTRUCTOR MARKER END]*/


}
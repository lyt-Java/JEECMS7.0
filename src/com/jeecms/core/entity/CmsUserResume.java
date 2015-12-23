package com.jeecms.core.entity;

import com.jeecms.core.entity.base.BaseCmsUserResume;



public class CmsUserResume extends BaseCmsUserResume {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsUserResume () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsUserResume (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsUserResume (
		java.lang.Integer id,
		java.lang.String resumeName) {

		super (
			id,
			resumeName);
	}

/*[CONSTRUCTOR MARKER END]*/


}
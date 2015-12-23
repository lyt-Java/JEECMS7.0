package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsOrigin;



public class CmsOrigin extends BaseCmsOrigin {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsOrigin () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsOrigin (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsOrigin (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer refCount,
		java.lang.String nameInitial) {

		super (
			id,
			name,
			refCount,
			nameInitial);
	}

/*[CONSTRUCTOR MARKER END]*/


}
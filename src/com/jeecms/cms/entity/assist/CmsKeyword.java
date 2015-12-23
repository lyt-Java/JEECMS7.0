package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsKeyword;

public class CmsKeyword extends BaseCmsKeyword {
	private static final long serialVersionUID = 1L;

	public void init() {
		if (getDisabled() == null) {
			setDisabled(false);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsKeyword () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsKeyword (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsKeyword (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String url,
		java.lang.Boolean disabled) {

		super (
			id,
			name,
			url,
			disabled);
	}

	/* [CONSTRUCTOR MARKER END] */

}
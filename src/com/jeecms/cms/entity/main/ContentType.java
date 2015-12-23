package com.jeecms.cms.entity.main;

import com.jeecms.cms.entity.main.base.BaseContentType;

public class ContentType extends BaseContentType {
	private static final long serialVersionUID = 1L;

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentType () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentType (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentType (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Boolean hasImage,
		java.lang.Boolean disabled) {

		super (
			id,
			name,
			hasImage,
			disabled);
	}

	/* [CONSTRUCTOR MARKER END] */

}
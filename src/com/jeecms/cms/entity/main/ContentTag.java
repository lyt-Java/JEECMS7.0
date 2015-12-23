package com.jeecms.cms.entity.main;

import com.jeecms.cms.entity.main.base.BaseContentTag;

public class ContentTag extends BaseContentTag {
	private static final long serialVersionUID = 1L;

	public void init() {
		if (getCount() == null) {
			setCount(1);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentTag () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentTag (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentTag (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer count) {

		super (
			id,
			name,
			count);
	}

	/* [CONSTRUCTOR MARKER END] */

}
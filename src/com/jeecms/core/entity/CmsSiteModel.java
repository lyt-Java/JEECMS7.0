package com.jeecms.core.entity;

import com.jeecms.core.entity.base.BaseCmsSiteModel;



public class CmsSiteModel extends BaseCmsSiteModel {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteModel () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSiteModel (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSiteModel (
		java.lang.Integer id,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer priority) {

		super (
			id,
			field,
			label,
			priority);
	}

/*[CONSTRUCTOR MARKER END]*/


}
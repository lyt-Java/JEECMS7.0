package com.jeecms.core.entity;

import com.jeecms.core.entity.base.BaseCmsSiteCompany;



public class CmsSiteCompany extends BaseCmsSiteCompany {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsSiteCompany () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsSiteCompany (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsSiteCompany (
		java.lang.Integer id,
		java.lang.String name) {

		super (
			id,
			name);
	}

/*[CONSTRUCTOR MARKER END]*/


}
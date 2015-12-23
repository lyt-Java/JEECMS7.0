package com.jeecms.core.entity;

import com.jeecms.core.entity.base.BaseDbFile;



public class DbFile extends BaseDbFile {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public DbFile () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public DbFile (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public DbFile (
		java.lang.String id,
		java.lang.Integer length,
		java.lang.Long lastModified,
		byte[] content) {

		super (
			id,
			length,
			lastModified,
			content);
	}

/*[CONSTRUCTOR MARKER END]*/


}
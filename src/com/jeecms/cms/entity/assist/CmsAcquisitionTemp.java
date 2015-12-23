package com.jeecms.cms.entity.assist;

import com.jeecms.cms.entity.assist.base.BaseCmsAcquisitionTemp;



public class CmsAcquisitionTemp extends BaseCmsAcquisitionTemp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsAcquisitionTemp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsAcquisitionTemp (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsAcquisitionTemp (
		java.lang.Integer id,
		java.lang.String channelUrl,
		java.lang.String contentUrl,
		java.lang.Integer percent,
		java.lang.String description,
		java.lang.Integer seq) {

		super (
			id,
			channelUrl,
			contentUrl,
			percent,
			description,
			seq);
	}

/*[CONSTRUCTOR MARKER END]*/


}
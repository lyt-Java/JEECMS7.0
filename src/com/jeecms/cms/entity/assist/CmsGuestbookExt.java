package com.jeecms.cms.entity.assist;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.assist.base.BaseCmsGuestbookExt;

public class CmsGuestbookExt extends BaseCmsGuestbookExt {
	private static final long serialVersionUID = 1L;

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getContent())) {
			setContent(null);
		}
		if (StringUtils.isBlank(getReply())) {
			setReply(null);
		}
		if (StringUtils.isBlank(getTitle())) {
			setTitle(null);
		}
		if (StringUtils.isBlank(getEmail())) {
			setEmail(null);
		}
		if (StringUtils.isBlank(getPhone())) {
			setPhone(null);
		}
		if (StringUtils.isBlank(getQq())) {
			setQq(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsGuestbookExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsGuestbookExt (java.lang.Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
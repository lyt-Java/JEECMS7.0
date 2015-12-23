package com.jeecms.cms.entity.assist;

import java.sql.Timestamp;

import com.jeecms.cms.entity.assist.base.BaseCmsGuestbook;
import com.jeecms.common.util.StrUtils;

public class CmsGuestbook extends BaseCmsGuestbook {
	private static final long serialVersionUID = 1L;

	public String getTitleHtml() {
		return StrUtils.txt2htm(getTitle());
	}

	public String getContentHtml() {
		return StrUtils.txt2htm(getContent());
	}

	public String getReplyHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getTitle() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getTitle();
		} else {
			return null;
		}
	}

	public String getContent() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getContent();
		} else {
			return null;
		}
	}

	public String getReply() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getReply();
		} else {
			return null;
		}
	}

	public String getEmail() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getEmail();
		} else {
			return null;
		}
	}

	public String getPhone() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getPhone();
		} else {
			return null;
		}
	}

	public String getQq() {
		CmsGuestbookExt ext = getExt();
		if (ext != null) {
			return ext.getQq();
		} else {
			return null;
		}
	}

	public void init() {
		if (getChecked() == null) {
			setChecked(false);
		}
		if (getRecommend() == null) {
			setRecommend(false);
		}
		if (getCreateTime() == null) {
			setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsGuestbook () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsGuestbook (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsGuestbook (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		com.jeecms.cms.entity.assist.CmsGuestbookCtg ctg,
		java.lang.String ip,
		java.util.Date createTime,
		java.lang.Boolean checked,
		java.lang.Boolean recommend) {

		super (
			id,
			site,
			ctg,
			ip,
			createTime,
			checked,
			recommend);
	}

	/* [CONSTRUCTOR MARKER END] */

}
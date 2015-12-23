package com.jeecms.cms.entity.assist;

import java.sql.Timestamp;

import com.jeecms.cms.entity.assist.base.BaseCmsComment;
import com.jeecms.common.util.StrUtils;

public class CmsComment extends BaseCmsComment {
	private static final long serialVersionUID = 1L;

	public String getText() {
		return getCommentExt().getText();
	}

	public String getTextHtml() {
		return StrUtils.txt2htm(getText());
	}

	public String getReply() {
		return getCommentExt().getReply();
	}

	public String getReplayHtml() {
		return StrUtils.txt2htm(getReply());
	}

	public String getIp() {
		return getCommentExt().getIp();
	}

	public void init() {
		short zero = 0;
		if (getDowns() == null) {
			setDowns(zero);
		}
		if (getUps() == null) {
			setUps(zero);
		}
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
	public CmsComment () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsComment (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsComment (
		java.lang.Integer id,
		com.jeecms.cms.entity.main.Content content,
		com.jeecms.core.entity.CmsSite site,
		java.util.Date createTime,
		java.lang.Short ups,
		java.lang.Short downs,
		java.lang.Boolean recommend,
		java.lang.Boolean checked) {

		super (
			id,
			content,
			site,
			createTime,
			ups,
			downs,
			recommend,
			checked);
	}

	/* [CONSTRUCTOR MARKER END] */

}
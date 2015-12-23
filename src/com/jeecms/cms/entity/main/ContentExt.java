package com.jeecms.cms.entity.main;

import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.main.base.BaseContentExt;
import com.jeecms.core.entity.CmsSite;
import com.jeecms.core.web.util.CmsUtils;

public class ContentExt extends BaseContentExt {
	private static final long serialVersionUID = 1L;

	/**
	 * 如果简短标题为空，则返回标题
	 * 
	 * @return
	 */
	public String getStitle() {
		if (!StringUtils.isBlank(getShortTitle())) {
			return getShortTitle();
		} else {
			return getTitle();
		}
	}

	public void init() {
		if (getReleaseDate() == null) {
			setReleaseDate(new Timestamp(System.currentTimeMillis()));
		}
		if (getBold() == null) {
			setBold(false);
		}
		if(getNeedRegenerate()==null){
			setNeedRegenerate(true);
		}
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getShortTitle())) {
			setShortTitle(null);
		}
		if (StringUtils.isBlank(getAuthor())) {
			setAuthor(null);
		}
		if (StringUtils.isBlank(getOrigin())) {
			setOrigin(null);
		}
		if (StringUtils.isBlank(getOriginUrl())) {
			setOriginUrl(null);
		}
		if (StringUtils.isBlank(getDescription())) {
			setDescription(null);
		}
		if (StringUtils.isBlank(getTitleColor())) {
			setTitleColor(null);
		}
		if (StringUtils.isBlank(getTitleImg())) {
			setTitleImg(null);
		}
		if (StringUtils.isBlank(getContentImg())) {
			setContentImg(null);
		}
		if (StringUtils.isBlank(getTypeImg())) {
			setTypeImg(null);
		}
		if (StringUtils.isBlank(getLink())) {
			setLink(null);
		}
		if (StringUtils.isBlank(getTplContent())) {
			setTplContent(null);
		}
		if (StringUtils.isBlank(getMediaPath())) {
			setMediaPath(null);
		}
		if (StringUtils.isBlank(getMediaType())) {
			setMediaType(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ContentExt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentExt (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ContentExt (
		java.lang.Integer id,
		java.lang.String title,
		java.util.Date releaseDate,
		java.lang.Boolean bold,
		java.lang.Boolean needRegenerate) {

		super (
			id,
			title,
			releaseDate,
			bold,
			needRegenerate);
	}

	/* [CONSTRUCTOR MARKER END] */

}
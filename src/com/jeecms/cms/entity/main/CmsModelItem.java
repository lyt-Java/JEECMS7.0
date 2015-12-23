package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.main.base.BaseCmsModelItem;

public class CmsModelItem extends BaseCmsModelItem {
	private static final long serialVersionUID = 1L;

	public void init() {
		if (getPriority() == null) {
			setPriority(10);
		}
		if (getSingle() == null) {
			setSingle(true);
		}
		if (getCustom() == null) {
			setCustom(true);
		}
		if (getDisplay() == null) {
			setDisplay(true);
		}
	}

	// 将字符串字段全部设置为非null，方便判断。
	public void emptyToNull() {
		if (StringUtils.isBlank(getDefValue())) {
			setDefValue(null);
		}
		if (StringUtils.isBlank(getOptValue())) {
			setOptValue(null);
		}
		if (StringUtils.isBlank(getSize())) {
			setSize(null);
		}
		if (StringUtils.isBlank(getRows())) {
			setRows(null);
		}
		if (StringUtils.isBlank(getCols())) {
			setCols(null);
		}
		if (StringUtils.isBlank(getHelp())) {
			setHelp(null);
		}
		if (StringUtils.isBlank(getHelpPosition())) {
			setHelpPosition(null);
		}
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public CmsModelItem () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsModelItem (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsModelItem (
		java.lang.Integer id,
		com.jeecms.cms.entity.main.CmsModel model,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer dataType,
		java.lang.Boolean single,
		java.lang.Boolean channel,
		java.lang.Boolean custom,
		java.lang.Boolean display) {

		super (
			id,
			model,
			field,
			label,
			dataType,
			single,
			channel,
			custom,
			display);
	}

	/* [CONSTRUCTOR MARKER END] */

}
package com.jeecms.core.entity;

import org.apache.commons.lang.StringUtils;

import com.jeecms.core.entity.base.BaseCmsConfigItem;



public class CmsConfigItem extends BaseCmsConfigItem {
	//会员注册模型
	public static final int CATEGORY_REGISTER=1;
	//留言模型
	public static final int CATEGORY_GUESTBOOK=2;
	private static final long serialVersionUID = 1L;
	public boolean getRequired(){
		return isRequired();
	}
	
	public void init() {
		if (getPriority() == null) {
			setPriority(10);
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

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsConfigItem () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsConfigItem (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsConfigItem (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsConfig config,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer priority,
		java.lang.Integer dataType,
		boolean required,
		java.lang.Integer category) {

		super (
			id,
			config,
			field,
			label,
			priority,
			dataType,
			required,
			category);
	}

/*[CONSTRUCTOR MARKER END]*/


}
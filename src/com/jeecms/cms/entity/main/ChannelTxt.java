package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.main.base.BaseChannelTxt;

public class ChannelTxt extends BaseChannelTxt {
	private static final long serialVersionUID = 1L;

	public void init() {
		blankToNull();
	}

	public void blankToNull() {
		if (StringUtils.isBlank(getTxt())) {
			setTxt(null);
		}
		if (StringUtils.isBlank(getTxt1())) {
			setTxt1(null);
		}
		if (StringUtils.isBlank(getTxt2())) {
			setTxt2(null);
		}
		if (StringUtils.isBlank(getTxt3())) {
			setTxt3(null);
		}
	}

	/**
	 * 是否所有属性都为空
	 * 
	 * @return
	 */
	public boolean isAllBlank() {
		return StringUtils.isBlank(getTxt()) && StringUtils.isBlank(getTxt1())
				&& StringUtils.isBlank(getTxt2())
				&& StringUtils.isBlank(getTxt3());
	}

	/* [CONSTRUCTOR MARKER BEGIN] */
	public ChannelTxt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ChannelTxt (java.lang.Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
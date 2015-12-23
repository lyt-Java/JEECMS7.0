package com.jeecms.cms.entity.main;

import org.apache.commons.lang.StringUtils;

import com.jeecms.cms.entity.main.base.BaseContentTxt;

public class ContentTxt extends BaseContentTxt {
	private static final long serialVersionUID = 1L;
	//ueditor采用分页
	public static String PAGE_START = "[NextPage]";
	public static String PAGE_END = "[/NextPage]";
	//public static String PAGE_START = "<p>[NextPage]";
	//public static String PAGE_END = "[/NextPage]</p>";
	

	public int getTxtCount() {
		String txt = getTxt();
		if (StringUtils.isBlank(txt)) {
			return 1;
		} else {
			return StringUtils.countMatches(txt, PAGE_START) + 1;
		}
	}

	public String getTxtByNo(int pageNo) {
		String txt = getTxt();
		if (StringUtils.isBlank(txt) || pageNo < 1) {
			return null;
		}
		int start = 0, end = 0;
		for (int i = 0; i < pageNo; i++) {
			// 如果不是第一页
			if (i != 0) {
				start = txt.indexOf(PAGE_END, end);
				if (start == -1) {
					return null;
				} else {
					start += PAGE_END.length();
				}
			}
			end = txt.indexOf(PAGE_START, start);
			if (end == -1) {
				end = txt.length();
			}
		}
		return txt.substring(start, end);
	}

	public String getTitleByNo(int pageNo) {
		if (pageNo < 1) {
			return null;
		}
		String title = getContent().getTitle();
		if (pageNo == 1) {
			return title;
		}
		String txt = getTxt();
		int start = 0, end = 0;
		for (int i = 1; i < pageNo; i++) {
			start = txt.indexOf(PAGE_START, end);
			if (start == -1) {
				return title;
			} else {
				start += PAGE_START.length();
			}
			end = txt.indexOf(PAGE_END, start);
			if (end == -1) {
				return title;
			}
		}
		String result = txt.substring(start, end);
		if (!StringUtils.isBlank(result)) {
			return result;
		} else {
			return title;
		}
	}

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
	public ContentTxt () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ContentTxt (java.lang.Integer id) {
		super(id);
	}

	/* [CONSTRUCTOR MARKER END] */

}
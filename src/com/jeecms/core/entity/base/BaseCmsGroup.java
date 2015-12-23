package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_group table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_group"
 */

public abstract class BaseCmsGroup  implements Serializable {

	public static String REF = "CmsGroup";
	public static String PROP_NEED_CHECK = "needCheck";
	public static String PROP_ALLOW_MAX_FILE = "allowMaxFile";
	public static String PROP_ALLOW_SUFFIX = "allowSuffix";
	public static String PROP_ALLOW_PER_DAY = "allowPerDay";
	public static String PROP_PRIORITY = "priority";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_REG_DEF = "regDef";
	public static String PROP_NEED_CAPTCHA = "needCaptcha";


	// constructors
	public BaseCmsGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsGroup (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsGroup (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer priority,
		java.lang.Integer allowPerDay,
		java.lang.Integer allowMaxFile,
		java.lang.Boolean needCaptcha,
		java.lang.Boolean needCheck,
		java.lang.Boolean regDef) {

		this.setId(id);
		this.setName(name);
		this.setPriority(priority);
		this.setAllowPerDay(allowPerDay);
		this.setAllowMaxFile(allowMaxFile);
		this.setNeedCaptcha(needCaptcha);
		this.setNeedCheck(needCheck);
		this.setRegDef(regDef);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer priority;
	private java.lang.Integer allowPerDay;
	private java.lang.Integer allowMaxFile;
	private java.lang.String allowSuffix;
	private java.lang.Boolean needCaptcha;
	private java.lang.Boolean needCheck;
	private java.lang.Boolean regDef;

	// collections
	private java.util.Set<com.jeecms.cms.entity.main.Channel> viewChannels;
	private java.util.Set<com.jeecms.cms.entity.main.Channel> contriChannels;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="group_id"
     */
	public java.lang.Integer getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.Integer id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: group_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: group_name
	 * @param name the group_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: priority
	 */
	public java.lang.Integer getPriority () {
		return priority;
	}

	/**
	 * Set the value related to the column: priority
	 * @param priority the priority value
	 */
	public void setPriority (java.lang.Integer priority) {
		this.priority = priority;
	}


	/**
	 * Return the value associated with the column: allow_per_day
	 */
	public java.lang.Integer getAllowPerDay () {
		return allowPerDay;
	}

	/**
	 * Set the value related to the column: allow_per_day
	 * @param allowPerDay the allow_per_day value
	 */
	public void setAllowPerDay (java.lang.Integer allowPerDay) {
		this.allowPerDay = allowPerDay;
	}


	/**
	 * Return the value associated with the column: allow_max_file
	 */
	public java.lang.Integer getAllowMaxFile () {
		return allowMaxFile;
	}

	/**
	 * Set the value related to the column: allow_max_file
	 * @param allowMaxFile the allow_max_file value
	 */
	public void setAllowMaxFile (java.lang.Integer allowMaxFile) {
		this.allowMaxFile = allowMaxFile;
	}


	/**
	 * Return the value associated with the column: allow_suffix
	 */
	public java.lang.String getAllowSuffix () {
		return allowSuffix;
	}

	/**
	 * Set the value related to the column: allow_suffix
	 * @param allowSuffix the allow_suffix value
	 */
	public void setAllowSuffix (java.lang.String allowSuffix) {
		this.allowSuffix = allowSuffix;
	}

	/**
	 * Return the value associated with the column: need_captcha
	 */
	public java.lang.Boolean getNeedCaptcha () {
		return needCaptcha;
	}

	/**
	 * Set the value related to the column: need_captcha
	 * @param needCaptcha the need_captcha value
	 */
	public void setNeedCaptcha (java.lang.Boolean needCaptcha) {
		this.needCaptcha = needCaptcha;
	}


	/**
	 * Return the value associated with the column: need_check
	 */
	public java.lang.Boolean getNeedCheck () {
		return needCheck;
	}

	/**
	 * Set the value related to the column: need_check
	 * @param needCheck the need_check value
	 */
	public void setNeedCheck (java.lang.Boolean needCheck) {
		this.needCheck = needCheck;
	}


	/**
	 * Return the value associated with the column: is_reg_def
	 */
	public java.lang.Boolean getRegDef () {
		return regDef;
	}

	/**
	 * Set the value related to the column: is_reg_def
	 * @param regDef the is_reg_def value
	 */
	public void setRegDef (java.lang.Boolean regDef) {
		this.regDef = regDef;
	}


	/**
	 * Return the value associated with the column: viewChannels
	 */
	public java.util.Set<com.jeecms.cms.entity.main.Channel> getViewChannels () {
		return viewChannels;
	}

	/**
	 * Set the value related to the column: viewChannels
	 * @param viewChannels the viewChannels value
	 */
	public void setViewChannels (java.util.Set<com.jeecms.cms.entity.main.Channel> viewChannels) {
		this.viewChannels = viewChannels;
	}


	/**
	 * Return the value associated with the column: contriChannels
	 */
	public java.util.Set<com.jeecms.cms.entity.main.Channel> getContriChannels () {
		return contriChannels;
	}

	/**
	 * Set the value related to the column: contriChannels
	 * @param contriChannels the contriChannels value
	 */
	public void setContriChannels (java.util.Set<com.jeecms.cms.entity.main.Channel> contriChannels) {
		this.contriChannels = contriChannels;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsGroup)) return false;
		else {
			com.jeecms.core.entity.CmsGroup cmsGroup = (com.jeecms.core.entity.CmsGroup) obj;
			if (null == this.getId() || null == cmsGroup.getId()) return false;
			else return (this.getId().equals(cmsGroup.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}
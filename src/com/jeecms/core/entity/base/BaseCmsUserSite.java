package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_user_site table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_user_site"
 */

public abstract class BaseCmsUserSite  implements Serializable {

	public static String REF = "CmsUserSite";
	public static String PROP_ALL_CHANNEL = "allChannel";
	public static String PROP_SITE = "site";
	public static String PROP_USER = "user";
	public static String PROP_CHECK_STEP = "checkStep";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsUserSite () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsUserSite (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsUserSite (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser user,
		com.jeecms.core.entity.CmsSite site,
		java.lang.Byte checkStep,
		java.lang.Boolean allChannel) {

		this.setId(id);
		this.setUser(user);
		this.setSite(site);
		this.setCheckStep(checkStep);
		this.setAllChannel(allChannel);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Byte checkStep;
	private java.lang.Boolean allChannel;

	// many to one
	private com.jeecms.core.entity.CmsUser user;
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="usersite_id"
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
	 * Return the value associated with the column: check_step
	 */
	public java.lang.Byte getCheckStep () {
		return checkStep;
	}

	/**
	 * Set the value related to the column: check_step
	 * @param checkStep the check_step value
	 */
	public void setCheckStep (java.lang.Byte checkStep) {
		this.checkStep = checkStep;
	}


	/**
	 * Return the value associated with the column: is_all_channel
	 */
	public java.lang.Boolean getAllChannel () {
		return allChannel;
	}

	/**
	 * Set the value related to the column: is_all_channel
	 * @param allChannel the is_all_channel value
	 */
	public void setAllChannel (java.lang.Boolean allChannel) {
		this.allChannel = allChannel;
	}


	/**
	 * Return the value associated with the column: user_id
	 */
	public com.jeecms.core.entity.CmsUser getUser () {
		return user;
	}

	/**
	 * Set the value related to the column: user_id
	 * @param user the user_id value
	 */
	public void setUser (com.jeecms.core.entity.CmsUser user) {
		this.user = user;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public com.jeecms.core.entity.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (com.jeecms.core.entity.CmsSite site) {
		this.site = site;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsUserSite)) return false;
		else {
			com.jeecms.core.entity.CmsUserSite cmsUserSite = (com.jeecms.core.entity.CmsUserSite) obj;
			if (null == this.getId() || null == cmsUserSite.getId()) return false;
			else return (this.getId().equals(cmsUserSite.getId()));
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
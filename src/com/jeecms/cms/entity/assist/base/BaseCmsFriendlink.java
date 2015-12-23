package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_friendlink table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_friendlink"
 */

public abstract class BaseCmsFriendlink  implements Serializable {

	public static String REF = "CmsFriendlink";
	public static String PROP_DOMAIN = "domain";
	public static String PROP_EMAIL = "email";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_LOGO = "logo";
	public static String PROP_SITE = "site";
	public static String PROP_ENABLED = "enabled";
	public static String PROP_VIEWS = "views";
	public static String PROP_CATEGORY = "category";
	public static String PROP_PRIORITY = "priority";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsFriendlink () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsFriendlink (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsFriendlink (
		java.lang.Integer id,
		com.jeecms.cms.entity.assist.CmsFriendlinkCtg category,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String name,
		java.lang.String domain,
		java.lang.Integer views,
		java.lang.Integer priority,
		java.lang.Boolean enabled) {

		this.setId(id);
		this.setCategory(category);
		this.setSite(site);
		this.setName(name);
		this.setDomain(domain);
		this.setViews(views);
		this.setPriority(priority);
		this.setEnabled(enabled);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String domain;
	private java.lang.String logo;
	private java.lang.String email;
	private java.lang.String description;
	private java.lang.Integer views;
	private java.lang.Integer priority;
	private java.lang.Boolean enabled;

	// many to one
	private com.jeecms.cms.entity.assist.CmsFriendlinkCtg category;
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="friendlink_id"
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
	 * Return the value associated with the column: site_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: site_name
	 * @param name the site_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: domain
	 */
	public java.lang.String getDomain () {
		return domain;
	}

	/**
	 * Set the value related to the column: domain
	 * @param domain the domain value
	 */
	public void setDomain (java.lang.String domain) {
		this.domain = domain;
	}


	/**
	 * Return the value associated with the column: logo
	 */
	public java.lang.String getLogo () {
		return logo;
	}

	/**
	 * Set the value related to the column: logo
	 * @param logo the logo value
	 */
	public void setLogo (java.lang.String logo) {
		this.logo = logo;
	}


	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}


	/**
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}


	/**
	 * Return the value associated with the column: views
	 */
	public java.lang.Integer getViews () {
		return views;
	}

	/**
	 * Set the value related to the column: views
	 * @param views the views value
	 */
	public void setViews (java.lang.Integer views) {
		this.views = views;
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
	 * Return the value associated with the column: is_enabled
	 */
	public java.lang.Boolean getEnabled () {
		return enabled;
	}

	/**
	 * Set the value related to the column: is_enabled
	 * @param enabled the is_enabled value
	 */
	public void setEnabled (java.lang.Boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * Return the value associated with the column: friendlinkctg_id
	 */
	public com.jeecms.cms.entity.assist.CmsFriendlinkCtg getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: friendlinkctg_id
	 * @param category the friendlinkctg_id value
	 */
	public void setCategory (com.jeecms.cms.entity.assist.CmsFriendlinkCtg category) {
		this.category = category;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsFriendlink)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsFriendlink cmsFriendlink = (com.jeecms.cms.entity.assist.CmsFriendlink) obj;
			if (null == this.getId() || null == cmsFriendlink.getId()) return false;
			else return (this.getId().equals(cmsFriendlink.getId()));
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
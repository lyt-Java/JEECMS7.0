package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_user_menu table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_user_menu"
 */

public abstract class BaseCmsUserMenu  implements Serializable {

	public static String REF = "CmsUserMenu";
	public static String PROP_USER = "user";
	public static String PROP_NAME = "name";
	public static String PROP_URL = "url";
	public static String PROP_ID = "id";
	public static String PROP_PRIORITY = "priority";


	// constructors
	public BaseCmsUserMenu () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsUserMenu (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsUserMenu (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser user,
		java.lang.String name,
		java.lang.String url,
		java.lang.Integer priority) {

		this.setId(id);
		this.setUser(user);
		this.setName(name);
		this.setUrl(url);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String url;
	private java.lang.Integer priority;

	// many to one
	private com.jeecms.core.entity.CmsUser user;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="menu_id"
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
	 * Return the value associated with the column: menu_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: menu_name
	 * @param name the menu_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: menu_url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: menu_url
	 * @param url the menu_url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
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



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsUserMenu)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsUserMenu cmsUserMenu = (com.jeecms.cms.entity.assist.CmsUserMenu) obj;
			if (null == this.getId() || null == cmsUserMenu.getId()) return false;
			else return (this.getId().equals(cmsUserMenu.getId()));
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
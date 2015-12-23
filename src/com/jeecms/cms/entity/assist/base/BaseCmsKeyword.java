package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_keyword table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_keyword"
 */

public abstract class BaseCmsKeyword  implements Serializable {

	public static String REF = "CmsKeyword";
	public static String PROP_SITE = "site";
	public static String PROP_DISABLED = "disabled";
	public static String PROP_URL = "url";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsKeyword () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsKeyword (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsKeyword (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String url,
		java.lang.Boolean disabled) {

		this.setId(id);
		this.setName(name);
		this.setUrl(url);
		this.setDisabled(disabled);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String url;
	private java.lang.Boolean disabled;

	// many to one
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="keyword_id"
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
	 * Return the value associated with the column: keyword_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: keyword_name
	 * @param name the keyword_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}


	/**
	 * Return the value associated with the column: is_disabled
	 */
	public java.lang.Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (java.lang.Boolean disabled) {
		this.disabled = disabled;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsKeyword)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsKeyword cmsKeyword = (com.jeecms.cms.entity.assist.CmsKeyword) obj;
			if (null == this.getId() || null == cmsKeyword.getId()) return false;
			else return (this.getId().equals(cmsKeyword.getId()));
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
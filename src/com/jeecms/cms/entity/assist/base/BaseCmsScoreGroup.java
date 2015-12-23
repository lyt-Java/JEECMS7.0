package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_score_group table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_score_group"
 */

public abstract class BaseCmsScoreGroup  implements Serializable {

	public static String REF = "CmsScoreGroup";
	public static String PROP_NAME = "name";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_SITE = "site";
	public static String PROP_ENABLE = "enable";
	public static String PROP_ID = "id";
	public static String PROP_DEF = "def";


	// constructors
	public BaseCmsScoreGroup () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsScoreGroup (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsScoreGroup (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String name,
		boolean enable,
		boolean def) {

		this.setId(id);
		this.setSite(site);
		this.setName(name);
		this.setEnable(enable);
		this.setDef(def);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String description;
	private boolean enable;
	private boolean def;

	// many to one
	private com.jeecms.core.entity.CmsSite site;

	// collections
	private java.util.Set<com.jeecms.cms.entity.assist.CmsScoreItem> items;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="score_group_id"
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
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
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
	 * Return the value associated with the column: enable
	 */
	public boolean isEnable () {
		return enable;
	}

	/**
	 * Set the value related to the column: enable
	 * @param enable the enable value
	 */
	public void setEnable (boolean enable) {
		this.enable = enable;
	}


	/**
	 * Return the value associated with the column: def
	 */
	public boolean isDef () {
		return def;
	}

	/**
	 * Set the value related to the column: def
	 * @param def the def value
	 */
	public void setDef (boolean def) {
		this.def = def;
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


	/**
	 * Return the value associated with the column: items
	 */
	public java.util.Set<com.jeecms.cms.entity.assist.CmsScoreItem> getItems () {
		return items;
	}

	/**
	 * Set the value related to the column: items
	 * @param items the items value
	 */
	public void setItems (java.util.Set<com.jeecms.cms.entity.assist.CmsScoreItem> items) {
		this.items = items;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsScoreGroup)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsScoreGroup cmsScoreGroup = (com.jeecms.cms.entity.assist.CmsScoreGroup) obj;
			if (null == this.getId() || null == cmsScoreGroup.getId()) return false;
			else return (this.getId().equals(cmsScoreGroup.getId()));
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
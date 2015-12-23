package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content_tag table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content_tag"
 */

public abstract class BaseContentTag  implements Serializable {

	public static String REF = "ContentTag";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_COUNT = "count";


	// constructors
	public BaseContentTag () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseContentTag (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseContentTag (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer count) {

		this.setId(id);
		this.setName(name);
		this.setCount(count);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer count;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="tag_id"
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
	 * Return the value associated with the column: tag_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: tag_name
	 * @param name the tag_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: ref_counter
	 */
	public java.lang.Integer getCount () {
		return count;
	}

	/**
	 * Set the value related to the column: ref_counter
	 * @param count the ref_counter value
	 */
	public void setCount (java.lang.Integer count) {
		this.count = count;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ContentTag)) return false;
		else {
			com.jeecms.cms.entity.main.ContentTag contentTag = (com.jeecms.cms.entity.main.ContentTag) obj;
			if (null == this.getId() || null == contentTag.getId()) return false;
			else return (this.getId().equals(contentTag.getId()));
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
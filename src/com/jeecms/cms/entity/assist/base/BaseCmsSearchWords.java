package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_search_words table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_search_words"
 */

public abstract class BaseCmsSearchWords  implements Serializable {

	public static String REF = "CmsSearchWords";
	public static String PROP_NAME = "name";
	public static String PROP_HIT_COUNT = "hitCount";
	public static String PROP_NAME_INITIAL = "nameInitial";
	public static String PROP_ID = "id";
	public static String PROP_PRIORITY = "priority";


	// constructors
	public BaseCmsSearchWords () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsSearchWords (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsSearchWords (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer hitCount,
		java.lang.Integer priority,
		java.lang.String nameInitial) {

		this.setId(id);
		this.setName(name);
		this.setHitCount(hitCount);
		this.setPriority(priority);
		this.setNameInitial(nameInitial);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer hitCount;
	private java.lang.Integer priority;
	private java.lang.String nameInitial;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="word_id"
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
	 * Return the value associated with the column: hit_count
	 */
	public java.lang.Integer getHitCount () {
		return hitCount;
	}

	/**
	 * Set the value related to the column: hit_count
	 * @param hitCount the hit_count value
	 */
	public void setHitCount (java.lang.Integer hitCount) {
		this.hitCount = hitCount;
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
	 * Return the value associated with the column: name_initial
	 */
	public java.lang.String getNameInitial () {
		return nameInitial;
	}

	/**
	 * Set the value related to the column: name_initial
	 * @param nameInitial the name_initial value
	 */
	public void setNameInitial (java.lang.String nameInitial) {
		this.nameInitial = nameInitial;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsSearchWords)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsSearchWords cmsSearchWords = (com.jeecms.cms.entity.assist.CmsSearchWords) obj;
			if (null == this.getId() || null == cmsSearchWords.getId()) return false;
			else return (this.getId().equals(cmsSearchWords.getId()));
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
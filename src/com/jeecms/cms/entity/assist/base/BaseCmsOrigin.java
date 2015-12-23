package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_origin table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_origin"
 */

public abstract class BaseCmsOrigin  implements Serializable {

	public static String REF = "CmsOrigin";
	public static String PROP_NAME = "name";
	public static String PROP_NAME_INITIAL = "nameInitial";
	public static String PROP_REF_COUNT = "refCount";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsOrigin () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsOrigin (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsOrigin (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Integer refCount,
		java.lang.String nameInitial) {

		this.setId(id);
		this.setName(name);
		this.setRefCount(refCount);
		this.setNameInitial(nameInitial);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer refCount;
	private java.lang.String nameInitial;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="origin_id"
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
	 * Return the value associated with the column: origin_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: origin_name
	 * @param name the origin_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: ref_count
	 */
	public java.lang.Integer getRefCount () {
		return refCount;
	}

	/**
	 * Set the value related to the column: ref_count
	 * @param refCount the ref_count value
	 */
	public void setRefCount (java.lang.Integer refCount) {
		this.refCount = refCount;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsOrigin)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsOrigin cmsOrigin = (com.jeecms.cms.entity.assist.CmsOrigin) obj;
			if (null == this.getId() || null == cmsOrigin.getId()) return false;
			else return (this.getId().equals(cmsOrigin.getId()));
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
package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_score_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_score_item"
 */

public abstract class BaseCmsScoreItem  implements Serializable {

	public static String REF = "CmsScoreItem";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_SCORE = "score";
	public static String PROP_GROUP = "group";
	public static String PROP_IMAGE_PATH = "imagePath";
	public static String PROP_PRIORITY = "priority";


	// constructors
	public BaseCmsScoreItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsScoreItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsScoreItem (
		java.lang.Integer id,
		com.jeecms.cms.entity.assist.CmsScoreGroup group,
		java.lang.String name,
		java.lang.Integer score,
		java.lang.Integer priority) {

		this.setId(id);
		this.setGroup(group);
		this.setName(name);
		this.setScore(score);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer score;
	private java.lang.String imagePath;
	private java.lang.Integer priority;

	// many to one
	private com.jeecms.cms.entity.assist.CmsScoreGroup group;

	// collections
	private java.util.Set<com.jeecms.cms.entity.assist.CmsScoreRecord> records;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="score_item_id"
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
	 * Return the value associated with the column: score
	 */
	public java.lang.Integer getScore () {
		return score;
	}

	/**
	 * Set the value related to the column: score
	 * @param score the score value
	 */
	public void setScore (java.lang.Integer score) {
		this.score = score;
	}


	/**
	 * Return the value associated with the column: image_path
	 */
	public java.lang.String getImagePath () {
		return imagePath;
	}

	/**
	 * Set the value related to the column: image_path
	 * @param imagePath the image_path value
	 */
	public void setImagePath (java.lang.String imagePath) {
		this.imagePath = imagePath;
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
	 * Return the value associated with the column: score_group_id
	 */
	public com.jeecms.cms.entity.assist.CmsScoreGroup getGroup () {
		return group;
	}

	/**
	 * Set the value related to the column: score_group_id
	 * @param group the score_group_id value
	 */
	public void setGroup (com.jeecms.cms.entity.assist.CmsScoreGroup group) {
		this.group = group;
	}


	/**
	 * Return the value associated with the column: records
	 */
	public java.util.Set<com.jeecms.cms.entity.assist.CmsScoreRecord> getRecords () {
		return records;
	}

	/**
	 * Set the value related to the column: records
	 * @param records the records value
	 */
	public void setRecords (java.util.Set<com.jeecms.cms.entity.assist.CmsScoreRecord> records) {
		this.records = records;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsScoreItem)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsScoreItem cmsScoreItem = (com.jeecms.cms.entity.assist.CmsScoreItem) obj;
			if (null == this.getId() || null == cmsScoreItem.getId()) return false;
			else return (this.getId().equals(cmsScoreItem.getId()));
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
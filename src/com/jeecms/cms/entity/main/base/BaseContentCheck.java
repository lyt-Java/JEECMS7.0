package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content_check table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content_check"
 */

public abstract class BaseContentCheck  implements Serializable {

	public static String REF = "ContentCheck";
	public static String PROP_REJECTED = "rejected";
	public static String PROP_CHECK_STEP = "checkStep";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";
	public static String PROP_CHECK_OPINION = "checkOpinion";


	// constructors
	public BaseContentCheck () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseContentCheck (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseContentCheck (
		java.lang.Integer id,
		java.lang.Byte checkStep,
		java.lang.Boolean rejected) {

		this.setId(id);
		this.setCheckStep(checkStep);
		this.setRejected(rejected);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Byte checkStep;
	private java.lang.String checkOpinion;
	private java.lang.Boolean rejected;
	private java.util.Date checkDate;
	
	private com.jeecms.core.entity.CmsUser reviewer;

	// one to one
	private com.jeecms.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="content_id"
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
	 * Return the value associated with the column: check_opinion
	 */
	public java.lang.String getCheckOpinion () {
		return checkOpinion;
	}

	/**
	 * Set the value related to the column: check_opinion
	 * @param checkOpinion the check_opinion value
	 */
	public void setCheckOpinion (java.lang.String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}


	/**
	 * Return the value associated with the column: is_rejected
	 */
	public java.lang.Boolean getRejected () {
		return rejected;
	}

	/**
	 * Set the value related to the column: is_rejected
	 * @param rejected the is_rejected value
	 */
	public void setRejected (java.lang.Boolean rejected) {
		this.rejected = rejected;
	}
	

	public java.util.Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(java.util.Date checkDate) {
		this.checkDate = checkDate;
	}

	public com.jeecms.core.entity.CmsUser getReviewer() {
		return reviewer;
	}

	public void setReviewer(com.jeecms.core.entity.CmsUser reviewer) {
		this.reviewer = reviewer;
	}

	/**
	 * Return the value associated with the column: content
	 */
	public com.jeecms.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (com.jeecms.cms.entity.main.Content content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ContentCheck)) return false;
		else {
			com.jeecms.cms.entity.main.ContentCheck contentCheck = (com.jeecms.cms.entity.main.ContentCheck) obj;
			if (null == this.getId() || null == contentCheck.getId()) return false;
			else return (this.getId().equals(contentCheck.getId()));
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
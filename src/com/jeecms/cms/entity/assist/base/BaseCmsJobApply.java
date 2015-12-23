package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_job_apply table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_job_apply"
 */

public abstract class BaseCmsJobApply  implements Serializable {

	public static String REF = "CmsJobApply";
	public static String PROP_USER = "user";
	public static String PROP_ID = "id";
	public static String PROP_CONTENT = "content";
	public static String PROP_APPLY_TIME = "applyTime";


	// constructors
	public BaseCmsJobApply () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsJobApply (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsJobApply (
		java.lang.Integer id,
		com.jeecms.cms.entity.main.Content content,
		com.jeecms.core.entity.CmsUser user,
		java.util.Date applyTime) {

		this.setId(id);
		this.setContent(content);
		this.setUser(user);
		this.setApplyTime(applyTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date applyTime;

	// many to one
	private com.jeecms.cms.entity.main.Content content;
	private com.jeecms.core.entity.CmsUser user;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="job_apply_id"
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
	 * Return the value associated with the column: apply_time
	 */
	public java.util.Date getApplyTime () {
		return applyTime;
	}

	/**
	 * Set the value related to the column: apply_time
	 * @param applyTime the apply_time value
	 */
	public void setApplyTime (java.util.Date applyTime) {
		this.applyTime = applyTime;
	}


	/**
	 * Return the value associated with the column: content_id
	 */
	public com.jeecms.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content_id
	 * @param content the content_id value
	 */
	public void setContent (com.jeecms.cms.entity.main.Content content) {
		this.content = content;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsJobApply)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsJobApply cmsJobApply = (com.jeecms.cms.entity.assist.CmsJobApply) obj;
			if (null == this.getId() || null == cmsJobApply.getId()) return false;
			else return (this.getId().equals(cmsJobApply.getId()));
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
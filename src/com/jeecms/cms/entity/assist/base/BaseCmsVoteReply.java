package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_vote_reply table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_vote_reply"
 */

public abstract class BaseCmsVoteReply  implements Serializable {

	public static String REF = "CmsVoteReply";
	public static String PROP_SUB_TOPIC = "subTopic";
	public static String PROP_ID = "id";
	public static String PROP_REPLY = "reply";


	// constructors
	public BaseCmsVoteReply () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsVoteReply (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String reply;

	// many to one
	private com.jeecms.cms.entity.assist.CmsVoteSubTopic subTopic;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="native"
     *  column="votereply_id"
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
	 * Return the value associated with the column: reply
	 */
	public java.lang.String getReply () {
		return reply;
	}

	/**
	 * Set the value related to the column: reply
	 * @param reply the reply value
	 */
	public void setReply (java.lang.String reply) {
		this.reply = reply;
	}


	/**
	 * Return the value associated with the column: subtopic_id
	 */
	public com.jeecms.cms.entity.assist.CmsVoteSubTopic getSubTopic () {
		return subTopic;
	}

	/**
	 * Set the value related to the column: subtopic_id
	 * @param subTopic the subtopic_id value
	 */
	public void setSubTopic (com.jeecms.cms.entity.assist.CmsVoteSubTopic subTopic) {
		this.subTopic = subTopic;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsVoteReply)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsVoteReply cmsVoteReply = (com.jeecms.cms.entity.assist.CmsVoteReply) obj;
			if (null == this.getId() || null == cmsVoteReply.getId()) return false;
			else return (this.getId().equals(cmsVoteReply.getId()));
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
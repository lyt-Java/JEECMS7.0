package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_vote_subtopic table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_vote_subtopic"
 */

public abstract class BaseCmsVoteSubTopic  implements Serializable {

	public static String REF = "CmsVoteSubTopic";
	public static String PROP_TYPE = "type";
	public static String PROP_VOTE_TOPIC = "voteTopic";
	public static String PROP_ID = "id";
	public static String PROP_TITLE = "title";
	public static String PROP_PRIORITY = "priority";


	// constructors
	public BaseCmsVoteSubTopic () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsVoteSubTopic (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsVoteSubTopic (
		java.lang.Integer id,
		com.jeecms.cms.entity.assist.CmsVoteTopic voteTopic,
		java.lang.String title,
		java.lang.Integer type,
		java.lang.Integer priority) {

		this.setId(id);
		this.setVoteTopic(voteTopic);
		this.setTitle(title);
		this.setType(type);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String title;
	private java.lang.Integer type;
	private java.lang.Integer priority;

	// many to one
	private com.jeecms.cms.entity.assist.CmsVoteTopic voteTopic;

	// collections
	private java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> voteItems;
	private java.util.Set<com.jeecms.cms.entity.assist.CmsVoteReply> voteReplys;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="native"
     *  column="subtopic_id"
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
	 * Return the value associated with the column: title
	 */
	public java.lang.String getTitle () {
		return title;
	}

	/**
	 * Set the value related to the column: title
	 * @param title the title value
	 */
	public void setTitle (java.lang.String title) {
		this.title = title;
	}


	/**
	 * Return the value associated with the column: subtopic_type
	 */
	public java.lang.Integer getType () {
		return type;
	}

	/**
	 * Set the value related to the column: subtopic_type
	 * @param type the subtopic_type value
	 */
	public void setType (java.lang.Integer type) {
		this.type = type;
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
	 * Return the value associated with the column: votetopic_id
	 */
	public com.jeecms.cms.entity.assist.CmsVoteTopic getVoteTopic () {
		return voteTopic;
	}

	/**
	 * Set the value related to the column: votetopic_id
	 * @param voteTopic the votetopic_id value
	 */
	public void setVoteTopic (com.jeecms.cms.entity.assist.CmsVoteTopic voteTopic) {
		this.voteTopic = voteTopic;
	}


	/**
	 * Return the value associated with the column: voteItems
	 */
	public java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> getVoteItems () {
		return voteItems;
	}

	/**
	 * Set the value related to the column: voteItems
	 * @param voteItems the voteItems value
	 */
	public void setVoteItems (java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> voteItems) {
		this.voteItems = voteItems;
	}


	/**
	 * Return the value associated with the column: voteReplys
	 */
	public java.util.Set<com.jeecms.cms.entity.assist.CmsVoteReply> getVoteReplys () {
		return voteReplys;
	}

	/**
	 * Set the value related to the column: voteReplys
	 * @param voteReplys the voteReplys value
	 */
	public void setVoteReplys (java.util.Set<com.jeecms.cms.entity.assist.CmsVoteReply> voteReplys) {
		this.voteReplys = voteReplys;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsVoteSubTopic)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsVoteSubTopic cmsVoteSubTopic = (com.jeecms.cms.entity.assist.CmsVoteSubTopic) obj;
			if (null == this.getId() || null == cmsVoteSubTopic.getId()) return false;
			else return (this.getId().equals(cmsVoteSubTopic.getId()));
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
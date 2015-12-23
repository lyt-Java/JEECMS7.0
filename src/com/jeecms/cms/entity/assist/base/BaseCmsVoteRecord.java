package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_vote_record table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_vote_record"
 */

public abstract class BaseCmsVoteRecord  implements Serializable {

	public static String REF = "CmsVoteRecord";
	public static String PROP_TIME = "time";
	public static String PROP_COOKIE = "cookie";
	public static String PROP_TOPIC = "topic";
	public static String PROP_USER = "user";
	public static String PROP_IP = "ip";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsVoteRecord () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsVoteRecord (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsVoteRecord (
		java.lang.Integer id,
		com.jeecms.cms.entity.assist.CmsVoteTopic topic,
		java.util.Date time,
		java.lang.String ip,
		java.lang.String cookie) {

		this.setId(id);
		this.setTopic(topic);
		this.setTime(time);
		this.setIp(ip);
		this.setCookie(cookie);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date time;
	private java.lang.String ip;
	private java.lang.String cookie;

	// many to one
	private com.jeecms.core.entity.CmsUser user;
	private com.jeecms.cms.entity.assist.CmsVoteTopic topic;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="voterecored_id"
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
	 * Return the value associated with the column: vote_time
	 */
	public java.util.Date getTime () {
		return time;
	}

	/**
	 * Set the value related to the column: vote_time
	 * @param time the vote_time value
	 */
	public void setTime (java.util.Date time) {
		this.time = time;
	}


	/**
	 * Return the value associated with the column: vote_ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: vote_ip
	 * @param ip the vote_ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: vote_cookie
	 */
	public java.lang.String getCookie () {
		return cookie;
	}

	/**
	 * Set the value related to the column: vote_cookie
	 * @param cookie the vote_cookie value
	 */
	public void setCookie (java.lang.String cookie) {
		this.cookie = cookie;
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


	/**
	 * Return the value associated with the column: votetopic_id
	 */
	public com.jeecms.cms.entity.assist.CmsVoteTopic getTopic () {
		return topic;
	}

	/**
	 * Set the value related to the column: votetopic_id
	 * @param topic the votetopic_id value
	 */
	public void setTopic (com.jeecms.cms.entity.assist.CmsVoteTopic topic) {
		this.topic = topic;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsVoteRecord)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsVoteRecord cmsVoteRecord = (com.jeecms.cms.entity.assist.CmsVoteRecord) obj;
			if (null == this.getId() || null == cmsVoteRecord.getId()) return false;
			else return (this.getId().equals(cmsVoteRecord.getId()));
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
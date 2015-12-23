package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jo_authentication table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jo_authentication"
 */

public abstract class BaseAuthentication  implements Serializable {

	public static String REF = "Authentication";
	public static String PROP_LOGIN_IP = "loginIp";
	public static String PROP_LOGIN_TIME = "loginTime";
	public static String PROP_UPDATE_TIME = "updateTime";
	public static String PROP_EMAIL = "email";
	public static String PROP_ID = "id";
	public static String PROP_USERNAME = "username";
	public static String PROP_UID = "uid";


	// constructors
	public BaseAuthentication () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseAuthentication (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseAuthentication (
		java.lang.String id,
		java.lang.Integer uid,
		java.lang.String username,
		java.util.Date loginTime,
		java.lang.String loginIp,
		java.util.Date updateTime) {

		this.setId(id);
		this.setUid(uid);
		this.setUsername(username);
		this.setLoginTime(loginTime);
		this.setLoginIp(loginIp);
		this.setUpdateTime(updateTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.Integer uid;
	private java.lang.String username;
	private java.lang.String email;
	private java.util.Date loginTime;
	private java.lang.String loginIp;
	private java.util.Date updateTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="authentication_id"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: userid
	 */
	public java.lang.Integer getUid () {
		return uid;
	}

	/**
	 * Set the value related to the column: userid
	 * @param uid the userid value
	 */
	public void setUid (java.lang.Integer uid) {
		this.uid = uid;
	}


	/**
	 * Return the value associated with the column: username
	 */
	public java.lang.String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: username
	 * @param username the username value
	 */
	public void setUsername (java.lang.String username) {
		this.username = username;
	}


	/**
	 * Return the value associated with the column: email
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: email
	 * @param email the email value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}


	/**
	 * Return the value associated with the column: login_time
	 */
	public java.util.Date getLoginTime () {
		return loginTime;
	}

	/**
	 * Set the value related to the column: login_time
	 * @param loginTime the login_time value
	 */
	public void setLoginTime (java.util.Date loginTime) {
		this.loginTime = loginTime;
	}


	/**
	 * Return the value associated with the column: login_ip
	 */
	public java.lang.String getLoginIp () {
		return loginIp;
	}

	/**
	 * Set the value related to the column: login_ip
	 * @param loginIp the login_ip value
	 */
	public void setLoginIp (java.lang.String loginIp) {
		this.loginIp = loginIp;
	}


	/**
	 * Return the value associated with the column: update_time
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}

	/**
	 * Set the value related to the column: update_time
	 * @param updateTime the update_time value
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.Authentication)) return false;
		else {
			com.jeecms.core.entity.Authentication authentication = (com.jeecms.core.entity.Authentication) obj;
			if (null == this.getId() || null == authentication.getId()) return false;
			else return (this.getId().equals(authentication.getId()));
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
package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_config"
 */

public abstract class BaseEmailConfig  implements Serializable {

	public static String REF = "EmailConfig";
	public static String PROP_PASSWORD = "password";
	public static String PROP_HOST = "host";
	public static String PROP_ENCODING = "encoding";
	public static String PROP_PERSONAL = "personal";
	public static String PROP_USERNAME = "username";


	// constructors
	public BaseEmailConfig () {
		initialize();
	}

	protected void initialize () {}



	// fields
	private java.lang.String host;
	private java.lang.String encoding;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String personal;






	/**
	 * Return the value associated with the column: email_host
	 */
	public java.lang.String getHost () {
		return host;
	}

	/**
	 * Set the value related to the column: email_host
	 * @param host the email_host value
	 */
	public void setHost (java.lang.String host) {
		this.host = host;
	}


	/**
	 * Return the value associated with the column: email_encoding
	 */
	public java.lang.String getEncoding () {
		return encoding;
	}

	/**
	 * Set the value related to the column: email_encoding
	 * @param encoding the email_encoding value
	 */
	public void setEncoding (java.lang.String encoding) {
		this.encoding = encoding;
	}


	/**
	 * Return the value associated with the column: email_username
	 */
	public java.lang.String getUsername () {
		return username;
	}

	/**
	 * Set the value related to the column: email_username
	 * @param username the email_username value
	 */
	public void setUsername (java.lang.String username) {
		this.username = username;
	}


	/**
	 * Return the value associated with the column: email_password
	 */
	public java.lang.String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: email_password
	 * @param password the email_password value
	 */
	public void setPassword (java.lang.String password) {
		this.password = password;
	}


	/**
	 * Return the value associated with the column: email_personal
	 */
	public java.lang.String getPersonal () {
		return personal;
	}

	/**
	 * Set the value related to the column: email_personal
	 * @param personal the email_personal value
	 */
	public void setPersonal (java.lang.String personal) {
		this.personal = personal;
	}






	public String toString () {
		return super.toString();
	}


}
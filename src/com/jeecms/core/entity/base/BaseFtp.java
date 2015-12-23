package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jo_ftp table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jo_ftp"
 */

public abstract class BaseFtp  implements Serializable {

	public static String REF = "Ftp";
	public static String PROP_TIMEOUT = "timeout";
	public static String PROP_PASSWORD = "password";
	public static String PROP_ENCODING = "encoding";
	public static String PROP_PATH = "path";
	public static String PROP_URL = "url";
	public static String PROP_IP = "ip";
	public static String PROP_PORT = "port";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_USERNAME = "username";


	// constructors
	public BaseFtp () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseFtp (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseFtp (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String ip,
		java.lang.Integer port,
		java.lang.String encoding,
		java.lang.String url) {

		this.setId(id);
		this.setName(name);
		this.setIp(ip);
		this.setPort(port);
		this.setEncoding(encoding);
		this.setUrl(url);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String ip;
	private java.lang.Integer port;
	private java.lang.String username;
	private java.lang.String password;
	private java.lang.String encoding;
	private java.lang.Integer timeout;
	private java.lang.String path;
	private java.lang.String url;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="ftp_id"
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
	 * Return the value associated with the column: ftp_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: ftp_name
	 * @param name the ftp_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: ip
	 */
	public java.lang.String getIp () {
		return ip;
	}

	/**
	 * Set the value related to the column: ip
	 * @param ip the ip value
	 */
	public void setIp (java.lang.String ip) {
		this.ip = ip;
	}


	/**
	 * Return the value associated with the column: port
	 */
	public java.lang.Integer getPort () {
		return port;
	}

	/**
	 * Set the value related to the column: port
	 * @param port the port value
	 */
	public void setPort (java.lang.Integer port) {
		this.port = port;
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
	 * Return the value associated with the column: password
	 */
	public java.lang.String getPassword () {
		return password;
	}

	/**
	 * Set the value related to the column: password
	 * @param password the password value
	 */
	public void setPassword (java.lang.String password) {
		this.password = password;
	}


	/**
	 * Return the value associated with the column: encoding
	 */
	public java.lang.String getEncoding () {
		return encoding;
	}

	/**
	 * Set the value related to the column: encoding
	 * @param encoding the encoding value
	 */
	public void setEncoding (java.lang.String encoding) {
		this.encoding = encoding;
	}


	/**
	 * Return the value associated with the column: timeout
	 */
	public java.lang.Integer getTimeout () {
		return timeout;
	}

	/**
	 * Set the value related to the column: timeout
	 * @param timeout the timeout value
	 */
	public void setTimeout (java.lang.Integer timeout) {
		this.timeout = timeout;
	}


	/**
	 * Return the value associated with the column: ftp_path
	 */
	public java.lang.String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: ftp_path
	 * @param path the ftp_path value
	 */
	public void setPath (java.lang.String path) {
		this.path = path;
	}


	/**
	 * Return the value associated with the column: url
	 */
	public java.lang.String getUrl () {
		return url;
	}

	/**
	 * Set the value related to the column: url
	 * @param url the url value
	 */
	public void setUrl (java.lang.String url) {
		this.url = url;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.Ftp)) return false;
		else {
			com.jeecms.core.entity.Ftp ftp = (com.jeecms.core.entity.Ftp) obj;
			if (null == this.getId() || null == ftp.getId()) return false;
			else return (this.getId().equals(ftp.getId()));
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
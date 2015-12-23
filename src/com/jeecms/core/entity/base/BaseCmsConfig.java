package com.jeecms.core.entity.base;

import java.io.Serializable;

import com.jeecms.core.entity.CmsConfigItem;


/**
 * This is an object that contains data related to the jc_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_config"
 */

public abstract class BaseCmsConfig  implements Serializable {

	public static String REF = "CmsConfig";
	public static String PROP_LOGIN_URL = "loginUrl";
	public static String PROP_COLOR = "color";
	public static String PROP_PROCESS_URL = "processUrl";
	public static String PROP_ALPHA = "alpha";
	public static String PROP_DEF_IMG = "defImg";
	public static String PROP_COUNT_CLEAR_TIME = "countClearTime";
	public static String PROP_COUNT_COPY_TIME = "countCopyTime";
	public static String PROP_PORT = "port";
	public static String PROP_DB_FILE_URI = "dbFileUri";
	public static String PROP_CONTEXT_PATH = "contextPath";
	public static String PROP_PASSWORD = "password";
	public static String PROP_OFFSET_X = "offsetX";
	public static String PROP_SERVLET_POINT = "servletPoint";
	public static String PROP_MIN_HEIGHT = "minHeight";
	public static String PROP_CONTENT = "content";
	public static String PROP_ON = "on";
	public static String PROP_DOWNLOAD_CODE = "downloadCode";
	public static String PROP_DOWNLOAD_TIME = "downloadTime";
	public static String PROP_HOST = "host";
	public static String PROP_POS = "pos";
	public static String PROP_MIN_WIDTH = "minWidth";
	public static String PROP_OFFSET_Y = "offsetY";
	public static String PROP_ENCODING = "encoding";
	public static String PROP_SIZE = "size";
	public static String PROP_IMAGE_PATH = "imagePath";
	public static String PROP_PERSONAL = "personal";
	public static String PROP_UPLOAD_TO_DB = "uploadToDb";
	public static String PROP_ID = "id";
	public static String PROP_USERNAME = "username";


	// constructors
	public BaseCmsConfig () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsConfig (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsConfig (
		java.lang.Integer id,
		java.lang.String dbFileUri,
		java.lang.Boolean uploadToDb,
		java.lang.String defImg,
		java.lang.String loginUrl,
		java.util.Date countClearTime,
		java.util.Date countCopyTime,
		java.lang.String downloadCode,
		java.lang.Integer downloadTime) {

		this.setId(id);
		this.setDbFileUri(dbFileUri);
		this.setUploadToDb(uploadToDb);
		this.setDefImg(defImg);
		this.setLoginUrl(loginUrl);
		this.setCountClearTime(countClearTime);
		this.setCountCopyTime(countCopyTime);
		this.setDownloadCode(downloadCode);
		this.setDownloadTime(downloadTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String contextPath;
	private java.lang.String servletPoint;
	private java.lang.Integer port;
	private java.lang.String dbFileUri;
	private java.lang.Boolean uploadToDb;
	private java.lang.String defImg;
	private java.lang.String loginUrl;
	private java.lang.String processUrl;
	private java.util.Date countClearTime;
	private java.util.Date countCopyTime;
	private java.lang.String downloadCode;
	private java.lang.Integer downloadTime;
	private java.lang.Boolean emailValidate;
	private java.lang.Boolean viewOnlyChecked;

	// components
	 com.jeecms.core.entity.MarkConfig m_markConfig;
	 com.jeecms.core.entity.EmailConfig m_emailConfig;

	// collections
	private java.util.Map<java.lang.String, java.lang.String> attr;
	private java.util.Set<CmsConfigItem> registerItems;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="config_id"
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
	 * Return the value associated with the column: context_path
	 */
	public java.lang.String getContextPath () {
		return contextPath;
	}

	/**
	 * Set the value related to the column: context_path
	 * @param contextPath the context_path value
	 */
	public void setContextPath (java.lang.String contextPath) {
		this.contextPath = contextPath;
	}


	/**
	 * Return the value associated with the column: servlet_point
	 */
	public java.lang.String getServletPoint () {
		return servletPoint;
	}

	/**
	 * Set the value related to the column: servlet_point
	 * @param servletPoint the servlet_point value
	 */
	public void setServletPoint (java.lang.String servletPoint) {
		this.servletPoint = servletPoint;
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
	 * Return the value associated with the column: db_file_uri
	 */
	public java.lang.String getDbFileUri () {
		return dbFileUri;
	}

	/**
	 * Set the value related to the column: db_file_uri
	 * @param dbFileUri the db_file_uri value
	 */
	public void setDbFileUri (java.lang.String dbFileUri) {
		this.dbFileUri = dbFileUri;
	}


	/**
	 * Return the value associated with the column: is_upload_to_db
	 */
	public java.lang.Boolean getUploadToDb () {
		return uploadToDb;
	}

	/**
	 * Set the value related to the column: is_upload_to_db
	 * @param uploadToDb the is_upload_to_db value
	 */
	public void setUploadToDb (java.lang.Boolean uploadToDb) {
		this.uploadToDb = uploadToDb;
	}


	/**
	 * Return the value associated with the column: def_img
	 */
	public java.lang.String getDefImg () {
		return defImg;
	}

	/**
	 * Set the value related to the column: def_img
	 * @param defImg the def_img value
	 */
	public void setDefImg (java.lang.String defImg) {
		this.defImg = defImg;
	}


	/**
	 * Return the value associated with the column: login_url
	 */
	public java.lang.String getLoginUrl () {
		return loginUrl;
	}

	/**
	 * Set the value related to the column: login_url
	 * @param loginUrl the login_url value
	 */
	public void setLoginUrl (java.lang.String loginUrl) {
		this.loginUrl = loginUrl;
	}


	/**
	 * Return the value associated with the column: process_url
	 */
	public java.lang.String getProcessUrl () {
		return processUrl;
	}

	/**
	 * Set the value related to the column: process_url
	 * @param processUrl the process_url value
	 */
	public void setProcessUrl (java.lang.String processUrl) {
		this.processUrl = processUrl;
	}


	/**
	 * Return the value associated with the column: count_clear_time
	 */
	public java.util.Date getCountClearTime () {
		return countClearTime;
	}

	/**
	 * Set the value related to the column: count_clear_time
	 * @param countClearTime the count_clear_time value
	 */
	public void setCountClearTime (java.util.Date countClearTime) {
		this.countClearTime = countClearTime;
	}


	/**
	 * Return the value associated with the column: count_copy_time
	 */
	public java.util.Date getCountCopyTime () {
		return countCopyTime;
	}

	/**
	 * Set the value related to the column: count_copy_time
	 * @param countCopyTime the count_copy_time value
	 */
	public void setCountCopyTime (java.util.Date countCopyTime) {
		this.countCopyTime = countCopyTime;
	}


	/**
	 * Return the value associated with the column: download_code
	 */
	public java.lang.String getDownloadCode () {
		return downloadCode;
	}

	/**
	 * Set the value related to the column: download_code
	 * @param downloadCode the download_code value
	 */
	public void setDownloadCode (java.lang.String downloadCode) {
		this.downloadCode = downloadCode;
	}


	/**
	 * Return the value associated with the column: download_time
	 */
	public java.lang.Integer getDownloadTime () {
		return downloadTime;
	}

	/**
	 * Set the value related to the column: download_time
	 * @param downloadTime the download_time value
	 */
	public void setDownloadTime (java.lang.Integer downloadTime) {
		this.downloadTime = downloadTime;
	}
	

	public java.lang.Boolean getEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(java.lang.Boolean emailValidate) {
		this.emailValidate = emailValidate;
	}

	public java.lang.Boolean getViewOnlyChecked() {
		return viewOnlyChecked;
	}

	public void setViewOnlyChecked(java.lang.Boolean viewOnlyChecked) {
		this.viewOnlyChecked = viewOnlyChecked;
	}
	
	public com.jeecms.core.entity.MarkConfig getMarkConfig () {
		return m_markConfig;
	}

	/**
	 * Set the value related to the column: ${prop.Column}
	 * @param m_markConfig the ${prop.Column} value
	 */
	public void setMarkConfig (com.jeecms.core.entity.MarkConfig m_markConfig) {
		this.m_markConfig = m_markConfig;
	}


	public com.jeecms.core.entity.EmailConfig getEmailConfig () {
		return m_emailConfig;
	}

	/**
	 * Set the value related to the column: ${prop.Column}
	 * @param m_emailConfig the ${prop.Column} value
	 */
	public void setEmailConfig (com.jeecms.core.entity.EmailConfig m_emailConfig) {
		this.m_emailConfig = m_emailConfig;
	}


	/**
	 * Return the value associated with the column: attr
	 */
	public java.util.Map<java.lang.String, java.lang.String> getAttr () {
		return attr;
	}

	/**
	 * Set the value related to the column: attr
	 * @param attr the attr value
	 */
	public void setAttr (java.util.Map<java.lang.String, java.lang.String> attr) {
		this.attr = attr;
	}

	public java.util.Set<CmsConfigItem> getRegisterItems() {
		return registerItems;
	}

	public void setRegisterItems(java.util.Set<CmsConfigItem> registerItems) {
		this.registerItems = registerItems;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsConfig)) return false;
		else {
			com.jeecms.core.entity.CmsConfig cmsConfig = (com.jeecms.core.entity.CmsConfig) obj;
			if (null == this.getId() || null == cmsConfig.getId()) return false;
			else return (this.getId().equals(cmsConfig.getId()));
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
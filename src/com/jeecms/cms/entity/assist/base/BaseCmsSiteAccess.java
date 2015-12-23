package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_site_access table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_site_access"
 */

public abstract class BaseCmsSiteAccess  implements Serializable {

	public static String REF = "CmsSiteAccess";
	public static String PROP_SESSION_ID = "sessionId";
	public static String PROP_ENTRY_PAGE = "entryPage";
	public static String PROP_IP = "ip";
	public static String PROP_SITE = "site";
	public static String PROP_KEYWORD = "keyword";
	public static String PROP_AREA = "area";
	public static String PROP_VISIT_PAGE_COUNT = "visitPageCount";
	public static String PROP_ENGINE = "engine";
	public static String PROP_BROWSER = "browser";
	public static String PROP_EXTERNAL_LINK = "externalLink";
	public static String PROP_VISIT_SECOND = "visitSecond";
	public static String PROP_ID = "id";
	public static String PROP_ACCESS_TIME = "accessTime";
	public static String PROP_ACCESS_DATE = "accessDate";
	public static String PROP_OPERATING_SYSTEM = "operatingSystem";
	public static String PROP_LAST_STOP_PAGE = "lastStopPage";
	public static String PROP_ACCESS_SOURCE = "accessSource";


	// constructors
	public BaseCmsSiteAccess () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsSiteAccess (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsSiteAccess (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String sessionId,
		java.util.Date accessTime,
		java.util.Date accessDate) {

		this.setId(id);
		this.setSite(site);
		this.setSessionId(sessionId);
		this.setAccessTime(accessTime);
		this.setAccessDate(accessDate);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String sessionId;
	private java.util.Date accessTime;
	private java.util.Date accessDate;
	private java.lang.String ip;
	private java.lang.String area;
	private java.lang.String accessSource;
	private java.lang.String externalLink;
	private java.lang.String engine;
	private java.lang.String entryPage;
	private java.lang.String lastStopPage;
	private java.lang.Integer visitSecond;
	private java.lang.Integer visitPageCount;
	private java.lang.String operatingSystem;
	private java.lang.String browser;
	private java.lang.String keyword;

	// many to one
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="access_id"
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
	 * Return the value associated with the column: session_id
	 */
	public java.lang.String getSessionId () {
		return sessionId;
	}

	/**
	 * Set the value related to the column: session_id
	 * @param sessionId the session_id value
	 */
	public void setSessionId (java.lang.String sessionId) {
		this.sessionId = sessionId;
	}


	/**
	 * Return the value associated with the column: access_time
	 */
	public java.util.Date getAccessTime () {
		return accessTime;
	}

	/**
	 * Set the value related to the column: access_time
	 * @param accessTime the access_time value
	 */
	public void setAccessTime (java.util.Date accessTime) {
		this.accessTime = accessTime;
	}


	/**
	 * Return the value associated with the column: access_date
	 */
	public java.util.Date getAccessDate () {
		return accessDate;
	}

	/**
	 * Set the value related to the column: access_date
	 * @param accessDate the access_date value
	 */
	public void setAccessDate (java.util.Date accessDate) {
		this.accessDate = accessDate;
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
	 * Return the value associated with the column: area
	 */
	public java.lang.String getArea () {
		return area;
	}

	/**
	 * Set the value related to the column: area
	 * @param area the area value
	 */
	public void setArea (java.lang.String area) {
		this.area = area;
	}


	/**
	 * Return the value associated with the column: access_source
	 */
	public java.lang.String getAccessSource () {
		return accessSource;
	}

	/**
	 * Set the value related to the column: access_source
	 * @param accessSource the access_source value
	 */
	public void setAccessSource (java.lang.String accessSource) {
		this.accessSource = accessSource;
	}


	/**
	 * Return the value associated with the column: external_link
	 */
	public java.lang.String getExternalLink () {
		return externalLink;
	}

	/**
	 * Set the value related to the column: external_link
	 * @param externalLink the external_link value
	 */
	public void setExternalLink (java.lang.String externalLink) {
		this.externalLink = externalLink;
	}


	/**
	 * Return the value associated with the column: engine
	 */
	public java.lang.String getEngine () {
		return engine;
	}

	/**
	 * Set the value related to the column: engine
	 * @param engine the engine value
	 */
	public void setEngine (java.lang.String engine) {
		this.engine = engine;
	}


	/**
	 * Return the value associated with the column: entry_page
	 */
	public java.lang.String getEntryPage () {
		return entryPage;
	}

	/**
	 * Set the value related to the column: entry_page
	 * @param entryPage the entry_page value
	 */
	public void setEntryPage (java.lang.String entryPage) {
		this.entryPage = entryPage;
	}


	/**
	 * Return the value associated with the column: last_stop_page
	 */
	public java.lang.String getLastStopPage () {
		return lastStopPage;
	}

	/**
	 * Set the value related to the column: last_stop_page
	 * @param lastStopPage the last_stop_page value
	 */
	public void setLastStopPage (java.lang.String lastStopPage) {
		this.lastStopPage = lastStopPage;
	}


	/**
	 * Return the value associated with the column: visit_second
	 */
	public java.lang.Integer getVisitSecond () {
		return visitSecond;
	}

	/**
	 * Set the value related to the column: visit_second
	 * @param visitSecond the visit_second value
	 */
	public void setVisitSecond (java.lang.Integer visitSecond) {
		this.visitSecond = visitSecond;
	}


	/**
	 * Return the value associated with the column: visit_page_count
	 */
	public java.lang.Integer getVisitPageCount () {
		return visitPageCount;
	}

	/**
	 * Set the value related to the column: visit_page_count
	 * @param visitPageCount the visit_page_count value
	 */
	public void setVisitPageCount (java.lang.Integer visitPageCount) {
		this.visitPageCount = visitPageCount;
	}


	/**
	 * Return the value associated with the column: operating_system
	 */
	public java.lang.String getOperatingSystem () {
		return operatingSystem;
	}

	/**
	 * Set the value related to the column: operating_system
	 * @param operatingSystem the operating_system value
	 */
	public void setOperatingSystem (java.lang.String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}


	/**
	 * Return the value associated with the column: browser
	 */
	public java.lang.String getBrowser () {
		return browser;
	}

	/**
	 * Set the value related to the column: browser
	 * @param browser the browser value
	 */
	public void setBrowser (java.lang.String browser) {
		this.browser = browser;
	}


	/**
	 * Return the value associated with the column: keyword
	 */
	public java.lang.String getKeyword () {
		return keyword;
	}

	/**
	 * Set the value related to the column: keyword
	 * @param keyword the keyword value
	 */
	public void setKeyword (java.lang.String keyword) {
		this.keyword = keyword;
	}


	/**
	 * Return the value associated with the column: site_id
	 */
	public com.jeecms.core.entity.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site_id
	 * @param site the site_id value
	 */
	public void setSite (com.jeecms.core.entity.CmsSite site) {
		this.site = site;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsSiteAccess)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsSiteAccess cmsSiteAccess = (com.jeecms.cms.entity.assist.CmsSiteAccess) obj;
			if (null == this.getId() || null == cmsSiteAccess.getId()) return false;
			else return (this.getId().equals(cmsSiteAccess.getId()));
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
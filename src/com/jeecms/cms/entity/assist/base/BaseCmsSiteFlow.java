package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_site_flow table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_site_flow"
 */

public abstract class BaseCmsSiteFlow  implements Serializable {

	public static String REF = "CmsSiteFlow";
	public static String PROP_SESSION_ID = "sessionId";
	public static String PROP_SITE = "site";
	public static String PROP_REFERER_PAGE = "refererPage";
	public static String PROP_AREA = "area";
	public static String PROP_ID = "id";
	public static String PROP_ACCESS_TIME = "accessTime";
	public static String PROP_ACCESS_PAGE = "accessPage";
	public static String PROP_ACCESS_DATE = "accessDate";
	public static String PROP_ACCESS_IP = "accessIp";
	public static String PROP_REFERER_WEB_SITE = "refererWebSite";
	public static String PROP_REFERER_KEYWORD = "refererKeyword";


	// constructors
	public BaseCmsSiteFlow () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsSiteFlow (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsSiteFlow (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String accessIp,
		java.lang.String accessDate,
		java.lang.String accessPage,
		java.lang.String sessionId) {

		this.setId(id);
		this.setSite(site);
		this.setAccessIp(accessIp);
		this.setAccessDate(accessDate);
		this.setAccessPage(accessPage);
		this.setSessionId(sessionId);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String accessIp;
	private java.lang.String accessDate;
	private java.util.Date accessTime;
	private java.lang.String accessPage;
	private java.lang.String refererWebSite;
	private java.lang.String refererPage;
	private java.lang.String refererKeyword;
	private java.lang.String area;
	private java.lang.String sessionId;

	// many to one
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="flow_id"
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
	 * Return the value associated with the column: access_ip
	 */
	public java.lang.String getAccessIp () {
		return accessIp;
	}

	/**
	 * Set the value related to the column: access_ip
	 * @param accessIp the access_ip value
	 */
	public void setAccessIp (java.lang.String accessIp) {
		this.accessIp = accessIp;
	}


	/**
	 * Return the value associated with the column: access_date
	 */
	public java.lang.String getAccessDate () {
		return accessDate;
	}

	/**
	 * Set the value related to the column: access_date
	 * @param accessDate the access_date value
	 */
	public void setAccessDate (java.lang.String accessDate) {
		this.accessDate = accessDate;
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
	 * Return the value associated with the column: access_page
	 */
	public java.lang.String getAccessPage () {
		return accessPage;
	}

	/**
	 * Set the value related to the column: access_page
	 * @param accessPage the access_page value
	 */
	public void setAccessPage (java.lang.String accessPage) {
		this.accessPage = accessPage;
	}


	/**
	 * Return the value associated with the column: referer_website
	 */
	public java.lang.String getRefererWebSite () {
		return refererWebSite;
	}

	/**
	 * Set the value related to the column: referer_website
	 * @param refererWebSite the referer_website value
	 */
	public void setRefererWebSite (java.lang.String refererWebSite) {
		this.refererWebSite = refererWebSite;
	}


	/**
	 * Return the value associated with the column: referer_page
	 */
	public java.lang.String getRefererPage () {
		return refererPage;
	}

	/**
	 * Set the value related to the column: referer_page
	 * @param refererPage the referer_page value
	 */
	public void setRefererPage (java.lang.String refererPage) {
		this.refererPage = refererPage;
	}


	/**
	 * Return the value associated with the column: referer_keyword
	 */
	public java.lang.String getRefererKeyword () {
		return refererKeyword;
	}

	/**
	 * Set the value related to the column: referer_keyword
	 * @param refererKeyword the referer_keyword value
	 */
	public void setRefererKeyword (java.lang.String refererKeyword) {
		this.refererKeyword = refererKeyword;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsSiteFlow)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsSiteFlow cmsSiteFlow = (com.jeecms.cms.entity.assist.CmsSiteFlow) obj;
			if (null == this.getId() || null == cmsSiteFlow.getId()) return false;
			else return (this.getId().equals(cmsSiteFlow.getId()));
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
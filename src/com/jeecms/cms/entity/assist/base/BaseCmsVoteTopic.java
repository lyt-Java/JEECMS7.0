package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_vote_topic table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_vote_topic"
 */

public abstract class BaseCmsVoteTopic  implements Serializable {

	public static String REF = "CmsVoteTopic";
	public static String PROP_MULTI_SELECT = "multiSelect";
	public static String PROP_RESTRICT_COOKIE = "restrictCookie";
	public static String PROP_SITE = "site";
	public static String PROP_DISABLED = "disabled";
	public static String PROP_DEF = "def";
	public static String PROP_RESTRICT_MEMBER = "restrictMember";
	public static String PROP_RESTRICT_IP = "restrictIp";
	public static String PROP_TOTAL_COUNT = "totalCount";
	public static String PROP_REPEATE_HOUR = "repeateHour";
	public static String PROP_END_TIME = "endTime";
	public static String PROP_START_TIME = "startTime";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_TITLE = "title";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsVoteTopic () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsVoteTopic (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsVoteTopic (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String title,
		java.lang.Integer totalCount,
		java.lang.Integer multiSelect,
		java.lang.Boolean restrictMember,
		java.lang.Boolean restrictIp,
		java.lang.Boolean restrictCookie,
		java.lang.Boolean disabled,
		java.lang.Boolean def) {

		this.setId(id);
		this.setSite(site);
		this.setTitle(title);
		this.setTotalCount(totalCount);
		this.setMultiSelect(multiSelect);
		this.setRestrictMember(restrictMember);
		this.setRestrictIp(restrictIp);
		this.setRestrictCookie(restrictCookie);
		this.setDisabled(disabled);
		this.setDef(def);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String title;
	private java.lang.String description;
	private java.util.Date startTime;
	private java.util.Date endTime;
	private java.lang.Integer repeateHour;
	private java.lang.Integer totalCount;
	private java.lang.Integer multiSelect;
	private java.lang.Boolean restrictMember;
	private java.lang.Boolean restrictIp;
	private java.lang.Boolean restrictCookie;
	private java.lang.Boolean disabled;
	private java.lang.Boolean def;

	// many to one
	private com.jeecms.core.entity.CmsSite site;

	// collections
	private java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> items;
	private java.util.Set<com.jeecms.cms.entity.assist.CmsVoteSubTopic> subtopics;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="votetopic_id"
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
	 * Return the value associated with the column: description
	 */
	public java.lang.String getDescription () {
		return description;
	}

	/**
	 * Set the value related to the column: description
	 * @param description the description value
	 */
	public void setDescription (java.lang.String description) {
		this.description = description;
	}


	/**
	 * Return the value associated with the column: start_time
	 */
	public java.util.Date getStartTime () {
		return startTime;
	}

	/**
	 * Set the value related to the column: start_time
	 * @param startTime the start_time value
	 */
	public void setStartTime (java.util.Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * Return the value associated with the column: end_time
	 */
	public java.util.Date getEndTime () {
		return endTime;
	}

	/**
	 * Set the value related to the column: end_time
	 * @param endTime the end_time value
	 */
	public void setEndTime (java.util.Date endTime) {
		this.endTime = endTime;
	}


	/**
	 * Return the value associated with the column: repeate_hour
	 */
	public java.lang.Integer getRepeateHour () {
		return repeateHour;
	}

	/**
	 * Set the value related to the column: repeate_hour
	 * @param repeateHour the repeate_hour value
	 */
	public void setRepeateHour (java.lang.Integer repeateHour) {
		this.repeateHour = repeateHour;
	}


	/**
	 * Return the value associated with the column: total_count
	 */
	public java.lang.Integer getTotalCount () {
		return totalCount;
	}

	/**
	 * Set the value related to the column: total_count
	 * @param totalCount the total_count value
	 */
	public void setTotalCount (java.lang.Integer totalCount) {
		this.totalCount = totalCount;
	}


	/**
	 * Return the value associated with the column: multi_select
	 */
	public java.lang.Integer getMultiSelect () {
		return multiSelect;
	}

	/**
	 * Set the value related to the column: multi_select
	 * @param multiSelect the multi_select value
	 */
	public void setMultiSelect (java.lang.Integer multiSelect) {
		this.multiSelect = multiSelect;
	}


	/**
	 * Return the value associated with the column: is_restrict_member
	 */
	public java.lang.Boolean getRestrictMember () {
		return restrictMember;
	}

	/**
	 * Set the value related to the column: is_restrict_member
	 * @param restrictMember the is_restrict_member value
	 */
	public void setRestrictMember (java.lang.Boolean restrictMember) {
		this.restrictMember = restrictMember;
	}


	/**
	 * Return the value associated with the column: is_restrict_ip
	 */
	public java.lang.Boolean getRestrictIp () {
		return restrictIp;
	}

	/**
	 * Set the value related to the column: is_restrict_ip
	 * @param restrictIp the is_restrict_ip value
	 */
	public void setRestrictIp (java.lang.Boolean restrictIp) {
		this.restrictIp = restrictIp;
	}


	/**
	 * Return the value associated with the column: is_restrict_cookie
	 */
	public java.lang.Boolean getRestrictCookie () {
		return restrictCookie;
	}

	/**
	 * Set the value related to the column: is_restrict_cookie
	 * @param restrictCookie the is_restrict_cookie value
	 */
	public void setRestrictCookie (java.lang.Boolean restrictCookie) {
		this.restrictCookie = restrictCookie;
	}


	/**
	 * Return the value associated with the column: is_disabled
	 */
	public java.lang.Boolean getDisabled () {
		return disabled;
	}

	/**
	 * Set the value related to the column: is_disabled
	 * @param disabled the is_disabled value
	 */
	public void setDisabled (java.lang.Boolean disabled) {
		this.disabled = disabled;
	}


	/**
	 * Return the value associated with the column: is_def
	 */
	public java.lang.Boolean getDef () {
		return def;
	}

	/**
	 * Set the value related to the column: is_def
	 * @param def the is_def value
	 */
	public void setDef (java.lang.Boolean def) {
		this.def = def;
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


	/**
	 * Return the value associated with the column: items
	 */
	public java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> getItems () {
		return items;
	}

	/**
	 * Set the value related to the column: items
	 * @param items the items value
	 */
	public void setItems (java.util.Set<com.jeecms.cms.entity.assist.CmsVoteItem> items) {
		this.items = items;
	}

	public java.util.Set<com.jeecms.cms.entity.assist.CmsVoteSubTopic> getSubtopics() {
		return subtopics;
	}

	public void setSubtopics(
			java.util.Set<com.jeecms.cms.entity.assist.CmsVoteSubTopic> subtopics) {
		this.subtopics = subtopics;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsVoteTopic)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsVoteTopic cmsVoteTopic = (com.jeecms.cms.entity.assist.CmsVoteTopic) obj;
			if (null == this.getId() || null == cmsVoteTopic.getId()) return false;
			else return (this.getId().equals(cmsVoteTopic.getId()));
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
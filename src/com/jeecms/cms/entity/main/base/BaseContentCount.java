package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content_count table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content_count"
 */

public abstract class BaseContentCount  implements Serializable {

	public static String REF = "ContentCount";
	public static String PROP_COMMENTS_WEEK = "commentsWeek";
	public static String PROP_VIEWS_WEEK = "viewsWeek";
	public static String PROP_COMMENTS_MONTH = "commentsMonth";
	public static String PROP_DOWNLOADS = "downloads";
	public static String PROP_DOWNLOADS_WEEK = "downloadsWeek";
	public static String PROP_UPS_WEEK = "upsWeek";
	public static String PROP_UPS_MONTH = "upsMonth";
	public static String PROP_VIEWS_MONTH = "viewsMonth";
	public static String PROP_COMMENTS_DAY = "commentsDay";
	public static String PROP_DOWNS = "downs";
	public static String PROP_VIEWS_DAY = "viewsDay";
	public static String PROP_DOWNLOADS_MONTH = "downloadsMonth";
	public static String PROP_COMMENTS = "comments";
	public static String PROP_UPS = "ups";
	public static String PROP_UPS_DAY = "upsDay";
	public static String PROP_VIEWS = "views";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";
	public static String PROP_DOWNLOADS_DAY = "downloadsDay";


	// constructors
	public BaseContentCount () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseContentCount (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseContentCount (
		java.lang.Integer id,
		java.lang.Integer views,
		java.lang.Integer viewsMonth,
		java.lang.Integer viewsWeek,
		java.lang.Integer viewsDay,
		java.lang.Integer comments,
		java.lang.Integer commentsMonth,
		java.lang.Short commentsWeek,
		java.lang.Short commentsDay,
		java.lang.Integer downloads,
		java.lang.Integer downloadsMonth,
		java.lang.Short downloadsWeek,
		java.lang.Short downloadsDay,
		java.lang.Integer ups,
		java.lang.Integer upsMonth,
		java.lang.Short upsWeek,
		java.lang.Short upsDay,
		java.lang.Integer downs) {

		this.setId(id);
		this.setViews(views);
		this.setViewsMonth(viewsMonth);
		this.setViewsWeek(viewsWeek);
		this.setViewsDay(viewsDay);
		this.setComments(comments);
		this.setCommentsMonth(commentsMonth);
		this.setCommentsWeek(commentsWeek);
		this.setCommentsDay(commentsDay);
		this.setDownloads(downloads);
		this.setDownloadsMonth(downloadsMonth);
		this.setDownloadsWeek(downloadsWeek);
		this.setDownloadsDay(downloadsDay);
		this.setUps(ups);
		this.setUpsMonth(upsMonth);
		this.setUpsWeek(upsWeek);
		this.setUpsDay(upsDay);
		this.setDowns(downs);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.Integer views;
	private java.lang.Integer viewsMonth;
	private java.lang.Integer viewsWeek;
	private java.lang.Integer viewsDay;
	private java.lang.Integer comments;
	private java.lang.Integer commentsMonth;
	private java.lang.Short commentsWeek;
	private java.lang.Short commentsDay;
	private java.lang.Integer downloads;
	private java.lang.Integer downloadsMonth;
	private java.lang.Short downloadsWeek;
	private java.lang.Short downloadsDay;
	private java.lang.Integer ups;
	private java.lang.Integer upsMonth;
	private java.lang.Short upsWeek;
	private java.lang.Short upsDay;
	private java.lang.Integer downs;

	// one to one
	private com.jeecms.cms.entity.main.Content content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="content_id"
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
	 * Return the value associated with the column: views
	 */
	public java.lang.Integer getViews () {
		return views;
	}

	/**
	 * Set the value related to the column: views
	 * @param views the views value
	 */
	public void setViews (java.lang.Integer views) {
		this.views = views;
	}


	/**
	 * Return the value associated with the column: views_month
	 */
	public java.lang.Integer getViewsMonth () {
		return viewsMonth;
	}

	/**
	 * Set the value related to the column: views_month
	 * @param viewsMonth the views_month value
	 */
	public void setViewsMonth (java.lang.Integer viewsMonth) {
		this.viewsMonth = viewsMonth;
	}


	/**
	 * Return the value associated with the column: views_week
	 */
	public java.lang.Integer getViewsWeek () {
		return viewsWeek;
	}

	/**
	 * Set the value related to the column: views_week
	 * @param viewsWeek the views_week value
	 */
	public void setViewsWeek (java.lang.Integer viewsWeek) {
		this.viewsWeek = viewsWeek;
	}


	/**
	 * Return the value associated with the column: views_day
	 */
	public java.lang.Integer getViewsDay () {
		return viewsDay;
	}

	/**
	 * Set the value related to the column: views_day
	 * @param viewsDay the views_day value
	 */
	public void setViewsDay (java.lang.Integer viewsDay) {
		this.viewsDay = viewsDay;
	}


	/**
	 * Return the value associated with the column: comments
	 */
	public java.lang.Integer getComments () {
		return comments;
	}

	/**
	 * Set the value related to the column: comments
	 * @param comments the comments value
	 */
	public void setComments (java.lang.Integer comments) {
		this.comments = comments;
	}


	/**
	 * Return the value associated with the column: comments_month
	 */
	public java.lang.Integer getCommentsMonth () {
		return commentsMonth;
	}

	/**
	 * Set the value related to the column: comments_month
	 * @param commentsMonth the comments_month value
	 */
	public void setCommentsMonth (java.lang.Integer commentsMonth) {
		this.commentsMonth = commentsMonth;
	}


	/**
	 * Return the value associated with the column: comments_week
	 */
	public java.lang.Short getCommentsWeek () {
		return commentsWeek;
	}

	/**
	 * Set the value related to the column: comments_week
	 * @param commentsWeek the comments_week value
	 */
	public void setCommentsWeek (java.lang.Short commentsWeek) {
		this.commentsWeek = commentsWeek;
	}


	/**
	 * Return the value associated with the column: comments_day
	 */
	public java.lang.Short getCommentsDay () {
		return commentsDay;
	}

	/**
	 * Set the value related to the column: comments_day
	 * @param commentsDay the comments_day value
	 */
	public void setCommentsDay (java.lang.Short commentsDay) {
		this.commentsDay = commentsDay;
	}


	/**
	 * Return the value associated with the column: downloads
	 */
	public java.lang.Integer getDownloads () {
		return downloads;
	}

	/**
	 * Set the value related to the column: downloads
	 * @param downloads the downloads value
	 */
	public void setDownloads (java.lang.Integer downloads) {
		this.downloads = downloads;
	}


	/**
	 * Return the value associated with the column: downloads_month
	 */
	public java.lang.Integer getDownloadsMonth () {
		return downloadsMonth;
	}

	/**
	 * Set the value related to the column: downloads_month
	 * @param downloadsMonth the downloads_month value
	 */
	public void setDownloadsMonth (java.lang.Integer downloadsMonth) {
		this.downloadsMonth = downloadsMonth;
	}


	/**
	 * Return the value associated with the column: downloads_week
	 */
	public java.lang.Short getDownloadsWeek () {
		return downloadsWeek;
	}

	/**
	 * Set the value related to the column: downloads_week
	 * @param downloadsWeek the downloads_week value
	 */
	public void setDownloadsWeek (java.lang.Short downloadsWeek) {
		this.downloadsWeek = downloadsWeek;
	}


	/**
	 * Return the value associated with the column: downloads_day
	 */
	public java.lang.Short getDownloadsDay () {
		return downloadsDay;
	}

	/**
	 * Set the value related to the column: downloads_day
	 * @param downloadsDay the downloads_day value
	 */
	public void setDownloadsDay (java.lang.Short downloadsDay) {
		this.downloadsDay = downloadsDay;
	}


	/**
	 * Return the value associated with the column: ups
	 */
	public java.lang.Integer getUps () {
		return ups;
	}

	/**
	 * Set the value related to the column: ups
	 * @param ups the ups value
	 */
	public void setUps (java.lang.Integer ups) {
		this.ups = ups;
	}


	/**
	 * Return the value associated with the column: ups_month
	 */
	public java.lang.Integer getUpsMonth () {
		return upsMonth;
	}

	/**
	 * Set the value related to the column: ups_month
	 * @param upsMonth the ups_month value
	 */
	public void setUpsMonth (java.lang.Integer upsMonth) {
		this.upsMonth = upsMonth;
	}


	/**
	 * Return the value associated with the column: ups_week
	 */
	public java.lang.Short getUpsWeek () {
		return upsWeek;
	}

	/**
	 * Set the value related to the column: ups_week
	 * @param upsWeek the ups_week value
	 */
	public void setUpsWeek (java.lang.Short upsWeek) {
		this.upsWeek = upsWeek;
	}


	/**
	 * Return the value associated with the column: ups_day
	 */
	public java.lang.Short getUpsDay () {
		return upsDay;
	}

	/**
	 * Set the value related to the column: ups_day
	 * @param upsDay the ups_day value
	 */
	public void setUpsDay (java.lang.Short upsDay) {
		this.upsDay = upsDay;
	}


	/**
	 * Return the value associated with the column: downs
	 */
	public java.lang.Integer getDowns () {
		return downs;
	}

	/**
	 * Set the value related to the column: downs
	 * @param downs the downs value
	 */
	public void setDowns (java.lang.Integer downs) {
		this.downs = downs;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public com.jeecms.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (com.jeecms.cms.entity.main.Content content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ContentCount)) return false;
		else {
			com.jeecms.cms.entity.main.ContentCount contentCount = (com.jeecms.cms.entity.main.ContentCount) obj;
			if (null == this.getId() || null == contentCount.getId()) return false;
			else return (this.getId().equals(contentCount.getId()));
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
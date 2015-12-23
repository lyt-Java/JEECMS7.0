package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_comment table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_comment"
 */

public abstract class BaseCmsComment  implements Serializable {

	public static String REF = "CmsComment";
	public static String PROP_RECOMMEND = "recommend";
	public static String PROP_COMMENT_USER = "commentUser";
	public static String PROP_REPLAY_USER = "replayUser";
	public static String PROP_SITE = "site";
	public static String PROP_REPLAY_TIME = "replayTime";
	public static String PROP_CREATE_TIME = "createTime";
	public static String PROP_DOWNS = "downs";
	public static String PROP_UPS = "ups";
	public static String PROP_CHECKED = "checked";
	public static String PROP_COMMENT_EXT = "commentExt";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsComment () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsComment (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsComment (
		java.lang.Integer id,
		com.jeecms.cms.entity.main.Content content,
		com.jeecms.core.entity.CmsSite site,
		java.util.Date createTime,
		java.lang.Short ups,
		java.lang.Short downs,
		java.lang.Boolean recommend,
		java.lang.Boolean checked) {

		this.setId(id);
		this.setContent(content);
		this.setSite(site);
		this.setCreateTime(createTime);
		this.setUps(ups);
		this.setDowns(downs);
		this.setRecommend(recommend);
		this.setChecked(checked);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.util.Date createTime;
	private java.util.Date replayTime;
	private java.lang.Short ups;
	private java.lang.Short downs;
	private java.lang.Boolean recommend;
	private java.lang.Boolean checked;

	// one to one
	private com.jeecms.cms.entity.assist.CmsCommentExt commentExt;

	// many to one
	private com.jeecms.core.entity.CmsUser replayUser;
	private com.jeecms.cms.entity.main.Content content;
	private com.jeecms.core.entity.CmsUser commentUser;
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="comment_id"
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
	 * Return the value associated with the column: create_time
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: create_time
	 * @param createTime the create_time value
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}


	/**
	 * Return the value associated with the column: reply_time
	 */
	public java.util.Date getReplayTime () {
		return replayTime;
	}

	/**
	 * Set the value related to the column: reply_time
	 * @param replayTime the reply_time value
	 */
	public void setReplayTime (java.util.Date replayTime) {
		this.replayTime = replayTime;
	}


	/**
	 * Return the value associated with the column: ups
	 */
	public java.lang.Short getUps () {
		return ups;
	}

	/**
	 * Set the value related to the column: ups
	 * @param ups the ups value
	 */
	public void setUps (java.lang.Short ups) {
		this.ups = ups;
	}


	/**
	 * Return the value associated with the column: downs
	 */
	public java.lang.Short getDowns () {
		return downs;
	}

	/**
	 * Set the value related to the column: downs
	 * @param downs the downs value
	 */
	public void setDowns (java.lang.Short downs) {
		this.downs = downs;
	}


	/**
	 * Return the value associated with the column: is_recommend
	 */
	public java.lang.Boolean getRecommend () {
		return recommend;
	}

	/**
	 * Set the value related to the column: is_recommend
	 * @param recommend the is_recommend value
	 */
	public void setRecommend (java.lang.Boolean recommend) {
		this.recommend = recommend;
	}


	/**
	 * Return the value associated with the column: is_checked
	 */
	public java.lang.Boolean getChecked () {
		return checked;
	}

	/**
	 * Set the value related to the column: is_checked
	 * @param checked the is_checked value
	 */
	public void setChecked (java.lang.Boolean checked) {
		this.checked = checked;
	}
	/**
	 * Return the value associated with the column: commentExt
	 */
	public com.jeecms.cms.entity.assist.CmsCommentExt getCommentExt () {
		return commentExt;
	}

	/**
	 * Set the value related to the column: commentExt
	 * @param commentExt the commentExt value
	 */
	public void setCommentExt (com.jeecms.cms.entity.assist.CmsCommentExt commentExt) {
		this.commentExt = commentExt;
	}


	/**
	 * Return the value associated with the column: reply_user_id
	 */
	public com.jeecms.core.entity.CmsUser getReplayUser () {
		return replayUser;
	}

	/**
	 * Set the value related to the column: reply_user_id
	 * @param replayUser the reply_user_id value
	 */
	public void setReplayUser (com.jeecms.core.entity.CmsUser replayUser) {
		this.replayUser = replayUser;
	}


	/**
	 * Return the value associated with the column: content_id
	 */
	public com.jeecms.cms.entity.main.Content getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content_id
	 * @param content the content_id value
	 */
	public void setContent (com.jeecms.cms.entity.main.Content content) {
		this.content = content;
	}


	/**
	 * Return the value associated with the column: comment_user_id
	 */
	public com.jeecms.core.entity.CmsUser getCommentUser () {
		return commentUser;
	}

	/**
	 * Set the value related to the column: comment_user_id
	 * @param commentUser the comment_user_id value
	 */
	public void setCommentUser (com.jeecms.core.entity.CmsUser commentUser) {
		this.commentUser = commentUser;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsComment)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsComment cmsComment = (com.jeecms.cms.entity.assist.CmsComment) obj;
			if (null == this.getId() || null == cmsComment.getId()) return false;
			else return (this.getId().equals(cmsComment.getId()));
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
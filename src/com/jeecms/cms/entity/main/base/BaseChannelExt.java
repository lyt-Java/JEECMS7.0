package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_channel_ext table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_channel_ext"
 */

public abstract class BaseChannelExt  implements Serializable {

	public static String REF = "ChannelExt";
	public static String PROP_ACCESS_BY_DIR = "accessByDir";
	public static String PROP_KEYWORDS = "keywords";
	public static String PROP_CHANNEL_RULE = "channelRule";
	public static String PROP_STATIC_CHANNEL = "staticChannel";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_CONTENT_RULE = "contentRule";
	public static String PROP_STATIC_CONTENT = "staticContent";
	public static String PROP_CHANNEL = "channel";
	public static String PROP_COMMENT_CONTROL = "commentControl";
	public static String PROP_PAGE_SIZE = "pageSize";
	public static String PROP_NAME = "name";
	public static String PROP_LINK = "link";
	public static String PROP_TITLE_IMG_HEIGHT = "titleImgHeight";
	public static String PROP_TPL_CHANNEL = "tplChannel";
	public static String PROP_ALLOW_UPDOWN = "allowUpdown";
	public static String PROP_LIST_CHILD = "listChild";
	public static String PROP_TITLE_IMG_WIDTH = "titleImgWidth";
	public static String PROP_TPL_CONTENT = "tplContent";
	public static String PROP_HAS_TITLE_IMG = "hasTitleImg";
	public static String PROP_CONTENT_IMG_WIDTH = "contentImgWidth";
	public static String PROP_FINAL_STEP = "finalStep";
	public static String PROP_HAS_CONTENT_IMG = "hasContentImg";
	public static String PROP_BLANK = "blank";
	public static String PROP_TITLE_IMG = "titleImg";
	public static String PROP_AFTER_CHECK = "afterCheck";
	public static String PROP_TITLE = "title";
	public static String PROP_CONTENT_IMG_HEIGHT = "contentImgHeight";
	public static String PROP_ID = "id";
	public static String PROP_CONTENT_IMG = "contentImg";


	// constructors
	public BaseChannelExt () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseChannelExt (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseChannelExt (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Boolean staticChannel,
		java.lang.Boolean staticContent,
		java.lang.Boolean accessByDir,
		java.lang.Boolean listChild,
		java.lang.Integer pageSize,
		java.lang.Boolean hasTitleImg,
		java.lang.Boolean hasContentImg,
		java.lang.Integer titleImgWidth,
		java.lang.Integer titleImgHeight,
		java.lang.Integer contentImgWidth,
		java.lang.Integer contentImgHeight,
		java.lang.Integer commentControl,
		java.lang.Boolean blank) {

		this.setId(id);
		this.setName(name);
		this.setStaticChannel(staticChannel);
		this.setStaticContent(staticContent);
		this.setAccessByDir(accessByDir);
		this.setListChild(listChild);
		this.setPageSize(pageSize);
		this.setHasTitleImg(hasTitleImg);
		this.setHasContentImg(hasContentImg);
		this.setTitleImgWidth(titleImgWidth);
		this.setTitleImgHeight(titleImgHeight);
		this.setContentImgWidth(contentImgWidth);
		this.setContentImgHeight(contentImgHeight);
		this.setCommentControl(commentControl);
		this.setBlank(blank);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Byte finalStep;
	private java.lang.Byte afterCheck;
	private java.lang.Boolean staticChannel;
	private java.lang.Boolean staticContent;
	private java.lang.Boolean accessByDir;
	private java.lang.Boolean listChild;
	private java.lang.Integer pageSize;
	private java.lang.String channelRule;
	private java.lang.String contentRule;
	private java.lang.String link;
	private java.lang.String tplChannel;
	private java.lang.String tplContent;
	private java.lang.String titleImg;
	private java.lang.String contentImg;
	private java.lang.Boolean hasTitleImg;
	private java.lang.Boolean hasContentImg;
	private java.lang.Integer titleImgWidth;
	private java.lang.Integer titleImgHeight;
	private java.lang.Integer contentImgWidth;
	private java.lang.Integer contentImgHeight;
	private java.lang.Integer commentControl;
	private java.lang.Boolean allowUpdown;
	private java.lang.Boolean allowShare;
	private java.lang.Boolean allowScore;
	private java.lang.Boolean blank;
	private java.lang.String title;
	private java.lang.String keywords;
	private java.lang.String description;

	// one to one
	private com.jeecms.cms.entity.main.Channel channel;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="channel_id"
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
	 * Return the value associated with the column: channel_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: channel_name
	 * @param name the channel_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: final_step
	 */
	public java.lang.Byte getFinalStep () {
		return finalStep;
	}

	/**
	 * Set the value related to the column: final_step
	 * @param finalStep the final_step value
	 */
	public void setFinalStep (java.lang.Byte finalStep) {
		this.finalStep = finalStep;
	}


	/**
	 * Return the value associated with the column: after_check
	 */
	public java.lang.Byte getAfterCheck () {
		return afterCheck;
	}

	/**
	 * Set the value related to the column: after_check
	 * @param afterCheck the after_check value
	 */
	public void setAfterCheck (java.lang.Byte afterCheck) {
		this.afterCheck = afterCheck;
	}


	/**
	 * Return the value associated with the column: is_static_channel
	 */
	public java.lang.Boolean getStaticChannel () {
		return staticChannel;
	}

	/**
	 * Set the value related to the column: is_static_channel
	 * @param staticChannel the is_static_channel value
	 */
	public void setStaticChannel (java.lang.Boolean staticChannel) {
		this.staticChannel = staticChannel;
	}


	/**
	 * Return the value associated with the column: is_static_content
	 */
	public java.lang.Boolean getStaticContent () {
		return staticContent;
	}

	/**
	 * Set the value related to the column: is_static_content
	 * @param staticContent the is_static_content value
	 */
	public void setStaticContent (java.lang.Boolean staticContent) {
		this.staticContent = staticContent;
	}


	/**
	 * Return the value associated with the column: is_access_by_dir
	 */
	public java.lang.Boolean getAccessByDir () {
		return accessByDir;
	}

	/**
	 * Set the value related to the column: is_access_by_dir
	 * @param accessByDir the is_access_by_dir value
	 */
	public void setAccessByDir (java.lang.Boolean accessByDir) {
		this.accessByDir = accessByDir;
	}


	/**
	 * Return the value associated with the column: is_list_child
	 */
	public java.lang.Boolean getListChild () {
		return listChild;
	}

	/**
	 * Set the value related to the column: is_list_child
	 * @param listChild the is_list_child value
	 */
	public void setListChild (java.lang.Boolean listChild) {
		this.listChild = listChild;
	}


	/**
	 * Return the value associated with the column: page_size
	 */
	public java.lang.Integer getPageSize () {
		return pageSize;
	}

	/**
	 * Set the value related to the column: page_size
	 * @param pageSize the page_size value
	 */
	public void setPageSize (java.lang.Integer pageSize) {
		this.pageSize = pageSize;
	}


	/**
	 * Return the value associated with the column: channel_rule
	 */
	public java.lang.String getChannelRule () {
		return channelRule;
	}

	/**
	 * Set the value related to the column: channel_rule
	 * @param channelRule the channel_rule value
	 */
	public void setChannelRule (java.lang.String channelRule) {
		this.channelRule = channelRule;
	}


	/**
	 * Return the value associated with the column: content_rule
	 */
	public java.lang.String getContentRule () {
		return contentRule;
	}

	/**
	 * Set the value related to the column: content_rule
	 * @param contentRule the content_rule value
	 */
	public void setContentRule (java.lang.String contentRule) {
		this.contentRule = contentRule;
	}


	/**
	 * Return the value associated with the column: link
	 */
	public java.lang.String getLink () {
		return link;
	}

	/**
	 * Set the value related to the column: link
	 * @param link the link value
	 */
	public void setLink (java.lang.String link) {
		this.link = link;
	}


	/**
	 * Return the value associated with the column: tpl_channel
	 */
	public java.lang.String getTplChannel () {
		return tplChannel;
	}

	/**
	 * Set the value related to the column: tpl_channel
	 * @param tplChannel the tpl_channel value
	 */
	public void setTplChannel (java.lang.String tplChannel) {
		this.tplChannel = tplChannel;
	}


	/**
	 * Return the value associated with the column: tpl_content
	 */
	public java.lang.String getTplContent () {
		return tplContent;
	}

	/**
	 * Set the value related to the column: tpl_content
	 * @param tplContent the tpl_content value
	 */
	public void setTplContent (java.lang.String tplContent) {
		this.tplContent = tplContent;
	}


	/**
	 * Return the value associated with the column: title_img
	 */
	public java.lang.String getTitleImg () {
		return titleImg;
	}

	/**
	 * Set the value related to the column: title_img
	 * @param titleImg the title_img value
	 */
	public void setTitleImg (java.lang.String titleImg) {
		this.titleImg = titleImg;
	}


	/**
	 * Return the value associated with the column: content_img
	 */
	public java.lang.String getContentImg () {
		return contentImg;
	}

	/**
	 * Set the value related to the column: content_img
	 * @param contentImg the content_img value
	 */
	public void setContentImg (java.lang.String contentImg) {
		this.contentImg = contentImg;
	}


	/**
	 * Return the value associated with the column: has_title_img
	 */
	public java.lang.Boolean getHasTitleImg () {
		return hasTitleImg;
	}

	/**
	 * Set the value related to the column: has_title_img
	 * @param hasTitleImg the has_title_img value
	 */
	public void setHasTitleImg (java.lang.Boolean hasTitleImg) {
		this.hasTitleImg = hasTitleImg;
	}


	/**
	 * Return the value associated with the column: has_content_img
	 */
	public java.lang.Boolean getHasContentImg () {
		return hasContentImg;
	}

	/**
	 * Set the value related to the column: has_content_img
	 * @param hasContentImg the has_content_img value
	 */
	public void setHasContentImg (java.lang.Boolean hasContentImg) {
		this.hasContentImg = hasContentImg;
	}


	/**
	 * Return the value associated with the column: title_img_width
	 */
	public java.lang.Integer getTitleImgWidth () {
		return titleImgWidth;
	}

	/**
	 * Set the value related to the column: title_img_width
	 * @param titleImgWidth the title_img_width value
	 */
	public void setTitleImgWidth (java.lang.Integer titleImgWidth) {
		this.titleImgWidth = titleImgWidth;
	}


	/**
	 * Return the value associated with the column: title_img_height
	 */
	public java.lang.Integer getTitleImgHeight () {
		return titleImgHeight;
	}

	/**
	 * Set the value related to the column: title_img_height
	 * @param titleImgHeight the title_img_height value
	 */
	public void setTitleImgHeight (java.lang.Integer titleImgHeight) {
		this.titleImgHeight = titleImgHeight;
	}


	/**
	 * Return the value associated with the column: content_img_width
	 */
	public java.lang.Integer getContentImgWidth () {
		return contentImgWidth;
	}

	/**
	 * Set the value related to the column: content_img_width
	 * @param contentImgWidth the content_img_width value
	 */
	public void setContentImgWidth (java.lang.Integer contentImgWidth) {
		this.contentImgWidth = contentImgWidth;
	}


	/**
	 * Return the value associated with the column: content_img_height
	 */
	public java.lang.Integer getContentImgHeight () {
		return contentImgHeight;
	}

	/**
	 * Set the value related to the column: content_img_height
	 * @param contentImgHeight the content_img_height value
	 */
	public void setContentImgHeight (java.lang.Integer contentImgHeight) {
		this.contentImgHeight = contentImgHeight;
	}


	/**
	 * Return the value associated with the column: comment_control
	 */
	public java.lang.Integer getCommentControl () {
		return commentControl;
	}

	/**
	 * Set the value related to the column: comment_control
	 * @param commentControl the comment_control value
	 */
	public void setCommentControl (java.lang.Integer commentControl) {
		this.commentControl = commentControl;
	}


	/**
	 * Return the value associated with the column: allow_updown
	 */
	public java.lang.Boolean getAllowUpdown () {
		return allowUpdown;
	}

	/**
	 * Set the value related to the column: allow_updown
	 * @param allowUpdown the allow_updown value
	 */
	public void setAllowUpdown (java.lang.Boolean allowUpdown) {
		this.allowUpdown = allowUpdown;
	}

	public java.lang.Boolean getAllowShare() {
		return allowShare;
	}

	public void setAllowShare(java.lang.Boolean allowShare) {
		this.allowShare = allowShare;
	}

	public java.lang.Boolean getAllowScore() {
		return allowScore;
	}

	public void setAllowScore(java.lang.Boolean allowScore) {
		this.allowScore = allowScore;
	}

	/**
	 * Return the value associated with the column: is_blank
	 */
	public java.lang.Boolean getBlank () {
		return blank;
	}

	/**
	 * Set the value related to the column: is_blank
	 * @param blank the is_blank value
	 */
	public void setBlank (java.lang.Boolean blank) {
		this.blank = blank;
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
	 * Return the value associated with the column: keywords
	 */
	public java.lang.String getKeywords () {
		return keywords;
	}

	/**
	 * Set the value related to the column: keywords
	 * @param keywords the keywords value
	 */
	public void setKeywords (java.lang.String keywords) {
		this.keywords = keywords;
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
	 * Return the value associated with the column: channel
	 */
	public com.jeecms.cms.entity.main.Channel getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: channel
	 * @param channel the channel value
	 */
	public void setChannel (com.jeecms.cms.entity.main.Channel channel) {
		this.channel = channel;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ChannelExt)) return false;
		else {
			com.jeecms.cms.entity.main.ChannelExt channelExt = (com.jeecms.cms.entity.main.ChannelExt) obj;
			if (null == this.getId() || null == channelExt.getId()) return false;
			else return (this.getId().equals(channelExt.getId()));
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
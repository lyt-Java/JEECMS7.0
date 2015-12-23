package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_receiver_message table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_receiver_message"
 */

public abstract class BaseCmsReceiverMessage  implements Serializable {

	public static String REF = "CmsReceiverMessage";
	public static String PROP_MSG_STATUS = "msgStatus";
	public static String PROP_SITE = "site";
	public static String PROP_MESSAGE = "message";
	public static String PROP_MSG_SEND_USER = "msgSendUser";
	public static String PROP_MSG_CONTENT = "msgContent";
	public static String PROP_MSG_BOX = "msgBox";
	public static String PROP_SEND_TIME = "sendTime";
	public static String PROP_ID = "id";
	public static String PROP_MSG_RECEIVER_USER = "msgReceiverUser";
	public static String PROP_MSG_TITLE = "msgTitle";


	// constructors
	public BaseCmsReceiverMessage () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsReceiverMessage (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsReceiverMessage (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsUser msgReceiverUser,
		com.jeecms.core.entity.CmsUser msgSendUser,
		com.jeecms.core.entity.CmsSite site,
		java.lang.String msgTitle,
		java.lang.String msgContent,
		java.util.Date sendTime,
		boolean msgStatus,
		java.lang.Integer msgBox) {

		this.setId(id);
		this.setMsgReceiverUser(msgReceiverUser);
		this.setMsgSendUser(msgSendUser);
		this.setSite(site);
		this.setMsgTitle(msgTitle);
		this.setMsgContent(msgContent);
		this.setSendTime(sendTime);
		this.setMsgStatus(msgStatus);
		this.setMsgBox(msgBox);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String msgTitle;
	private java.lang.String msgContent;
	private java.util.Date sendTime;
	private boolean msgStatus;
	private java.lang.Integer msgBox;

	

	// many to one
	private com.jeecms.core.entity.CmsUser msgReceiverUser;
	private com.jeecms.core.entity.CmsUser msgSendUser;
	private com.jeecms.core.entity.CmsSite site;
	private com.jeecms.cms.entity.assist.CmsMessage message;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="msg_id"
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
	 * Return the value associated with the column: msg_title
	 */
	public java.lang.String getMsgTitle () {
		return msgTitle;
	}

	/**
	 * Set the value related to the column: msg_title
	 * @param msgTitle the msg_title value
	 */
	public void setMsgTitle (java.lang.String msgTitle) {
		this.msgTitle = msgTitle;
	}


	/**
	 * Return the value associated with the column: msg_content
	 */
	public java.lang.String getMsgContent () {
		return msgContent;
	}

	/**
	 * Set the value related to the column: msg_content
	 * @param msgContent the msg_content value
	 */
	public void setMsgContent (java.lang.String msgContent) {
		this.msgContent = msgContent;
	}


	/**
	 * Return the value associated with the column: send_time
	 */
	public java.util.Date getSendTime () {
		return sendTime;
	}

	/**
	 * Set the value related to the column: send_time
	 * @param sendTime the send_time value
	 */
	public void setSendTime (java.util.Date sendTime) {
		this.sendTime = sendTime;
	}


	/**
	 * Return the value associated with the column: msg_status
	 */
	public boolean isMsgStatus () {
		return msgStatus;
	}

	/**
	 * Set the value related to the column: msg_status
	 * @param msgStatus the msg_status value
	 */
	public void setMsgStatus (boolean msgStatus) {
		this.msgStatus = msgStatus;
	}


	/**
	 * Return the value associated with the column: msg_box
	 */
	public java.lang.Integer getMsgBox () {
		return msgBox;
	}

	/**
	 * Set the value related to the column: msg_box
	 * @param msgBox the msg_box value
	 */
	public void setMsgBox (java.lang.Integer msgBox) {
		this.msgBox = msgBox;
	}


	/**
	 * Return the value associated with the column: message
	 */
	public com.jeecms.cms.entity.assist.CmsMessage getMessage () {
		return message;
	}

	/**
	 * Set the value related to the column: message
	 * @param message the message value
	 */
	public void setMessage (com.jeecms.cms.entity.assist.CmsMessage message) {
		this.message = message;
	}


	/**
	 * Return the value associated with the column: msg_receiver_user
	 */
	public com.jeecms.core.entity.CmsUser getMsgReceiverUser () {
		return msgReceiverUser;
	}

	/**
	 * Set the value related to the column: msg_receiver_user
	 * @param msgReceiverUser the msg_receiver_user value
	 */
	public void setMsgReceiverUser (com.jeecms.core.entity.CmsUser msgReceiverUser) {
		this.msgReceiverUser = msgReceiverUser;
	}


	/**
	 * Return the value associated with the column: msg_send_user
	 */
	public com.jeecms.core.entity.CmsUser getMsgSendUser () {
		return msgSendUser;
	}

	/**
	 * Set the value related to the column: msg_send_user
	 * @param msgSendUser the msg_send_user value
	 */
	public void setMsgSendUser (com.jeecms.core.entity.CmsUser msgSendUser) {
		this.msgSendUser = msgSendUser;
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
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsReceiverMessage)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsReceiverMessage cmsReceiverMessage = (com.jeecms.cms.entity.assist.CmsReceiverMessage) obj;
			if (null == this.getId() || null == cmsReceiverMessage.getId()) return false;
			else return (this.getId().equals(cmsReceiverMessage.getId()));
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
package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content_txt table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content_txt"
 */

public abstract class BaseContentTxt  implements Serializable {

	public static String REF = "ContentTxt";
	public static String PROP_TXT2 = "txt2";
	public static String PROP_TXT = "txt";
	public static String PROP_TXT1 = "txt1";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";
	public static String PROP_TXT3 = "txt3";


	// constructors
	public BaseContentTxt () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseContentTxt (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String txt;
	private java.lang.String txt1;
	private java.lang.String txt2;
	private java.lang.String txt3;

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
	 * Return the value associated with the column: txt
	 */
	public java.lang.String getTxt () {
		return txt;
	}

	/**
	 * Set the value related to the column: txt
	 * @param txt the txt value
	 */
	public void setTxt (java.lang.String txt) {
		this.txt = txt;
	}


	/**
	 * Return the value associated with the column: txt1
	 */
	public java.lang.String getTxt1 () {
		return txt1;
	}

	/**
	 * Set the value related to the column: txt1
	 * @param txt1 the txt1 value
	 */
	public void setTxt1 (java.lang.String txt1) {
		this.txt1 = txt1;
	}


	/**
	 * Return the value associated with the column: txt2
	 */
	public java.lang.String getTxt2 () {
		return txt2;
	}

	/**
	 * Set the value related to the column: txt2
	 * @param txt2 the txt2 value
	 */
	public void setTxt2 (java.lang.String txt2) {
		this.txt2 = txt2;
	}


	/**
	 * Return the value associated with the column: txt3
	 */
	public java.lang.String getTxt3 () {
		return txt3;
	}

	/**
	 * Set the value related to the column: txt3
	 * @param txt3 the txt3 value
	 */
	public void setTxt3 (java.lang.String txt3) {
		this.txt3 = txt3;
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
		if (!(obj instanceof com.jeecms.cms.entity.main.ContentTxt)) return false;
		else {
			com.jeecms.cms.entity.main.ContentTxt contentTxt = (com.jeecms.cms.entity.main.ContentTxt) obj;
			if (null == this.getId() || null == contentTxt.getId()) return false;
			else return (this.getId().equals(contentTxt.getId()));
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
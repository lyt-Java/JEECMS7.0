package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content"
 */

public abstract class BaseContentAttachment  implements Serializable {

	public static String REF = "ContentAttachment";
	public static String PROP_PATH = "path";
	public static String PROP_FILENAME = "filename";
	public static String PROP_NAME = "name";
	public static String PROP_COUNT = "count";


	// constructors
	public BaseContentAttachment () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseContentAttachment (
		java.lang.String path,
		java.lang.String name,
		java.lang.Integer count) {

		this.setPath(path);
		this.setName(name);
		this.setCount(count);
		initialize();
	}

	protected void initialize () {}



	// fields
	private java.lang.String path;
	private java.lang.String name;
	private java.lang.String filename;
	private java.lang.Integer count;






	/**
	 * Return the value associated with the column: attachment_path
	 */
	public java.lang.String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: attachment_path
	 * @param path the attachment_path value
	 */
	public void setPath (java.lang.String path) {
		this.path = path;
	}


	/**
	 * Return the value associated with the column: attachment_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: attachment_name
	 * @param name the attachment_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: filename
	 */
	public java.lang.String getFilename () {
		return filename;
	}

	/**
	 * Set the value related to the column: filename
	 * @param filename the filename value
	 */
	public void setFilename (java.lang.String filename) {
		this.filename = filename;
	}


	/**
	 * Return the value associated with the column: download_count
	 */
	public java.lang.Integer getCount () {
		return count;
	}

	/**
	 * Set the value related to the column: download_count
	 * @param count the download_count value
	 */
	public void setCount (java.lang.Integer count) {
		this.count = count;
	}






	public String toString () {
		return super.toString();
	}


}
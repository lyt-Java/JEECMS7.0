package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jo_upload table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jo_upload"
 */

public abstract class BaseDbFile  implements Serializable {

	public static String REF = "DbFile";
	public static String PROP_LAST_MODIFIED = "lastModified";
	public static String PROP_LENGTH = "length";
	public static String PROP_CONTENT = "content";
	public static String PROP_ID = "id";


	// constructors
	public BaseDbFile () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDbFile (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDbFile (
		java.lang.String id,
		java.lang.Integer length,
		java.lang.Long lastModified,
		byte[] content) {

		this.setId(id);
		this.setLength(length);
		this.setLastModified(lastModified);
		this.setContent(content);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.Integer length;
	private java.lang.Long lastModified;
	private byte[] content;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="filename"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: length
	 */
	public java.lang.Integer getLength () {
		return length;
	}

	/**
	 * Set the value related to the column: length
	 * @param length the length value
	 */
	public void setLength (java.lang.Integer length) {
		this.length = length;
	}


	/**
	 * Return the value associated with the column: last_modified
	 */
	public java.lang.Long getLastModified () {
		return lastModified;
	}

	/**
	 * Set the value related to the column: last_modified
	 * @param lastModified the last_modified value
	 */
	public void setLastModified (java.lang.Long lastModified) {
		this.lastModified = lastModified;
	}


	/**
	 * Return the value associated with the column: content
	 */
	public byte[] getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: content
	 * @param content the content value
	 */
	public void setContent (byte[] content) {
		this.content = content;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.DbFile)) return false;
		else {
			com.jeecms.core.entity.DbFile dbFile = (com.jeecms.core.entity.DbFile) obj;
			if (null == this.getId() || null == dbFile.getId()) return false;
			else return (this.getId().equals(dbFile.getId()));
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
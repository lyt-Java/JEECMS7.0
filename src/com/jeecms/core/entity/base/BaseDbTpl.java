package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jo_template table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jo_template"
 */

public abstract class BaseDbTpl  implements Serializable {

	public static String REF = "DbTpl";
	public static String PROP_LAST_MODIFIED = "lastModified";
	public static String PROP_SOURCE = "source";
	public static String PROP_DIRECTORY = "directory";
	public static String PROP_ID = "id";


	// constructors
	public BaseDbTpl () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseDbTpl (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseDbTpl (
		java.lang.String id,
		long lastModified,
		boolean directory) {

		this.setId(id);
		this.setLastModified(lastModified);
		this.setDirectory(directory);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String source;
	private long lastModified;
	private boolean directory;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="tpl_name"
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
	 * Return the value associated with the column: tpl_source
	 */
	public java.lang.String getSource () {
		return source;
	}

	/**
	 * Set the value related to the column: tpl_source
	 * @param source the tpl_source value
	 */
	public void setSource (java.lang.String source) {
		this.source = source;
	}


	/**
	 * Return the value associated with the column: last_modified
	 */
	public long getLastModified () {
		return lastModified;
	}

	/**
	 * Set the value related to the column: last_modified
	 * @param lastModified the last_modified value
	 */
	public void setLastModified (long lastModified) {
		this.lastModified = lastModified;
	}


	/**
	 * Return the value associated with the column: is_directory
	 */
	public boolean isDirectory () {
		return directory;
	}

	/**
	 * Set the value related to the column: is_directory
	 * @param directory the is_directory value
	 */
	public void setDirectory (boolean directory) {
		this.directory = directory;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.DbTpl)) return false;
		else {
			com.jeecms.core.entity.DbTpl dbTpl = (com.jeecms.core.entity.DbTpl) obj;
			if (null == this.getId() || null == dbTpl.getId()) return false;
			else return (this.getId().equals(dbTpl.getId()));
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
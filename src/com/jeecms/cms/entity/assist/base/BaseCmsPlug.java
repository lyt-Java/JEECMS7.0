package com.jeecms.cms.entity.assist.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_plug table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_plug"
 */

public abstract class BaseCmsPlug  implements Serializable {

	public static String REF = "CmsPlug";
	public static String PROP_NAME = "name";
	public static String PROP_FILE_CONFLICT = "fileConflict";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_USED = "used";
	public static String PROP_UPLOAD_TIME = "uploadTime";
	public static String PROP_UNINSTALL_TIME = "uninstallTime";
	public static String PROP_AUTHOR = "author";
	public static String PROP_ID = "id";
	public static String PROP_INSTALL_TIME = "installTime";
	public static String PROP_PLUG_PERMS = "plugPerms";
	public static String PROP_PATH = "path";


	// constructors
	public BaseCmsPlug () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsPlug (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsPlug (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.String path,
		java.util.Date uploadTime,
		boolean fileConflict,
		boolean used) {

		this.setId(id);
		this.setName(name);
		this.setPath(path);
		this.setUploadTime(uploadTime);
		this.setFileConflict(fileConflict);
		this.setUsed(used);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String path;
	private java.lang.String description;
	private java.lang.String author;
	private java.util.Date uploadTime;
	private java.util.Date installTime;
	private java.util.Date uninstallTime;
	private boolean fileConflict;
	private boolean used;
	private java.lang.String plugPerms;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="plug_id"
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
	 * Return the value associated with the column: name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: name
	 * @param name the name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: path
	 */
	public java.lang.String getPath () {
		return path;
	}

	/**
	 * Set the value related to the column: path
	 * @param path the path value
	 */
	public void setPath (java.lang.String path) {
		this.path = path;
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
	 * Return the value associated with the column: author
	 */
	public java.lang.String getAuthor () {
		return author;
	}

	/**
	 * Set the value related to the column: author
	 * @param author the author value
	 */
	public void setAuthor (java.lang.String author) {
		this.author = author;
	}


	/**
	 * Return the value associated with the column: upload_time
	 */
	public java.util.Date getUploadTime () {
		return uploadTime;
	}

	/**
	 * Set the value related to the column: upload_time
	 * @param uploadTime the upload_time value
	 */
	public void setUploadTime (java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}


	/**
	 * Return the value associated with the column: install_time
	 */
	public java.util.Date getInstallTime () {
		return installTime;
	}

	/**
	 * Set the value related to the column: install_time
	 * @param installTime the install_time value
	 */
	public void setInstallTime (java.util.Date installTime) {
		this.installTime = installTime;
	}


	/**
	 * Return the value associated with the column: uninstall_time
	 */
	public java.util.Date getUninstallTime () {
		return uninstallTime;
	}

	/**
	 * Set the value related to the column: uninstall_time
	 * @param uninstallTime the uninstall_time value
	 */
	public void setUninstallTime (java.util.Date uninstallTime) {
		this.uninstallTime = uninstallTime;
	}


	/**
	 * Return the value associated with the column: file_conflict
	 */
	public boolean isFileConflict () {
		return fileConflict;
	}

	/**
	 * Set the value related to the column: file_conflict
	 * @param fileConflict the file_conflict value
	 */
	public void setFileConflict (boolean fileConflict) {
		this.fileConflict = fileConflict;
	}


	/**
	 * Return the value associated with the column: is_used
	 */
	public boolean isUsed () {
		return used;
	}

	/**
	 * Set the value related to the column: is_used
	 * @param used the is_used value
	 */
	public void setUsed (boolean used) {
		this.used = used;
	}


	/**
	 * Return the value associated with the column: plug_perms
	 */
	public java.lang.String getPlugPerms () {
		return plugPerms;
	}

	/**
	 * Set the value related to the column: plug_perms
	 * @param plugPerms the plug_perms value
	 */
	public void setPlugPerms (java.lang.String plugPerms) {
		this.plugPerms = plugPerms;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.assist.CmsPlug)) return false;
		else {
			com.jeecms.cms.entity.assist.CmsPlug cmsPlug = (com.jeecms.cms.entity.assist.CmsPlug) obj;
			if (null == this.getId() || null == cmsPlug.getId()) return false;
			else return (this.getId().equals(cmsPlug.getId()));
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
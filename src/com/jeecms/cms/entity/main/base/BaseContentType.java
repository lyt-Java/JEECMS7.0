package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_content_type table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_content_type"
 */

public abstract class BaseContentType  implements Serializable {

	public static String REF = "ContentType";
	public static String PROP_IMG_HEIGHT = "imgHeight";
	public static String PROP_IMG_WIDTH = "imgWidth";
	public static String PROP_DISABLED = "disabled";
	public static String PROP_NAME = "name";
	public static String PROP_ID = "id";
	public static String PROP_HAS_IMAGE = "hasImage";


	// constructors
	public BaseContentType () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseContentType (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseContentType (
		java.lang.Integer id,
		java.lang.String name,
		java.lang.Boolean hasImage,
		java.lang.Boolean disabled) {

		this.setId(id);
		this.setName(name);
		this.setHasImage(hasImage);
		this.setDisabled(disabled);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.Integer imgWidth;
	private java.lang.Integer imgHeight;
	private java.lang.Boolean hasImage;
	private java.lang.Boolean disabled;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="type_id"
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
	 * Return the value associated with the column: type_name
	 */
	public java.lang.String getName () {
		return name;
	}

	/**
	 * Set the value related to the column: type_name
	 * @param name the type_name value
	 */
	public void setName (java.lang.String name) {
		this.name = name;
	}


	/**
	 * Return the value associated with the column: img_width
	 */
	public java.lang.Integer getImgWidth () {
		return imgWidth;
	}

	/**
	 * Set the value related to the column: img_width
	 * @param imgWidth the img_width value
	 */
	public void setImgWidth (java.lang.Integer imgWidth) {
		this.imgWidth = imgWidth;
	}


	/**
	 * Return the value associated with the column: img_height
	 */
	public java.lang.Integer getImgHeight () {
		return imgHeight;
	}

	/**
	 * Set the value related to the column: img_height
	 * @param imgHeight the img_height value
	 */
	public void setImgHeight (java.lang.Integer imgHeight) {
		this.imgHeight = imgHeight;
	}


	/**
	 * Return the value associated with the column: has_image
	 */
	public java.lang.Boolean getHasImage () {
		return hasImage;
	}

	/**
	 * Set the value related to the column: has_image
	 * @param hasImage the has_image value
	 */
	public void setHasImage (java.lang.Boolean hasImage) {
		this.hasImage = hasImage;
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



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.ContentType)) return false;
		else {
			com.jeecms.cms.entity.main.ContentType contentType = (com.jeecms.cms.entity.main.ContentType) obj;
			if (null == this.getId() || null == contentType.getId()) return false;
			else return (this.getId().equals(contentType.getId()));
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
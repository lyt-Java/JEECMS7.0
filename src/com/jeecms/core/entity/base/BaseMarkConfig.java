package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_config table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_config"
 */

public abstract class BaseMarkConfig  implements Serializable {

	public static String REF = "MarkConfig";
	public static String PROP_COLOR = "color";
	public static String PROP_ALPHA = "alpha";
	public static String PROP_POS = "pos";
	public static String PROP_SIZE = "size";
	public static String PROP_OFFSET_X = "offsetX";
	public static String PROP_MIN_WIDTH = "minWidth";
	public static String PROP_IMAGE_PATH = "imagePath";
	public static String PROP_CONTENT = "content";
	public static String PROP_MIN_HEIGHT = "minHeight";
	public static String PROP_OFFSET_Y = "offsetY";
	public static String PROP_ON = "on";


	// constructors
	public BaseMarkConfig () {
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseMarkConfig (
		java.lang.Boolean on,
		java.lang.Integer minWidth,
		java.lang.Integer minHeight,
		java.lang.String content,
		java.lang.Integer size,
		java.lang.String color,
		java.lang.Integer alpha,
		java.lang.Integer pos,
		java.lang.Integer offsetX,
		java.lang.Integer offsetY) {

		this.setOn(on);
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setContent(content);
		this.setSize(size);
		this.setColor(color);
		this.setAlpha(alpha);
		this.setPos(pos);
		this.setOffsetX(offsetX);
		this.setOffsetY(offsetY);
		initialize();
	}

	protected void initialize () {}



	// fields
	private java.lang.Boolean on;
	private java.lang.Integer minWidth;
	private java.lang.Integer minHeight;
	private java.lang.String imagePath;
	private java.lang.String content;
	private java.lang.Integer size;
	private java.lang.String color;
	private java.lang.Integer alpha;
	private java.lang.Integer pos;
	private java.lang.Integer offsetX;
	private java.lang.Integer offsetY;






	/**
	 * Return the value associated with the column: mark_on
	 */
	public java.lang.Boolean getOn () {
		return on;
	}

	/**
	 * Set the value related to the column: mark_on
	 * @param on the mark_on value
	 */
	public void setOn (java.lang.Boolean on) {
		this.on = on;
	}


	/**
	 * Return the value associated with the column: mark_width
	 */
	public java.lang.Integer getMinWidth () {
		return minWidth;
	}

	/**
	 * Set the value related to the column: mark_width
	 * @param minWidth the mark_width value
	 */
	public void setMinWidth (java.lang.Integer minWidth) {
		this.minWidth = minWidth;
	}


	/**
	 * Return the value associated with the column: mark_height
	 */
	public java.lang.Integer getMinHeight () {
		return minHeight;
	}

	/**
	 * Set the value related to the column: mark_height
	 * @param minHeight the mark_height value
	 */
	public void setMinHeight (java.lang.Integer minHeight) {
		this.minHeight = minHeight;
	}


	/**
	 * Return the value associated with the column: mark_image
	 */
	public java.lang.String getImagePath () {
		return imagePath;
	}

	/**
	 * Set the value related to the column: mark_image
	 * @param imagePath the mark_image value
	 */
	public void setImagePath (java.lang.String imagePath) {
		this.imagePath = imagePath;
	}


	/**
	 * Return the value associated with the column: mark_content
	 */
	public java.lang.String getContent () {
		return content;
	}

	/**
	 * Set the value related to the column: mark_content
	 * @param content the mark_content value
	 */
	public void setContent (java.lang.String content) {
		this.content = content;
	}


	/**
	 * Return the value associated with the column: mark_size
	 */
	public java.lang.Integer getSize () {
		return size;
	}

	/**
	 * Set the value related to the column: mark_size
	 * @param size the mark_size value
	 */
	public void setSize (java.lang.Integer size) {
		this.size = size;
	}


	/**
	 * Return the value associated with the column: mark_color
	 */
	public java.lang.String getColor () {
		return color;
	}

	/**
	 * Set the value related to the column: mark_color
	 * @param color the mark_color value
	 */
	public void setColor (java.lang.String color) {
		this.color = color;
	}


	/**
	 * Return the value associated with the column: mark_alpha
	 */
	public java.lang.Integer getAlpha () {
		return alpha;
	}

	/**
	 * Set the value related to the column: mark_alpha
	 * @param alpha the mark_alpha value
	 */
	public void setAlpha (java.lang.Integer alpha) {
		this.alpha = alpha;
	}


	/**
	 * Return the value associated with the column: mark_position
	 */
	public java.lang.Integer getPos () {
		return pos;
	}

	/**
	 * Set the value related to the column: mark_position
	 * @param pos the mark_position value
	 */
	public void setPos (java.lang.Integer pos) {
		this.pos = pos;
	}


	/**
	 * Return the value associated with the column: mark_offset_x
	 */
	public java.lang.Integer getOffsetX () {
		return offsetX;
	}

	/**
	 * Set the value related to the column: mark_offset_x
	 * @param offsetX the mark_offset_x value
	 */
	public void setOffsetX (java.lang.Integer offsetX) {
		this.offsetX = offsetX;
	}


	/**
	 * Return the value associated with the column: mark_offset_y
	 */
	public java.lang.Integer getOffsetY () {
		return offsetY;
	}

	/**
	 * Set the value related to the column: mark_offset_y
	 * @param offsetY the mark_offset_y value
	 */
	public void setOffsetY (java.lang.Integer offsetY) {
		this.offsetY = offsetY;
	}






	public String toString () {
		return super.toString();
	}


}
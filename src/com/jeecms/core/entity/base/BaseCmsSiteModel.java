package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_site_model table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_site_model"
 */

public abstract class BaseCmsSiteModel  implements Serializable {

	public static String REF = "CmsSiteModel";
	public static String PROP_SINGLE = "single";
	public static String PROP_COLS = "cols";
	public static String PROP_DATA_TYPE = "dataType";
	public static String PROP_HELP_POSITION = "helpPosition";
	public static String PROP_ROWS = "rows";
	public static String PROP_HELP = "help";
	public static String PROP_SIZE = "size";
	public static String PROP_UPLOAD_PATH = "uploadPath";
	public static String PROP_PRIORITY = "priority";
	public static String PROP_LABEL = "label";
	public static String PROP_FIELD = "field";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsSiteModel () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsSiteModel (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsSiteModel (
		java.lang.Integer id,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer priority) {

		this.setId(id);
		this.setField(field);
		this.setLabel(label);
		this.setPriority(priority);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String field;
	private java.lang.String label;
	private java.lang.Integer priority;
	private java.lang.String uploadPath;
	private java.lang.String size;
	private java.lang.String rows;
	private java.lang.String cols;
	private java.lang.String help;
	private java.lang.String helpPosition;
	private java.lang.Integer dataType;
	private java.lang.Boolean single;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="model_id"
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
	 * Return the value associated with the column: model_label
	 */
	public java.lang.String getField () {
		return field;
	}

	/**
	 * Set the value related to the column: model_label
	 * @param field the model_label value
	 */
	public void setField (java.lang.String field) {
		this.field = field;
	}


	/**
	 * Return the value associated with the column: label
	 */
	public java.lang.String getLabel () {
		return label;
	}

	/**
	 * Set the value related to the column: label
	 * @param label the label value
	 */
	public void setLabel (java.lang.String label) {
		this.label = label;
	}


	/**
	 * Return the value associated with the column: priority
	 */
	public java.lang.Integer getPriority () {
		return priority;
	}

	/**
	 * Set the value related to the column: priority
	 * @param priority the priority value
	 */
	public void setPriority (java.lang.Integer priority) {
		this.priority = priority;
	}


	/**
	 * Return the value associated with the column: upload_path
	 */
	public java.lang.String getUploadPath () {
		return uploadPath;
	}

	/**
	 * Set the value related to the column: upload_path
	 * @param uploadPath the upload_path value
	 */
	public void setUploadPath (java.lang.String uploadPath) {
		this.uploadPath = uploadPath;
	}


	/**
	 * Return the value associated with the column: text_size
	 */
	public java.lang.String getSize () {
		return size;
	}

	/**
	 * Set the value related to the column: text_size
	 * @param size the text_size value
	 */
	public void setSize (java.lang.String size) {
		this.size = size;
	}


	/**
	 * Return the value associated with the column: area_rows
	 */
	public java.lang.String getRows () {
		return rows;
	}

	/**
	 * Set the value related to the column: area_rows
	 * @param rows the area_rows value
	 */
	public void setRows (java.lang.String rows) {
		this.rows = rows;
	}


	/**
	 * Return the value associated with the column: area_cols
	 */
	public java.lang.String getCols () {
		return cols;
	}

	/**
	 * Set the value related to the column: area_cols
	 * @param cols the area_cols value
	 */
	public void setCols (java.lang.String cols) {
		this.cols = cols;
	}


	/**
	 * Return the value associated with the column: help
	 */
	public java.lang.String getHelp () {
		return help;
	}

	/**
	 * Set the value related to the column: help
	 * @param help the help value
	 */
	public void setHelp (java.lang.String help) {
		this.help = help;
	}


	/**
	 * Return the value associated with the column: help_position
	 */
	public java.lang.String getHelpPosition () {
		return helpPosition;
	}

	/**
	 * Set the value related to the column: help_position
	 * @param helpPosition the help_position value
	 */
	public void setHelpPosition (java.lang.String helpPosition) {
		this.helpPosition = helpPosition;
	}


	/**
	 * Return the value associated with the column: data_type
	 */
	public java.lang.Integer getDataType () {
		return dataType;
	}

	/**
	 * Set the value related to the column: data_type
	 * @param dataType the data_type value
	 */
	public void setDataType (java.lang.Integer dataType) {
		this.dataType = dataType;
	}


	/**
	 * Return the value associated with the column: is_single
	 */
	public java.lang.Boolean getSingle () {
		return single;
	}

	/**
	 * Set the value related to the column: is_single
	 * @param single the is_single value
	 */
	public void setSingle (java.lang.Boolean single) {
		this.single = single;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsSiteModel)) return false;
		else {
			com.jeecms.core.entity.CmsSiteModel cmsSiteModel = (com.jeecms.core.entity.CmsSiteModel) obj;
			if (null == this.getId() || null == cmsSiteModel.getId()) return false;
			else return (this.getId().equals(cmsSiteModel.getId()));
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
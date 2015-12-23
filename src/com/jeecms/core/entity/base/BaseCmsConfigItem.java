package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_config_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_config_item"
 */

public abstract class BaseCmsConfigItem  implements Serializable {

	public static String REF = "CmsConfigItem";
	public static String PROP_COLS = "cols";
	public static String PROP_CONFIG = "config";
	public static String PROP_ROWS = "rows";
	public static String PROP_FIELD = "field";
	public static String PROP_DATA_TYPE = "dataType";
	public static String PROP_HELP_POSITION = "helpPosition";
	public static String PROP_PRIORITY = "priority";
	public static String PROP_HELP = "help";
	public static String PROP_CATEGORY = "category";
	public static String PROP_OPT_VALUE = "optValue";
	public static String PROP_REQUIRED = "required";
	public static String PROP_DEF_VALUE = "defValue";
	public static String PROP_ID = "id";
	public static String PROP_LABEL = "label";
	public static String PROP_SIZE = "size";


	// constructors
	public BaseCmsConfigItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsConfigItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsConfigItem (
		java.lang.Integer id,
		com.jeecms.core.entity.CmsConfig config,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer priority,
		java.lang.Integer dataType,
		boolean required,
		java.lang.Integer category) {

		this.setId(id);
		this.setConfig(config);
		this.setField(field);
		this.setLabel(label);
		this.setPriority(priority);
		this.setDataType(dataType);
		this.setRequired(required);
		this.setCategory(category);
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
	private java.lang.String defValue;
	private java.lang.String optValue;
	private java.lang.String size;
	private java.lang.String rows;
	private java.lang.String cols;
	private java.lang.String help;
	private java.lang.String helpPosition;
	private java.lang.Integer dataType;
	private boolean required;
	private java.lang.Integer category;

	// many to one
	private com.jeecms.core.entity.CmsConfig config;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="identity"
     *  column="modelitem_id"
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
	 * Return the value associated with the column: field
	 */
	public java.lang.String getField () {
		return field;
	}

	/**
	 * Set the value related to the column: field
	 * @param field the field value
	 */
	public void setField (java.lang.String field) {
		this.field = field;
	}


	/**
	 * Return the value associated with the column: item_label
	 */
	public java.lang.String getLabel () {
		return label;
	}

	/**
	 * Set the value related to the column: item_label
	 * @param label the item_label value
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
	 * Return the value associated with the column: def_value
	 */
	public java.lang.String getDefValue () {
		return defValue;
	}

	/**
	 * Set the value related to the column: def_value
	 * @param defValue the def_value value
	 */
	public void setDefValue (java.lang.String defValue) {
		this.defValue = defValue;
	}


	/**
	 * Return the value associated with the column: opt_value
	 */
	public java.lang.String getOptValue () {
		return optValue;
	}

	/**
	 * Set the value related to the column: opt_value
	 * @param optValue the opt_value value
	 */
	public void setOptValue (java.lang.String optValue) {
		this.optValue = optValue;
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
	 * Return the value associated with the column: is_required
	 */
	public boolean isRequired () {
		return required;
	}

	/**
	 * Set the value related to the column: is_required
	 * @param required the is_required value
	 */
	public void setRequired (boolean required) {
		this.required = required;
	}


	/**
	 * Return the value associated with the column: category
	 */
	public java.lang.Integer getCategory () {
		return category;
	}

	/**
	 * Set the value related to the column: category
	 * @param category the category value
	 */
	public void setCategory (java.lang.Integer category) {
		this.category = category;
	}


	/**
	 * Return the value associated with the column: config_id
	 */
	public com.jeecms.core.entity.CmsConfig getConfig () {
		return config;
	}

	/**
	 * Set the value related to the column: config_id
	 * @param config the config_id value
	 */
	public void setConfig (com.jeecms.core.entity.CmsConfig config) {
		this.config = config;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsConfigItem)) return false;
		else {
			com.jeecms.core.entity.CmsConfigItem cmsConfigItem = (com.jeecms.core.entity.CmsConfigItem) obj;
			if (null == this.getId() || null == cmsConfigItem.getId()) return false;
			else return (this.getId().equals(cmsConfigItem.getId()));
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
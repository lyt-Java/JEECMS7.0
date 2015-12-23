package com.jeecms.cms.entity.main.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_model_item table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_model_item"
 */

public abstract class BaseCmsModelItem  implements Serializable {

	public static String REF = "CmsModelItem";
	public static String PROP_SINGLE = "single";
	public static String PROP_DATA_TYPE = "dataType";
	public static String PROP_CUSTOM = "custom";
	public static String PROP_OPT_VALUE = "optValue";
	public static String PROP_HELP = "help";
	public static String PROP_PRIORITY = "priority";
	public static String PROP_FIELD = "field";
	public static String PROP_LABEL = "label";
	public static String PROP_COLS = "cols";
	public static String PROP_MODEL = "model";
	public static String PROP_DEF_VALUE = "defValue";
	public static String PROP_HELP_POSITION = "helpPosition";
	public static String PROP_ROWS = "rows";
	public static String PROP_SIZE = "size";
	public static String PROP_DISPLAY = "display";
	public static String PROP_CHANNEL = "channel";
	public static String PROP_ID = "id";


	// constructors
	public BaseCmsModelItem () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsModelItem (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsModelItem (
		java.lang.Integer id,
		com.jeecms.cms.entity.main.CmsModel model,
		java.lang.String field,
		java.lang.String label,
		java.lang.Integer dataType,
		java.lang.Boolean single,
		java.lang.Boolean channel,
		java.lang.Boolean custom,
		java.lang.Boolean display) {

		this.setId(id);
		this.setModel(model);
		this.setField(field);
		this.setLabel(label);
		this.setDataType(dataType);
		this.setSingle(single);
		this.setChannel(channel);
		this.setCustom(custom);
		this.setDisplay(display);
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
	private java.lang.Boolean single;
	private java.lang.Boolean channel;
	private java.lang.Boolean custom;
	private java.lang.Boolean display;

	// many to one
	private com.jeecms.cms.entity.main.CmsModel model;



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


	/**
	 * Return the value associated with the column: is_channel
	 */
	public java.lang.Boolean getChannel () {
		return channel;
	}

	/**
	 * Set the value related to the column: is_channel
	 * @param channel the is_channel value
	 */
	public void setChannel (java.lang.Boolean channel) {
		this.channel = channel;
	}


	/**
	 * Return the value associated with the column: is_custom
	 */
	public java.lang.Boolean getCustom () {
		return custom;
	}

	/**
	 * Set the value related to the column: is_custom
	 * @param custom the is_custom value
	 */
	public void setCustom (java.lang.Boolean custom) {
		this.custom = custom;
	}


	/**
	 * Return the value associated with the column: is_display
	 */
	public java.lang.Boolean getDisplay () {
		return display;
	}

	/**
	 * Set the value related to the column: is_display
	 * @param display the is_display value
	 */
	public void setDisplay (java.lang.Boolean display) {
		this.display = display;
	}


	/**
	 * Return the value associated with the column: model_id
	 */
	public com.jeecms.cms.entity.main.CmsModel getModel () {
		return model;
	}

	/**
	 * Set the value related to the column: model_id
	 * @param model the model_id value
	 */
	public void setModel (com.jeecms.cms.entity.main.CmsModel model) {
		this.model = model;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.cms.entity.main.CmsModelItem)) return false;
		else {
			com.jeecms.cms.entity.main.CmsModelItem cmsModelItem = (com.jeecms.cms.entity.main.CmsModelItem) obj;
			if (null == this.getId() || null == cmsModelItem.getId()) return false;
			else return (this.getId().equals(cmsModelItem.getId()));
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
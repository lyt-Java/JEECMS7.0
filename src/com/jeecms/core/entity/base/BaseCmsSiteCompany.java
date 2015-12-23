package com.jeecms.core.entity.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the jc_site_company table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="jc_site_company"
 */

public abstract class BaseCmsSiteCompany  implements Serializable {

	public static String REF = "CmsSiteCompany";
	public static String PROP_NAME = "name";
	public static String PROP_DESCRIPTION = "description";
	public static String PROP_SITE = "site";
	public static String PROP_ADDRESS = "address";
	public static String PROP_CONTACT = "contact";
	public static String PROP_LATITUDE = "latitude";
	public static String PROP_ID = "id";
	public static String PROP_LONGITUDE = "longitude";
	public static String PROP_SCALE = "scale";
	public static String PROP_NATURE = "nature";
	public static String PROP_INDUSTRY = "industry";


	// constructors
	public BaseCmsSiteCompany () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseCmsSiteCompany (java.lang.Integer id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseCmsSiteCompany (
		java.lang.Integer id,
		java.lang.String name) {

		this.setId(id);
		this.setName(name);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.Integer id;

	// fields
	private java.lang.String name;
	private java.lang.String scale;
	private java.lang.String nature;
	private java.lang.String industry;
	private java.lang.String contact;
	private java.lang.String description;
	private java.lang.String address;
	private java.lang.Float longitude;
	private java.lang.Float latitude;

	// one to one
	private com.jeecms.core.entity.CmsSite site;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="foreign"
     *  column="site_id"
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
	 * Return the value associated with the column: scale
	 */
	public java.lang.String getScale () {
		return scale;
	}

	/**
	 * Set the value related to the column: scale
	 * @param scale the scale value
	 */
	public void setScale (java.lang.String scale) {
		this.scale = scale;
	}


	/**
	 * Return the value associated with the column: nature
	 */
	public java.lang.String getNature () {
		return nature;
	}

	/**
	 * Set the value related to the column: nature
	 * @param nature the nature value
	 */
	public void setNature (java.lang.String nature) {
		this.nature = nature;
	}


	/**
	 * Return the value associated with the column: industry
	 */
	public java.lang.String getIndustry () {
		return industry;
	}

	/**
	 * Set the value related to the column: industry
	 * @param industry the industry value
	 */
	public void setIndustry (java.lang.String industry) {
		this.industry = industry;
	}


	/**
	 * Return the value associated with the column: contact
	 */
	public java.lang.String getContact () {
		return contact;
	}

	/**
	 * Set the value related to the column: contact
	 * @param contact the contact value
	 */
	public void setContact (java.lang.String contact) {
		this.contact = contact;
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
	 * Return the value associated with the column: address
	 */
	public java.lang.String getAddress () {
		return address;
	}

	/**
	 * Set the value related to the column: address
	 * @param address the address value
	 */
	public void setAddress (java.lang.String address) {
		this.address = address;
	}


	/**
	 * Return the value associated with the column: longitude
	 */
	public java.lang.Float getLongitude () {
		return longitude;
	}

	/**
	 * Set the value related to the column: longitude
	 * @param longitude the longitude value
	 */
	public void setLongitude (java.lang.Float longitude) {
		this.longitude = longitude;
	}


	/**
	 * Return the value associated with the column: latitude
	 */
	public java.lang.Float getLatitude () {
		return latitude;
	}

	/**
	 * Set the value related to the column: latitude
	 * @param latitude the latitude value
	 */
	public void setLatitude (java.lang.Float latitude) {
		this.latitude = latitude;
	}


	/**
	 * Return the value associated with the column: site
	 */
	public com.jeecms.core.entity.CmsSite getSite () {
		return site;
	}

	/**
	 * Set the value related to the column: site
	 * @param site the site value
	 */
	public void setSite (com.jeecms.core.entity.CmsSite site) {
		this.site = site;
	}



	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.jeecms.core.entity.CmsSiteCompany)) return false;
		else {
			com.jeecms.core.entity.CmsSiteCompany cmsSiteCompany = (com.jeecms.core.entity.CmsSiteCompany) obj;
			if (null == this.getId() || null == cmsSiteCompany.getId()) return false;
			else return (this.getId().equals(cmsSiteCompany.getId()));
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
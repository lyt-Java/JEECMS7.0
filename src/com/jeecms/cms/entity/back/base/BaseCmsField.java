package com.jeecms.cms.entity.back.base;

import java.io.Serializable;

public abstract class BaseCmsField implements Serializable {

	// constructors
	public BaseCmsField() {
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// fields
	private java.lang.String name;
	private java.lang.String fieldType;
	private java.lang.String fieldDefault;
	private java.lang.String fieldProperty;
	private java.lang.String comment;
	private java.lang.String nullable;
	private java.lang.String extra;
	private java.lang.String length;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getFieldType() {
		return fieldType;
	}

	public void setFieldType(java.lang.String fieldType) {
		this.fieldType = fieldType;
	}

	public java.lang.String getFieldDefault() {
		return fieldDefault;
	}

	public void setFieldDefault(java.lang.String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}

	public java.lang.String getFieldProperty() {
		return fieldProperty;
	}

	public void setFieldProperty(java.lang.String fieldProperty) {
		this.fieldProperty = fieldProperty;
	}

	public java.lang.String getComment() {
		return comment;
	}

	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	public java.lang.String getNullable() {
		return nullable;
	}

	public void setNullable(java.lang.String nullable) {
		this.nullable = nullable;
	}

	public java.lang.String getExtra() {
		return extra;
	}

	public void setExtra(java.lang.String extra) {
		this.extra = extra;
	}

	public java.lang.String getLength() {
		return length;
	}

	public void setLength(java.lang.String length) {
		this.length = length;
	}

	public String toString() {
		return super.toString();
	}

}
package com.ylxx.fx.core.domain;

import java.io.Serializable;

public class QueryNationCodeVo implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private  String fieldName;
	private  String fieldValue;
	private  Integer pageNo;
	private  Integer pageSize;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}

package com.ylxx.fx.utils;

public enum ErrorCodeCust {
	
	E_00("SelectSUCCESS"),
	E_01("SelectNoCount"),
	E_02("SelectError"),
	
	E_100("InsertSuccess"),
	E_101("InsertRepeat"),
	E_102("InsertError"),
	
	E_200("UpdateSuccess"),
	E_201("UpdateError"),
	E_202("UpdateRepeat"),
	
	E_300("DeleteSuccess"),
	E_301("DeleteError"),
	;
	
	private String code;
	ErrorCodeCust(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
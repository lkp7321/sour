package com.ylxx.qt.utils;

public enum ErrorCode {
	E_00("SUCCESS"),
	E_01("ERROR"),
	
	;
	
	
	private String code;
	ErrorCode(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}


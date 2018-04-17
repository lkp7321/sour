package com.ylxx.fx.utils;

public enum ErrorCodePrice {
	
	E_00("SelSuccess"),
	E_01("SelNoCount"),
	E_02("SelError"),
	
	E_10("UpSuccess"),
	E_11("UpError"),
	
	E_20("InsSuccess"),
	E_21("InsError"),
	
	E_30("DelSuccess"),
	E_31("DelError"),
	
	E_40("RegisterSuccess"),
	E_41("RegisterError"),
	
	E_50("MoZhangSuccess"),
	E_51("MoZhangError"),
	
	E_60("HandleSuccess"),
	E_61("HandleError"),
	;
	
	private String code;
	ErrorCodePrice(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
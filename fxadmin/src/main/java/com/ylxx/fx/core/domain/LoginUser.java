package com.ylxx.fx.core.domain;

public class LoginUser {
	private String usnm;
	private String prod;
	public String getUsnm() {
		return usnm;
	}
	public void setUsnm(String usnm) {
		this.usnm = usnm;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public LoginUser(String usnm, String prod) {
		super();
		this.usnm = usnm;
		this.prod = prod;
	}
	public LoginUser() {
		super();
	}
	
}

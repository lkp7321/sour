package com.ylxx.fx.core.domain;

import com.ylxx.fx.service.po.CustLevelBean;

public class CustVo {
	private String userKey;
	private CustLevelBean custLevel;
	private LoginUser logUser;
	public CustLevelBean getCustLevel() {
		return custLevel;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public void setCustLevel(CustLevelBean custLevel) {
		this.custLevel = custLevel;
	}
	public LoginUser getLogUser() {
		return logUser;
	}
	public void setLogUser(LoginUser logUser) {
		this.logUser = logUser;
	}
	public CustVo(CustLevelBean custLevel, LoginUser logUser) {
		super();
		this.custLevel = custLevel;
		this.logUser = logUser;
	}
	public CustVo() {
		super();
	}
	
}

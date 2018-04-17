package com.ylxx.fx.core.domain;

import com.ylxx.fx.service.po.PdtinfoBean;

public class QutFreqtVo {
	private String userKey;
	private PdtinfoBean pdtinfo;
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public PdtinfoBean getPdtinfo() {
		return pdtinfo;
	}
	public void setPdtinfo(PdtinfoBean pdtinfo) {
		this.pdtinfo = pdtinfo;
	}
	public QutFreqtVo(PdtinfoBean pdtinfo, String userKey) {
		super();
		this.pdtinfo = pdtinfo;
		this.userKey = userKey;
	}
	public QutFreqtVo() {
		super();
	}
}

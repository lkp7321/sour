package com.ylxx.fx.service.po;

import java.math.BigDecimal;

public class Kon_RpcRequestBean {
	private String frontserialdata;
	private String downloadkey;
	private String tradecode;
	private String tradetype;
	private String reqtype;
	private BigDecimal retrytimes;
	private String state;
	// add by wsk RV前置修改  20121102
	private String requestdate;     //请求日期
	private String requesttime;     //请求时间
	public String getFrontserialdata() {
		return frontserialdata;
	}
	public void setFrontserialdata(String frontserialdata) {
		this.frontserialdata = frontserialdata;
	}
	public String getDownloadkey() {
		return downloadkey;
	}
	public void setDownloadkey(String downloadkey) {
		this.downloadkey = downloadkey;
	}
	public String getTradecode() {
		return tradecode;
	}
	public void setTradecode(String tradecode) {
		this.tradecode = tradecode;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getReqtype() {
		return reqtype;
	}
	public void setReqtype(String reqtype) {
		this.reqtype = reqtype;
	}
	public BigDecimal getRetrytimes() {
		return retrytimes;
	}
	public void setRetrytimes(BigDecimal retrytimes) {
		this.retrytimes = retrytimes;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getRequestdate() {
		return requestdate;
	}
	public void setRequestdate(String requestdate) {
		this.requestdate = requestdate;
	}
	public String getRequesttime() {
		return requesttime;
	}
	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}
}

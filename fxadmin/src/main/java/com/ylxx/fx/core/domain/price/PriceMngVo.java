package com.ylxx.fx.core.domain.price;

public class PriceMngVo {

	private String userKey; 
	private String data;	//	起始日期
	private String user;	//	用户名
	private String handle;	//	操作
	private String endata;	//	结束日期
	private Integer pageNo;
	private Integer pageSize;
	
	public String getEndata() {
		return endata;
	}
	public void setEndate(String endata) {
		this.endata = endata;
	}
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
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
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
}

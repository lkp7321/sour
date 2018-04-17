package com.ylxx.fx.core.domain;

public class PreSysManagerVo {
	private String dgfieldbegin;
	private String dgfieldend;
	private String comStcd;
	private Integer pageNo;//页数
	private Integer pageSize;//每一页的记录数
	public String getDgfieldbegin() {
		return dgfieldbegin;
	}
	public void setDgfieldbegin(String dgfieldbegin) {
		this.dgfieldbegin = dgfieldbegin;
	}
	public String getDgfieldend() {
		return dgfieldend;
	}
	public void setDgfieldend(String dgfieldend) {
		this.dgfieldend = dgfieldend;
	}
	public String getComStcd() {
		return comStcd;
	}
	public void setComStcd(String comStcd) {
		this.comStcd = comStcd;
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
	

}

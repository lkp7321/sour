package com.ylxx.fx.core.domain.price;

import com.ylxx.fx.service.po.calendar.OriginalVO;

public class PriceRecVo extends OriginalVO{
	private String mkpk;
	private String mkid;
	private String mknm;
	private String exnm;
	private String calendarID;
	private String calendarName;
	private boolean selected;
	private Integer pageNo;
	private Integer pageSize;
	
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
	public String getMkpk() {
		return mkpk;
	}
	public void setMkpk(String mkpk) {
		this.mkpk = mkpk;
	}
	public String getMkid() {
		return mkid;
	}
	public void setMkid(String mkid) {
		this.mkid = mkid;
	}
	public String getMknm() {
		return mknm;
	}
	public void setMknm(String mknm) {
		this.mknm = mknm;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getCalendarID() {
		return calendarID;
	}
	public void setCalendarID(String calendarID) {
		this.calendarID = calendarID;
	}
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

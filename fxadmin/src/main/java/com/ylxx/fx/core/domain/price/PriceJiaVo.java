package com.ylxx.fx.core.domain.price;

import com.ylxx.fx.service.po.calendar.OriginalVO;

public class PriceJiaVo extends OriginalVO {
	private String priceJiaPK;
	private String ptid;
	private String ptnm;
	private String calendarID;
	private String calendarName;
	public String getPriceJiaPK() {
		return priceJiaPK;
	}
	public void setPriceJiaPK(String priceJiaPK) {
		this.priceJiaPK = priceJiaPK;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getPtnm() {
		return ptnm;
	}
	public void setPtnm(String ptnm) {
		this.ptnm = ptnm;
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
	
}

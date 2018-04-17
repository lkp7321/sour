package com.ylxx.fx.service.po.calendar;

public class TradeProCalVO extends OriginalVO{
	private String ptid;        //产品编号
	private String ptnm;        //产品名称
	private String trcd;        //交易码
	private String trds;        //交易描述
	private String calendarID;  //与日历表中id关联
	private String calendarName;//日历名称
	private boolean selected;   //是否选中标志
	private String leveType;	//日历等级
	
	public String getLeveType() {
		return leveType;
	}
	public void setLeveType(String leveType) {
		this.leveType = leveType;
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
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getTrds() {
		return trds;
	}
	public void setTrds(String trds) {
		this.trds = trds;
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

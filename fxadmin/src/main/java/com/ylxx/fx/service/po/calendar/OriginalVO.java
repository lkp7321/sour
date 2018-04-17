package com.ylxx.fx.service.po.calendar;

public class OriginalVO {
	private String mark;      //备注
	private String calendarID;  //与日历表中id关联
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getCalendarID() {
		return calendarID;
	}
	public void setCalendarID(String calendarID) {
		this.calendarID = calendarID;
	}
	public OriginalVO(String mark, String calendarID) {
		super();
		this.mark = mark;
		this.calendarID = calendarID;
	}
	public OriginalVO() {
		super();
	}
	
}

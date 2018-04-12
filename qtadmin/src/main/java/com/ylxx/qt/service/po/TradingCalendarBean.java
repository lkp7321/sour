package com.ylxx.qt.service.po;

import java.io.Serializable;

public class TradingCalendarBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;
	private String mark;
	public TradingCalendarBean(int year, int month, String mark) {
		super();
		this.year = year;
		this.month = month;
		this.mark = mark;
	}
	public TradingCalendarBean() {
		super();
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	@Override
	public String toString() {
		return "TradingCalendarBean [year=" + year + ", month=" + month
				+ ", mark=" + mark + "]";
	}
	
}

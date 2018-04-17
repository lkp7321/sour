package com.ylxx.fx.service.po.calendar;

import java.io.Serializable;
import java.util.Date;

public class CalendarVO implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String calendarID;     //日历规则id
    private String calendarName;   //日历规则名称
    private String beginWeek;      //起始星期
    private String endWeek;        //结束星期
    private String beginDate;      //起始日期
    private String endDate;        //结束日期
    private String beginTime;      //起始时间
    private String endTime;        //结束时间
    private String flag;           //开闭盘标志（数字表示：0、开1、闭）
    private String levelType;      //规则等级 （数字表示：1、每周每日 2、每周特殊日3、年度特殊日）
    private String levelName;      //规则名称 即1、每周每日 2、每周特殊日3、年度特殊日
    private String isquantian;     //是否全天(数字表示：0、不是全天 1、是全天)
    private String quanflag;       //是否全天汉字表示（汉字表示：0、否    1、是）
    private String productId;      //产品id
    private String productName;    //产品名称
    private String week;           //当月中的第几周
    private Date begindatetime;    //开始日期时间   （格式：20120201 12:02:25）
    private Date enddatetime;      //结束日期时间    （格式：20120201 12:02:25）
    
    private String beginendtime;   //开始时间至结束时间（格式：02:02:01-12:10:25）
    private String flags;          //标志数组
	public CalendarVO(String calendarID, String calendarName, String beginWeek,
			String endWeek, String beginDate, String endDate, String beginTime,
			String endTime, String flag, String levelType, String levelName,
			String isquantian, String quanflag, String productId,
			String productName, String week, Date begindatetime,
			Date enddatetime, String beginendtime, String flags) {
		super();
		this.calendarID = calendarID;
		this.calendarName = calendarName;
		this.beginWeek = beginWeek;
		this.endWeek = endWeek;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.flag = flag;
		this.levelType = levelType;
		this.levelName = levelName;
		this.isquantian = isquantian;
		this.quanflag = quanflag;
		this.productId = productId;
		this.productName = productName;
		this.week = week;
		this.begindatetime = begindatetime;
		this.enddatetime = enddatetime;
		this.beginendtime = beginendtime;
		this.flags = flags;
	}
	public CalendarVO() {
		super();
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
	public String getBeginWeek() {
		return beginWeek;
	}
	public void setBeginWeek(String beginWeek) {
		this.beginWeek = beginWeek;
	}
	public String getEndWeek() {
		return endWeek;
	}
	public void setEndWeek(String endWeek) {
		this.endWeek = endWeek;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getIsquantian() {
		return isquantian;
	}
	public void setIsquantian(String isquantian) {
		this.isquantian = isquantian;
	}
	public String getQuanflag() {
		return quanflag;
	}
	public void setQuanflag(String quanflag) {
		this.quanflag = quanflag;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Date getBegindatetime() {
		return begindatetime;
	}
	public void setBegindatetime(Date begindatetime) {
		this.begindatetime = begindatetime;
	}
	public Date getEnddatetime() {
		return enddatetime;
	}
	public void setEnddatetime(Date enddatetime) {
		this.enddatetime = enddatetime;
	}
	public String getBeginendtime() {
		return beginendtime;
	}
	public void setBeginendtime(String beginendtime) {
		this.beginendtime = beginendtime;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
    
}

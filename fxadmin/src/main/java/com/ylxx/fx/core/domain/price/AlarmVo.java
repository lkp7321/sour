package com.ylxx.fx.core.domain.price;

import com.ylxx.fx.service.po.CmmAlarmCode;
import com.ylxx.fx.service.po.CmmAlarmLevel;
import com.ylxx.fx.service.po.CmmAlarmNotify;
import com.ylxx.fx.service.po.Cmmalarmuser;

public class AlarmVo {
	private String betime;
	private String endtime;
	private String ercd;
	private String userKey;
	private Cmmalarmuser alarmUser;
	private CmmAlarmCode alarmCode;
	private String alid;
	private CmmAlarmLevel alarmLevel;
	private CmmAlarmNotify notify;
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
	public CmmAlarmNotify getNotify() {
		return notify;
	}
	public void setNotify(CmmAlarmNotify notify) {
		this.notify = notify;
	}
	public CmmAlarmLevel getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(CmmAlarmLevel alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getAlid() {
		return alid;
	}
	public void setAlid(String alid) {
		this.alid = alid;
	}
	public CmmAlarmCode getAlarmCode() {
		return alarmCode;
	}
	public void setAlarmCode(CmmAlarmCode alarmCode) {
		this.alarmCode = alarmCode;
	}
	public Cmmalarmuser getAlarmUser() {
		return alarmUser;
	}
	public void setAlarmUser(Cmmalarmuser alarmUser) {
		this.alarmUser = alarmUser;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getBetime() {
		return betime;
	}
	public void setBetime(String betime) {
		this.betime = betime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	
}

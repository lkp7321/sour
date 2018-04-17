package com.ylxx.fx.core.domain;

import java.util.ArrayList;

import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public class ChannelFavRuleVo {

private	String userKey;
	private String ogcd;
	private String currency;
	private String chnl;
	private String byds;
	private String slds;
	private String beginDate;
	private String endDate;
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
	private ArrayList <Trd_FavruleOgcd> chlist;
	
	public ArrayList<Trd_FavruleOgcd> getChlist() {
		return chlist;
	}
	public void setChlist(ArrayList<Trd_FavruleOgcd> chlist) {
		this.chlist = chlist;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getChnl() {
		return chnl;
	}
	public void setChnl(String chnl) {
		this.chnl = chnl;
	}
	public String getByds() {
		return byds;
	}
	public void setByds(String byds) {
		this.byds = byds;
	}
	public String getSlds() {
		return slds;
	}
	public void setSlds(String slds) {
		this.slds = slds;
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
	
	
	
}

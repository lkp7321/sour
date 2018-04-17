package com.ylxx.fx.core.domain;

import java.util.ArrayList;

import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public class PereCardFavRuleVo {
	private String ogcd;
	private String cbin;
	private String byds;
	private String slds;
	private String stdt;
	private String eddt;
	private String userkey;
	private Integer pageNo;
	private Integer pageSize;
	
	private ArrayList <Trd_FavruleOgcd> chlist;
	
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
	public ArrayList<Trd_FavruleOgcd> getChlist() {
		return chlist;
	}
	public void setChlist(ArrayList<Trd_FavruleOgcd> chlist) {
		this.chlist = chlist;
	}
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getCbin() {
		return cbin;
	}
	public void setCbin(String cbin) {
		this.cbin = cbin;
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
	public String getStdt() {
		return stdt;
	}
	public void setStdt(String stdt) {
		this.stdt = stdt;
	}
	public String getEddt() {
		return eddt;
	}
	public void setEddt(String eddt) {
		this.eddt = eddt;
	}
	
	
}

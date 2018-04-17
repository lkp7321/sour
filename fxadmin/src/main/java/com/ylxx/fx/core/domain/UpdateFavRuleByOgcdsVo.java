package com.ylxx.fx.core.domain;

import java.util.ArrayList;

import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public class UpdateFavRuleByOgcdsVo {
	private String userKey;
	private ArrayList <Trd_FavruleOgcd> chlist;
	private String ogcd;
	private String byds;
	private String slds;
	private String beginDate;
	private String endDate;

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	

	public ArrayList<Trd_FavruleOgcd> getChlist() {
		return chlist;
	}

	public void setChlist(ArrayList<Trd_FavruleOgcd> chlist) {
		this.chlist = chlist;
	}

	public String getOgcd() {
		return ogcd;
	}

	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
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

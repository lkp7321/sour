package com.ylxx.fx.core.domain;

import java.util.ArrayList;

import com.ylxx.fx.service.po.Trd_FavruleOgcd;

public class DeleteOrgFavrRuleVo {
	private String userKey;
	private ArrayList<Trd_FavruleOgcd>  chlist;
	private String ogcd;
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
	
}

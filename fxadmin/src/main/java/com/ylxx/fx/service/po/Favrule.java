package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Favrule implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ogcd;
	private String ognm;
	private String fvid;
	private String fvnm;
	private String rule;
	private String stat;
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
	public String getFvid() {
		return fvid;
	}
	public void setFvid(String fvid) {
		this.fvid = fvid;
	}
	public String getFvnm() {
		return fvnm;
	}
	public void setFvnm(String fvnm) {
		this.fvnm = fvnm;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Favrule(String ogcd, String ognm, String fvid, String fvnm,
			String rule, String stat) {
		super();
		this.ogcd = ogcd;
		this.ognm = ognm;
		this.fvid = fvid;
		this.fvnm = fvnm;
		this.rule = rule;
		this.stat = stat;
	}
	public Favrule() {
		super();
	}
		
}

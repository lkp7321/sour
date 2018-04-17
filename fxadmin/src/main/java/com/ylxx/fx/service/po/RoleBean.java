package com.ylxx.fx.service.po;

import java.io.Serializable;

public class RoleBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ptid;
	private String ptnm;
	private String usfg;
	private String remk;
	private String ogcd;
	private String opfg;
	private String prod;
	private String rofg;
	public RoleBean() {
		super();
	}
	public RoleBean(String ptid, String ptnm, String usfg, String remk,
			String ogcd, String opfg, String prod, String rofg) {
		super();
		this.ptid = ptid;
		this.ptnm = ptnm;
		this.usfg = usfg;
		this.remk = remk;
		this.ogcd = ogcd;
		this.opfg = opfg;
		this.prod = prod;
		this.rofg = rofg;
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
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getOpfg() {
		return opfg;
	}
	public void setOpfg(String opfg) {
		this.opfg = opfg;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getRofg() {
		return rofg;
	}
	public void setRofg(String rofg) {
		this.rofg = rofg;
	}
	
	
	
	
}

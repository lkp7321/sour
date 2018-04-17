package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Trd_ogcd implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ogcd;
	private String ognm;
	private String usfg;
	private String leve;
	private String ogup;
	private String orgn;
	private String fatherLeve;
	
	public Trd_ogcd() {
		super();
	}

	public Trd_ogcd(String ogcd, String ognm, String usfg, String leve,
			String ogup, String orgn, String fatherLeve) {
		super();
		this.ogcd = ogcd;
		this.ognm = ognm;
		this.usfg = usfg;
		this.leve = leve;
		this.ogup = ogup;
		this.orgn = orgn;
		this.fatherLeve = fatherLeve;
	}

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

	public String getUsfg() {
		return usfg;
	}

	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}

	public String getLeve() {
		return leve;
	}

	public void setLeve(String leve) {
		this.leve = leve;
	}

	public String getOgup() {
		return ogup;
	}

	public void setOgup(String ogup) {
		this.ogup = ogup;
	}

	public String getOrgn() {
		return orgn;
	}

	public void setOrgn(String orgn) {
		this.orgn = orgn;
	}

	public String getFatherLeve() {
		return fatherLeve;
	}

	public void setFatherLeve(String fatherLeve) {
		this.fatherLeve = fatherLeve;
	}
	
}

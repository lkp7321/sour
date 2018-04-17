package com.ylxx.fx.service.po;

import java.io.Serializable;

public class TrdTynm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tynm;
	private String apfg;
	private String stat;
	private int fvda;
	public String getTynm() {
		return tynm;
	}
	public void setTynm(String tynm) {
		this.tynm = tynm;
	}
	public String getApfg() {
		return apfg;
	}
	public void setApfg(String apfg) {
		this.apfg = apfg;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public int getFvda() {
		return fvda;
	}
	public void setFvda(int fvda) {
		this.fvda = fvda;
	}
	public TrdTynm(String tynm, String apfg, String stat, int fvda) {
		super();
		this.tynm = tynm;
		this.apfg = apfg;
		this.stat = stat;
		this.fvda = fvda;
	}
	public TrdTynm() {
		super();
	}
}

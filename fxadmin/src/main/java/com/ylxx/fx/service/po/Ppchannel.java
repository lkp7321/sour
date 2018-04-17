package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Ppchannel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prcd;
	private String exnm;
	private String stat;
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Ppchannel(String prcd, String exnm, String stat) {
		super();
		this.prcd = prcd;
		this.exnm = exnm;
		this.stat = stat;
	}
	public Ppchannel() {
		super();
	}
	
}

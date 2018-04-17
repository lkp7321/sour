package com.ylxx.fx.service.po.calendar;

import java.io.Serializable;

public class TradeCodeVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String odcd;         //交易码
	private String trcd;         //交易码
	private String trds;         //交易名称
	private String sevd;
	public String getOdcd() {
		return odcd;
	}
	public void setOdcd(String odcd) {
		this.odcd = odcd;
	}
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getTrds() {
		return trds;
	}
	public void setTrds(String trds) {
		this.trds = trds;
	}
	public String getSevd() {
		return sevd;
	}
	public void setSevd(String sevd) {
		this.sevd = sevd;
	}
	public TradeCodeVO(String odcd, String trcd, String trds, String sevd) {
		super();
		this.odcd = odcd;
		this.trcd = trcd;
		this.trds = trds;
		this.sevd = sevd;
	}
	public TradeCodeVO() {
		super();
	}   
	
}

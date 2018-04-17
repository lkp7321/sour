package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Ptpara implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String paid;
	private String remk;
	private String valu;
	private String stat;
	public Ptpara(String paid, String remk, String valu, String stat) {
		super();
		this.paid = paid;
		this.remk = remk;
		this.valu = valu;
		this.stat = stat;
	}
	public Ptpara() {
		super();
	}
	public String getPaid() {
		return paid;
	}
	public void setPaid(String paid) {
		this.paid = paid;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public String getValu() {
		return valu;
	}
	public void setValu(String valu) {
		this.valu = valu;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	

}

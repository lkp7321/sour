package com.ylxx.fx.service.po;

import java.io.Serializable;

public class TrdSpcut implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tynm;
	private String apfg;
	private String cuno;
	private String stat;
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
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public TrdSpcut(String tynm, String apfg, String cuno, String stat) {
		super();
		this.tynm = tynm;
		this.apfg = apfg;
		this.cuno = cuno;
		this.stat = stat;
	}
	public TrdSpcut() {
		super();
	}
	
	
}

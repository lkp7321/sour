package com.ylxx.fx.service.po;

import java.io.Serializable;

public class PdtValidateBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String tpnm;
	private String tpfg;
	private String term;
	private String exnm;
	private String excd;
	private String cxfg;
	private Integer mxdt;//原为Number类型，注意在使用中是否转为其他类型!!!!
	private String usfg;
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
	public String getTpfg() {
		return tpfg;
	}
	public void setTpfg(String tpfg) {
		this.tpfg = tpfg;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public Integer getMxdt() {
		return mxdt;
	}
	public void setMxdt(Integer mxdt) {
		this.mxdt = mxdt;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
}

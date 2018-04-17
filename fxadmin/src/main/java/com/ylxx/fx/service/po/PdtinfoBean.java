package com.ylxx.fx.service.po;

import java.io.Serializable;

public class PdtinfoBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String ptid;
	private String prnm;
	private String ptnm;
	private String qtty;
	private String tpnm;
	private String term;
	private String cxfg;
	private String frqy;
	private String usfg;
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getPrnm() {
		return prnm;
	}
	public void setPrnm(String prnm) {
		this.prnm = prnm;
	}
	public String getPtnm() {
		return ptnm;
	}
	public void setPtnm(String ptnm) {
		this.ptnm = ptnm;
	}
	public String getQtty() {
		return qtty;
	}
	public void setQtty(String qtty) {
		this.qtty = qtty;
	}
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getFrqy() {
		return frqy;
	}
	public void setFrqy(String frqy) {
		this.frqy = frqy;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public PdtinfoBean(String ptid, String prnm, String ptnm, String qtty,
			String term, String cxfg, String frqy, String usfg) {
		super();
		this.ptid = ptid;
		this.prnm = prnm;
		this.ptnm = ptnm;
		this.qtty = qtty;
		this.term = term;
		this.cxfg = cxfg;
		this.frqy = frqy;
		this.usfg = usfg;
	}
	public PdtinfoBean() {
		super();
	}
}

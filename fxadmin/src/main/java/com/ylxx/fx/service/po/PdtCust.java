package com.ylxx.fx.service.po;

import java.io.Serializable;

public class PdtCust implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sequ;
	private String tpfg;	
	private String term;
	private String excd;
	private String cxfg;	
	private String exnm;
	private String mdtm;
	private String vltm;
	private String nemd;
	private String neby;
	private String nesl;
	private String famd;
	private String faby;
	private String fasl;
	private String udfg;
	private String prsc;
	private String stop;
	private String trfg;
	private String stfg;
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
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
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getMdtm() {
		return mdtm;
	}
	public void setMdtm(String mdtm) {
		this.mdtm = mdtm;
	}
	public String getVltm() {
		return vltm;
	}
	public void setVltm(String vltm) {
		this.vltm = vltm;
	}
	public String getNemd() {
		return nemd;
	}
	public void setNemd(String nemd) {
		this.nemd = nemd;
	}
	public String getNeby() {
		return neby;
	}
	public void setNeby(String neby) {
		this.neby = neby;
	}
	public String getNesl() {
		return nesl;
	}
	public void setNesl(String nesl) {
		this.nesl = nesl;
	}
	public String getFamd() {
		return famd;
	}
	public void setFamd(String famd) {
		this.famd = famd;
	}
	public String getFaby() {
		return faby;
	}
	public void setFaby(String faby) {
		this.faby = faby;
	}
	public String getFasl() {
		return fasl;
	}
	public void setFasl(String fasl) {
		this.fasl = fasl;
	}
	public String getUdfg() {
		return udfg;
	}
	public void setUdfg(String udfg) {
		this.udfg = udfg;
	}
	public String getPrsc() {
		return prsc;
	}
	public void setPrsc(String prsc) {
		this.prsc = prsc;
	}
	public String getStop() {
		return stop;
	}
	public void setStop(String stop) {
		this.stop = stop;
	}
	public String getTrfg() {
		return trfg;
	}
	public void setTrfg(String trfg) {
		this.trfg = trfg;
	}
	public String getStfg() {
		return stfg;
	}
	public void setStfg(String stfg) {
		this.stfg = stfg;
	}
	public PdtCust(String sequ, String tpfg, String term, String excd,
			String cxfg, String exnm, String mdtm, String vltm, String nemd,
			String neby, String nesl, String famd, String faby, String fasl,
			String udfg, String prsc, String stop, String trfg, String stfg) {
		super();
		this.sequ = sequ;
		this.tpfg = tpfg;
		this.term = term;
		this.excd = excd;
		this.cxfg = cxfg;
		this.exnm = exnm;
		this.mdtm = mdtm;
		this.vltm = vltm;
		this.nemd = nemd;
		this.neby = neby;
		this.nesl = nesl;
		this.famd = famd;
		this.faby = faby;
		this.fasl = fasl;
		this.udfg = udfg;
		this.prsc = prsc;
		this.stop = stop;
		this.trfg = trfg;
		this.stfg = stfg;
	}
	public PdtCust() {
		super();
	}
	
}

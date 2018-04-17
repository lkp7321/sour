package com.ylxx.fx.service.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class PdtChkParaBean implements Serializable{
	//已检查，+3(ptnm、ptid、selected)
	private static final long serialVersionUID = -2682305557890221059L;
	private String ptnm;
	private String ptid;
	private String tpfg;
	private String tpnm;
	private String term;
	private String exnm;
	private String cxfg;
	private String excd;
	private String ximd;
	private String xxmd;
	public String getXimd() {
		return ximd;
	}
	public void setXimd(String ximd) {
		this.ximd = ximd;
	}
	public String getXxmd() {
		return xxmd;
	}
	public void setXxmd(String xxmd) {
		this.xxmd = xxmd;
	}
	private BigDecimal mxmd;
	private BigDecimal mdmd;
	private BigDecimal mimd;
	private BigDecimal mxdp;
	private BigDecimal mxbp;
	private BigDecimal mxct;
	private BigDecimal ctnu;
	private BigDecimal udnu;
	private BigDecimal mxft;
	private BigDecimal mdft;
	private BigDecimal mift;
	private BigDecimal mxud;
	private BigDecimal qjft;
	private BigDecimal blft;
	private String usfg;
	private boolean selected;
	
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
	public String getPtnm() {
		return ptnm;
	}
	public void setPtnm(String ptnm) {
		this.ptnm = ptnm;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
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
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}	
	
	public BigDecimal getMxmd() {
		return mxmd;
	}
	public void setMxmd(BigDecimal mxmd) {
		this.mxmd = mxmd;
	}
	public BigDecimal getMdmd() {
		return mdmd;
	}
	public void setMdmd(BigDecimal mdmd) {
		this.mdmd = mdmd;
	}
	public BigDecimal getMimd() {
		return mimd;
	}
	public void setMimd(BigDecimal mimd) {
		this.mimd = mimd;
	}
	public BigDecimal getMxdp() {
		return mxdp;
	}
	public void setMxdp(BigDecimal mxdp) {
		this.mxdp = mxdp;
	}
	public BigDecimal getMxbp() {
		return mxbp;
	}
	public void setMxbp(BigDecimal mxbp) {
		this.mxbp = mxbp;
	}
	public BigDecimal getMxct() {
		return mxct;
	}
	public void setMxct(BigDecimal mxct) {
		this.mxct = mxct;
	}
	public BigDecimal getCtnu() {
		return ctnu;
	}
	public void setCtnu(BigDecimal ctnu) {
		this.ctnu = ctnu;
	}
	public BigDecimal getUdnu() {
		return udnu;
	}
	public void setUdnu(BigDecimal udnu) {
		this.udnu = udnu;
	}
	public BigDecimal getMxft() {
		return mxft;
	}
	public void setMxft(BigDecimal mxft) {
		this.mxft = mxft;
	}
	public BigDecimal getMdft() {
		return mdft;
	}
	public void setMdft(BigDecimal mdft) {
		this.mdft = mdft;
	}
	public BigDecimal getMift() {
		return mift;
	}
	public void setMift(BigDecimal mift) {
		this.mift = mift;
	}
	public BigDecimal getMxud() {
		return mxud;
	}
	public void setMxud(BigDecimal mxud) {
		this.mxud = mxud;
	}
	public BigDecimal getQjft() {
		return qjft;
	}
	public void setQjft(BigDecimal qjft) {
		this.qjft = qjft;
	}
	public BigDecimal getBlft() {
		return blft;
	}
	public void setBlft(BigDecimal blft) {
		this.blft = blft;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}

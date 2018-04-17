package com.ylxx.fx.service.po;

import java.io.Serializable;

public class PdtRParaTBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String tpfg;//价格类型
	private String tpnm;
	private String exse;//币别对序号
	private String term;//期限
	private String cxfg;//钞汇标志
	private String excd;//币别对编号
	private String exnm;//币别对名称
	private String mkst;//市场源列表（以”|”分割）
	private String mksl;//权重列表（顺序与MKST相对应）（以”|”分割）
	private String mklv;//市场级别（顺序与MKST相对应）（以”|”分割）
	private String pmid;//策略编号
	private String gmnm;//策略名称
	public String getTpfg() {
		return tpfg;
	}
	public void setTpfg(String tpfg) {
		this.tpfg = tpfg;
	}
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
	public String getExse() {
		return exse;
	}
	public void setExse(String exse) {
		this.exse = exse;
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
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getMkst() {
		return mkst;
	}
	public void setMkst(String mkst) {
		this.mkst = mkst;
	}
	public String getMksl() {
		return mksl;
	}
	public void setMksl(String mksl) {
		this.mksl = mksl;
	}
	public String getMklv() {
		return mklv;
	}
	public void setMklv(String mklv) {
		this.mklv = mklv;
	}
	public String getPmid() {
		return pmid;
	}
	public void setPmid(String pmid) {
		this.pmid = pmid;
	}
	public String getGmnm() {
		return gmnm;
	}
	public void setGmnm(String gmnm) {
		this.gmnm = gmnm;
	}
}

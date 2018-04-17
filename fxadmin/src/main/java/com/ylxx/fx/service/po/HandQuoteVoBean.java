package com.ylxx.fx.service.po;

import java.io.Serializable;
import java.math.BigDecimal;

public class HandQuoteVoBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String mkid;       //市场编号(报价源)
	private String tpfg;       //价格类型
	private String tpnm;       //价格名称
	private String term;       //期限
	private String exnm;       //币别对名称
	private String cxfg;       //钞汇标志
	private String excd;       //币别对编号
	private BigDecimal neby;       //近端买入价
	private BigDecimal nesl;       //近端卖出价
	private BigDecimal nemd;       //近端中间价
	private String ocfg;       //启用标志
	private String prcd;       //产品编号
	private String bcfg;       //客户价/结售汇价
	private String stfg;       //状态
	private String opnm;       //操作人
	private String optm;       //操作时间
	private String opac;       //操作动作
	private boolean selected;  //是否选中
	public String getMkid() {
		return mkid;
	}
	public void setMkid(String mkid) {
		this.mkid = mkid;
	}
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
	public BigDecimal getNeby() {
		return neby;
	}
	public void setNeby(BigDecimal neby) {
		this.neby = neby;
	}
	public BigDecimal getNesl() {
		return nesl;
	}
	public void setNesl(BigDecimal nesl) {
		this.nesl = nesl;
	}
	public BigDecimal getNemd() {
		return nemd;
	}
	public void setNemd(BigDecimal nemd) {
		this.nemd = nemd;
	}
	public String getOcfg() {
		return ocfg;
	}
	public void setOcfg(String ocfg) {
		this.ocfg = ocfg;
	}
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getBcfg() {
		return bcfg;
	}
	public void setBcfg(String bcfg) {
		this.bcfg = bcfg;
	}
	public String getStfg() {
		return stfg;
	}
	public void setStfg(String stfg) {
		this.stfg = stfg;
	}
	public String getOpnm() {
		return opnm;
	}
	public void setOpnm(String opnm) {
		this.opnm = opnm;
	}
	public String getOptm() {
		return optm;
	}
	public void setOptm(String optm) {
		this.optm = optm;
	}
	public String getOpac() {
		return opac;
	}
	public void setOpac(String opac) {
		this.opac = opac;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

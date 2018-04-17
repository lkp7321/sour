package com.ylxx.fx.service.po;

import java.math.BigDecimal;

public class RateBean {
	
	private String pdtp;//利率产品代码
	private String desp;//产品描述
	private String pdtm;//产品期限
	private String pdbr;//计息方式
	private BigDecimal rate;//利率
	private BigDecimal mint;//起存克数
	private String updt;//更新日期
	private String jxtp;//类型
	private BigDecimal gain;//份额
	public String getPdtp() {
		return pdtp;
	}
	public void setPdtp(String pdtp) {
		this.pdtp = pdtp;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getPdtm() {
		return pdtm;
	}
	public void setPdtm(String pdtm) {
		this.pdtm = pdtm;
	}
	public String getPdbr() {
		return pdbr;
	}
	public void setPdbr(String pdbr) {
		this.pdbr = pdbr;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public BigDecimal getMint() {
		return mint;
	}
	public void setMint(BigDecimal mint) {
		this.mint = mint;
	}
	public String getUpdt() {
		return updt;
	}
	public void setUpdt(String updt) {
		this.updt = updt;
	}
	public String getJxtp() {
		return jxtp;
	}
	public void setJxtp(String jxtp) {
		this.jxtp = jxtp;
	}
	public BigDecimal getGain() {
		return gain;
	}
	public void setGain(BigDecimal gain) {
		this.gain = gain;
	}
	
	
	
	
}

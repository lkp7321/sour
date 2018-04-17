package com.ylxx.fx.service.po;

import java.math.BigDecimal;

public class AccinfoMonitorBean {
	private String accDate;//日期
	private String nowTime;//时间
	private String ogcd;//机构号
	private String ognm;//机构名称
	private BigDecimal hqam;//活期累计量
	private BigDecimal dqam;//定期累计量
	private BigDecimal amnt;//总累计量
	public String getAccDate() {
		return accDate;
	}
	public void setAccDate(String accDate) {
		this.accDate = accDate;
	}
	public String getNowTime() {
		return nowTime;
	}
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
	}
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
	public BigDecimal getHqam() {
		return hqam;
	}
	public void setHqam(BigDecimal hqam) {
		this.hqam = hqam;
	}
	public BigDecimal getDqam() {
		return dqam;
	}
	public void setDqam(BigDecimal dqam) {
		this.dqam = dqam;
	}
	public BigDecimal getAmnt() {
		return amnt;
	}
	public void setAmnt(BigDecimal amnt) {
		this.amnt = amnt;
	}
	
}

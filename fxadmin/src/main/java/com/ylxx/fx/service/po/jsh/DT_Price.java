package com.ylxx.fx.service.po.jsh;

import java.math.BigDecimal;
/**
 * 定投价格
 * @author lz130
 *
 */
public class DT_Price {
	/**
	 * 币别对
	 */
	private String exnm;
	/**
	 * 日期
	 */
	private String trdt;
	/**
	 * 时间
	 */
	private String trtm;
	/**
	 * 客户买入价
	 */
	private BigDecimal tcby;
	/**
	 * 客户卖出价
	 */
	private BigDecimal tcsl;
	/**
	 * 客户中间价
	 */
	private BigDecimal tcmd;
	/**
	 * 价格序列号
	 */
	private String sequ;
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public BigDecimal getTcby() {
		return tcby;
	}
	public void setTcby(BigDecimal tcby) {
		this.tcby = tcby;
	}
	public BigDecimal getTcsl() {
		return tcsl;
	}
	public void setTcsl(BigDecimal tcsl) {
		this.tcsl = tcsl;
	}
	public BigDecimal getTcmd() {
		return tcmd;
	}
	public void setTcmd(BigDecimal tcmd) {
		this.tcmd = tcmd;
	}
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	
	
	
}

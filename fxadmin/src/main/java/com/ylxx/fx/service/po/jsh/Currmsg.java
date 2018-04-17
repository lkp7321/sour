package com.ylxx.fx.service.po.jsh;

import java.io.Serializable;
/**
 * 货币对
 * 货币对表CMM_CURRMSG_P004
 * @author lz130
 *
 */
public class Currmsg implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 币别对名称，6位英文字母如USDRMB等
	 */
	private String exnm;
	/**
	 * 币别对编号,4位数字如1401
	 */
	private String excd;
	/**
	 * 币别对中文名称
	 */
	private String excn;
	/**
	 * 价格位点数，1位数字
	 */
	private String pion;
	/**
	 * 币别对类型，未使用
	 */
	private String extp;
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
	public String getExcn() {
		return excn;
	}
	public void setExcn(String excn) {
		this.excn = excn;
	}
	public String getPion() {
		return pion;
	}
	public void setPion(String pion) {
		this.pion = pion;
	}
	public String getExtp() {
		return extp;
	}
	public void setExtp(String extp) {
		this.extp = extp;
	}
	
}

package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Currmsg implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String exnm;	//币别对名称
	private String excd;	//币别对编号
	private String excn;	//币别对中文名称
	private String pion;	//价格位点数
	private String extp;	//币别对类型
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
	public Currmsg(String exnm, String excd, String excn, String pion,
			String extp) {
		super();
		this.exnm = exnm;
		this.excd = excd;
		this.excn = excn;
		this.pion = pion;
		this.extp = extp;
	}
	public Currmsg() {
		super();
	}
		
}

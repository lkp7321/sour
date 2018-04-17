package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Cytp implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cytp;	//币种代码		
	private String cyen;	//币种英文名简称	
	private String cycn;	//币种中文名	
	private String usfg;	//启用标志	
	private String remk;	//参数说明
	public String getCytp() {
		return cytp;
	}
	public void setCytp(String cytp) {
		this.cytp = cytp;
	}
	public String getCyen() {
		return cyen;
	}
	public void setCyen(String cyen) {
		this.cyen = cyen;
	}
	public String getCycn() {
		return cycn;
	}
	public void setCycn(String cycn) {
		this.cycn = cycn;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public Cytp(String cytp, String cyen, String cycn, String usfg, String remk) {
		super();
		this.cytp = cytp;
		this.cyen = cyen;
		this.cycn = cycn;
		this.usfg = usfg;
		this.remk = remk;
	}
	public Cytp() {
		super();
	}
	
}

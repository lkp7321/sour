package com.ylxx.fx.core.domain;

import java.io.Serializable;
//原系统中没有的实体类，自己根据需求进行定义的
public class CmmCymsgBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String cyen;//币种英文名简称
	private String cytp;//币种代码
	private String cycn;//币种中文名
	private String usfg;//启用标志
	public String getCyen() {
		return cyen;
	}
	public void setCyen(String cyen) {
		this.cyen = cyen;
	}
	public String getCytp() {
		return cytp;
	}
	public void setCytp(String cytp) {
		this.cytp = cytp;
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
	
}

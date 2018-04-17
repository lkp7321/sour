package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Sysctl implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usfg;	
	private String opno;
	private String trtm;
	private String prod;
	private int valu;
	
	public int getValu() {
		return valu;
	}
	public void setValu(int valu) {
		this.valu = valu;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getOpno() {
		return opno;
	}
	public void setOpno(String opno) {
		this.opno = opno;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public Sysctl(String usfg, String opno, String trtm, 
			String prod, int valu) {
		super();
		this.usfg = usfg;
		this.opno = opno;
		this.trtm = trtm;
		this.prod = prod;
		this.valu = valu;
	}
	public Sysctl() {
		super();
	}
	
}

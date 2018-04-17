package com.ylxx.fx.service.po;

import java.io.Serializable;

public class CurrMarketBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String mkid;
	private String mknm;
	private String mksl;
	private String mklv;
	public String getMkid() {
		return mkid;
	}
	public void setMkid(String mkid) {
		this.mkid = mkid;
	}
	public String getMknm() {
		return mknm;
	}
	public void setMknm(String mknm) {
		this.mknm = mknm;
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
}

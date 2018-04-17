package com.ylxx.fx.service.po;

import java.io.Serializable;

public class MaxFavPointBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String ogcd;
	private String ognm;
	private String exnm;
	private String excd;
	private String mxfv;
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
	public String getMxfv() {
		return mxfv;
	}
	public void setMxfv(String mxfv) {
		this.mxfv = mxfv;
	}
}

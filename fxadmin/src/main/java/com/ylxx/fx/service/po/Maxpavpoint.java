package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Maxpavpoint implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ogcd;
	private String exnm;
	private String excd;
	private String mxfv;
	private String ognm;
	public String getOgcd() {
		return ogcd;
	}
	public void setOgcd(String ogcd) {
		this.ogcd = ogcd;
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
	public String getOgnm() {
		return ognm;
	}
	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}
	public Maxpavpoint(String ogcd, String exnm, String excd, String mxfv,
			String ognm) {
		super();
		this.ogcd = ogcd;
		this.exnm = exnm;
		this.excd = excd;
		this.mxfv = mxfv;
		this.ognm = ognm;
	}
	public Maxpavpoint() {
		super();
	}
	

}

package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Ck_Dictionary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prcd;
	private String zcen;
	private String zdhy;
	private String mben;
	private String mbhy;
	private String usfg;
	private String memo;
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getZcen() {
		return zcen;
	}
	public void setZcen(String zcen) {
		this.zcen = zcen;
	}
	public String getZdhy() {
		return zdhy;
	}
	public void setZdhy(String zdhy) {
		this.zdhy = zdhy;
	}
	public String getMben() {
		return mben;
	}
	public void setMben(String mben) {
		this.mben = mben;
	}
	public String getMbhy() {
		return mbhy;
	}
	public void setMbhy(String mbhy) {
		this.mbhy = mbhy;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Ck_Dictionary(String prcd, String zcen, String zdhy, String mben,
			String mbhy, String usfg, String memo) {
		super();
		this.prcd = prcd;
		this.zcen = zcen;
		this.zdhy = zdhy;
		this.mben = mben;
		this.mbhy = mbhy;
		this.usfg = usfg;
		this.memo = memo;
	}
	public Ck_Dictionary() {
		super();
	}
	
}

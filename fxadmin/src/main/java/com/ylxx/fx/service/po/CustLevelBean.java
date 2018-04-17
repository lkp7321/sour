package com.ylxx.fx.service.po;

import java.io.Serializable;

public class CustLevelBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String cuty;//客户级别
	private String tynm;//级别名称
	public String getCuty() {
		return cuty;
	}
	public void setCuty(String cuty) {
		this.cuty = cuty;
	}
	public String getTynm() {
		return tynm;
	}
	public void setTynm(String tynm) {
		this.tynm = tynm;
	}
	public CustLevelBean(String cuty, String tynm) {
		super();
		this.cuty = cuty;
		this.tynm = tynm;
	}
	public CustLevelBean() {
		super();
	}
}

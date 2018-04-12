package com.ylxx.qt.service.po;


import java.io.Serializable;


public class AccountBean  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String userpassword;
	private String ctpbrokerid;
	private String inorout;
	private String marginnumber;
	private String marginpassword;
	private String isrank;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getCtpbrokerid() {
		return ctpbrokerid;
	}
	public void setCtpbrokerid(String ctpbrokerid) {
		this.ctpbrokerid = ctpbrokerid;
	}
	public String getInorout() {
		return inorout;
	}
	public void setInorout(String inorout) {
		this.inorout = inorout;
	}
	public String getMarginnumber() {
		return marginnumber;
	}
	public void setMarginnumber(String marginnumber) {
		this.marginnumber = marginnumber;
	}
	public String getMarginpassword() {
		return marginpassword;
	}
	public void setMarginpassword(String marginpassword) {
		this.marginpassword = marginpassword;
	}
	public String getIsrank() {
		return isrank;
	}
	public void setIsrank(String isrank) {
		this.isrank = isrank;
	}
	
	
	

}

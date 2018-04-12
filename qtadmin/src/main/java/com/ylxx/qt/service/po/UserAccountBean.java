package com.ylxx.qt.service.po;

import java.io.Serializable;

/**
 * 
 * @author zouhang
 *
 */
public class UserAccountBean  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String investor;
	private String username;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getInvestor() {
		return investor;
	}
	public void setInvestor(String investor) {
		this.investor = investor;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}

package com.ylxx.fx.core.domain;

import java.io.Serializable;

public class ChangeCunoVo implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String userKey;
	private String oldcuno;
	private String newcuno;
	private String caty;
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getOldcuno() {
		return oldcuno;
	}
	public void setOldcuno(String oldcuno) {
		this.oldcuno = oldcuno;
	}
	public String getNewcuno() {
		return newcuno;
	}
	public void setNewcuno(String newcuno) {
		this.newcuno = newcuno;
	}
	public String getCaty() {
		return caty;
	}
	public void setCaty(String caty) {
		this.caty = caty;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}

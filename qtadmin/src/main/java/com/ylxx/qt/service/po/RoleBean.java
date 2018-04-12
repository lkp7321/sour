package com.ylxx.qt.service.po;

import java.io.Serializable;

public class RoleBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String roleid;
	private String rolename;
	private String username;
	private String userid;
	private String isnull;
	
	public RoleBean(){
		
	}

	public RoleBean(String roleid, String rolename) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
	}
	
	public RoleBean(String userid,String username,String roleid) {
		super();
		this.username = username;
		this.userid = userid;
		this.roleid = roleid;
	}

	@Override
	public String toString() {
		return "RoleBean [roleid=" + roleid + ", rolename=" + rolename + "]";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIsnull() {
		return isnull;
	}

	public void setIsnull(String isnull) {
		this.isnull = isnull;
	}
	
	
}

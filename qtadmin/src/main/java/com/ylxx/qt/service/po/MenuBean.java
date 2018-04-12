package com.ylxx.qt.service.po;

import java.io.Serializable;

public class MenuBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String menuid;
	private String menuname;
	private String menuurl;
	private String username;
	private String parentMenu;
	private String roleid;
	private String userid;
	private String isnull;
	private String isshow;

	public MenuBean(){
		
	}
	
	
	
	public String getIsshow() {
		return isshow;
	}



	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}



	public String getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(String parentMenu) {
		this.parentMenu = parentMenu;
	}

	
	public MenuBean(String menuid, String roleid) {
		super();
		this.menuid = menuid;
		this.roleid = roleid;
	}

	public MenuBean(String menuid, String menuname, String menuurl,
			 String roleid) {
		super();
		this.menuid = menuid;
		this.menuname = menuname;
		this.menuurl = menuurl;
		this.roleid = roleid;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getMenuurl() {
		return menuurl;
	}

	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@Override
	public String toString() {
		return "MenuBean [menuid=" + menuid + ", menuname=" + menuname
				+ ", menuurl=" + menuurl + ", parentmenu="
				+ ", roleid=" + roleid + "]";
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

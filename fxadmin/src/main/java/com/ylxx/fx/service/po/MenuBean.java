package com.ylxx.fx.service.po;

import java.io.Serializable;

public class MenuBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idno;
	private String sbmn;
	private String clas;
	private String urls;
	private String parn;
	public MenuBean() {
		super();
	}
	public MenuBean(String idno, String sbmn, String clas, String urls,
			String parn) {
		super();
		this.idno = idno;
		this.sbmn = sbmn;
		this.clas = clas;
		this.urls = urls;
		this.parn = parn;
	}
	public String getIdno() {
		return idno;
	}
	public void setIdno(String idno) {
		this.idno = idno;
	}
	public String getSbmn() {
		return sbmn;
	}
	public void setSbmn(String sbmn) {
		this.sbmn = sbmn;
	}
	public String getClas() {
		return clas;
	}
	public void setClas(String clas) {
		this.clas = clas;
	}
	public String getUrls() {
		return urls;
	}
	public void setUrls(String urls) {
		this.urls = urls;
	}
	public String getParn() {
		return parn;
	}
	public void setParn(String parn) {
		this.parn = parn;
	}
	
}

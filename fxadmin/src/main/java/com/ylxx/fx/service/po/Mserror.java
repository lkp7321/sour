package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Mserror implements Serializable{
	
	
	public String ercd;
	public String ertx;
	public String erxs;
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	public String getErtx() {
		return ertx;
	}
	public void setErtx(String ertx) {
		this.ertx = ertx;
	}
	public String getErxs() {
		return erxs;
	}
	public void setErxs(String erxs) {
		this.erxs = erxs;
	}
	public Mserror(String ercd, String ertx, String erxs) {
		super();
		this.ercd = ercd;
		this.ertx = ertx;
		this.erxs = erxs;
	}
	public Mserror() {
		super();
	}
	
}

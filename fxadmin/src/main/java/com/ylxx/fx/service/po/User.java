package com.ylxx.fx.service.po;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -2682305557890221059L;
	private String usid;
	private String usnm;
	private String pswd;
	private String uspt;
	private String cstn;
	private String ognm;
	private String cmpt;
	private String mbtl;
	private String telp;
	private String mail;
	private String ufax;
	private String rmrk;
	private String macip;
	private String prod;
	private String ltime;
	private String utime;
	private String usfg;
	private int ecount;
	
	public User() {
		super();
	}

	public User(String usid, String usnm, String pswd, String uspt,
			String cstn, String ognm, String cmpt, String mbtl, String telp,
			String mail, String ufax, String rmrk, String macip, String prod,
			String ltime, String utime, String usfg, int ecount) {
		super();
		this.usid = usid;
		this.usnm = usnm;
		this.pswd = pswd;
		this.uspt = uspt;
		this.cstn = cstn;
		this.ognm = ognm;
		this.cmpt = cmpt;
		this.mbtl = mbtl;
		this.telp = telp;
		this.mail = mail;
		this.ufax = ufax;
		this.rmrk = rmrk;
		this.macip = macip;
		this.prod = prod;
		this.ltime = ltime;
		this.utime = utime;
		this.usfg = usfg;
		this.ecount = ecount;
	}

	public String getUsid() {
		return usid;
	}

	public void setUsid(String usid) {
		this.usid = usid;
	}

	public String getUsnm() {
		return usnm;
	}

	public void setUsnm(String usnm) {
		this.usnm = usnm;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public String getUspt() {
		return uspt;
	}

	public void setUspt(String uspt) {
		this.uspt = uspt;
	}

	public String getCstn() {
		return cstn;
	}

	public void setCstn(String cstn) {
		this.cstn = cstn;
	}

	public String getOgnm() {
		return ognm;
	}

	public void setOgnm(String ognm) {
		this.ognm = ognm;
	}

	public String getCmpt() {
		return cmpt;
	}

	public void setCmpt(String cmpt) {
		this.cmpt = cmpt;
	}

	public String getMbtl() {
		return mbtl;
	}

	public void setMbtl(String mbtl) {
		this.mbtl = mbtl;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUfax() {
		return ufax;
	}

	public void setUfax(String ufax) {
		this.ufax = ufax;
	}

	public String getRmrk() {
		return rmrk;
	}

	public void setRmrk(String rmrk) {
		this.rmrk = rmrk;
	}

	public String getMacip() {
		return macip;
	}

	public void setMacip(String macip) {
		this.macip = macip;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getLtime() {
		return ltime;
	}

	public void setLtime(String ltime) {
		this.ltime = ltime;
	}

	public String getUtime() {
		return utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getUsfg() {
		return usfg;
	}

	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}

	public int getEcount() {
		return ecount;
	}

	public void setEcount(int ecount) {
		this.ecount = ecount;
	}

	
}

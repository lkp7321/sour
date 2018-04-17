package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Nrlrgt implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ptid;
	private String mnid;
	private String clas;
	private String sqno;
	private String rltn;
	
	public Nrlrgt() {
		super();
	}
	
	public Nrlrgt(String ptid, String mnid, String clas, String sqno) {
		super();
		this.ptid = ptid;
		this.mnid = mnid;
		this.clas = clas;
		this.sqno = sqno;
	}

	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public String getMnid() {
		return mnid;
	}

	public String getRltn() {
		return rltn;
	}

	public void setRltn(String rltn) {
		this.rltn = rltn;
	}

	public void setMnid(String mnid) {
		this.mnid = mnid;
	}

	public String getClas() {
		return clas;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public String getSqno() {
		return sqno;
	}

	public void setSqno(String sqno) {
		this.sqno = sqno;
	}
	
	
}

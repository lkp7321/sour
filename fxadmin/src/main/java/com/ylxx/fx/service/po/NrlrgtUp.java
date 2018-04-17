package com.ylxx.fx.service.po;

import java.io.Serializable;

public class NrlrgtUp implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mnid;
	private String mamn;
	private String sbmn;
	private String bool;
	private boolean selected = false;
	private String rltn;
	private String ptid;
	private String rofg;
	
	
	public String getRofg() {
		return rofg;
	}
	public void setRofg(String rofg) {
		this.rofg = rofg;
	}
	public NrlrgtUp() {
		super();
	}
	public NrlrgtUp(String mnid, String mamn, String sbmn, String bool,
			boolean selected) {
		super();
		this.mnid = mnid;
		this.mamn = mamn;
		this.sbmn = sbmn;
		this.bool = bool;
		this.selected = selected;
	}
	
	public String getRltn() {
		return rltn;
	}
	public void setRltn(String rltn) {
		this.rltn = rltn;
	}
	public String getMnid() {
		return mnid;
	}
	public void setMnid(String mnid) {
		this.mnid = mnid;
	}
	public String getMamn() {
		return mamn;
	}
	public void setMamn(String mamn) {
		this.mamn = mamn;
	}
	public String getSbmn() {
		return sbmn;
	}
	public void setSbmn(String sbmn) {
		this.sbmn = sbmn;
	}
	public String getBool() {
		return bool;
	}
	public void setBool(String bool) {
		this.bool = bool;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
}

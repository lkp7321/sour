package com.ylxx.fx.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AdditionalMarginVo implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private  String userKey;
	private  String prod;
	private  Integer pageNo;
	private  Integer pageSize;
	private  String trac;
	private  String cyen;
	
	private String stat;
	
	
	public String getStat() {
		return stat;
	}



	public void setStat(String stat) {
		this.stat = stat;
	}



	public ArrayList<AdditionalMarginVo> getAdmlist() {
		return admlist;
	}



	public void setAdmlist(ArrayList<AdditionalMarginVo> admlist) {
		this.admlist = admlist;
	}
	private ArrayList<AdditionalMarginVo> admlist;
	
	public String getTrac() {
		return trac;
	}



	public void setTrac(String trac) {
		this.trac = trac;
	}



	public String getCyen() {
		return cyen;
	}



	public void setCyen(String cyen) {
		this.cyen = cyen;
	}



	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}

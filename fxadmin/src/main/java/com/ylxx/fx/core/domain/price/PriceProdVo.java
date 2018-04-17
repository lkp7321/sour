package com.ylxx.fx.core.domain.price;

import com.ylxx.fx.service.po.PdtJGinfo;
import com.ylxx.fx.service.po.Pdtinfo;

public class PriceProdVo {
	private String userKey;
	private String ptid;
	private String prnm;
	private String usfg;
	private Pdtinfo pdtinfo;
	private PdtJGinfo bean;
	//历史价格查询
	private String prod;
	private String exnm;
	private String begintime; 
	private String endtime;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getBegintime() {
		return begintime;
	}
	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public PdtJGinfo getBean() {
		return bean;
	}
	public void setBean(PdtJGinfo bean) {
		this.bean = bean;
	}
	public Pdtinfo getPdtinfo() {
		return pdtinfo;
	}
	public void setPdtinfo(Pdtinfo pdtinfo) {
		this.pdtinfo = pdtinfo;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getPrnm() {
		return prnm;
	}
	public void setPrnm(String prnm) {
		this.prnm = prnm;
	}
	
}

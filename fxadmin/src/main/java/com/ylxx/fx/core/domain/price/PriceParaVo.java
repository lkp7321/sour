package com.ylxx.fx.core.domain.price;

import com.ylxx.fx.service.po.PdtPointTForAcc;

public class PriceParaVo {
	private String userKey;
	private String prod;
	private String ptid1;
	private PdtPointTForAcc pdtPoint;
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
	public String getPtid1() {
		return ptid1;
	}
	public void setPtid1(String ptid1) {
		this.ptid1 = ptid1;
	}
	public PdtPointTForAcc getPdtPoint() {
		return pdtPoint;
	}
	public void setPdtPoint(PdtPointTForAcc pdtPoint) {
		this.pdtPoint = pdtPoint;
	}
	
}

package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.service.po.BailExceBean;
import com.ylxx.fx.service.po.BailMt4Bean;
import com.ylxx.fx.service.po.Cmmptparac;

public class BailSystemVo {
	private String userKey;
	private Cmmptparac bean;
	private String acno; 
	private String strcuac; 
	private String trdtbegin; 
	private String trdtend; 
	private String trcd;
	private String curDate; 
	private String toDate;
	private String tranCode;
	private BailMt4Bean cmmbean;
	private List<BailExceBean> bailExceList;
	
	public List<BailExceBean> getBailExceList() {
		return bailExceList;
	}
	public void setBailExceList(List<BailExceBean> bailExceList) {
		this.bailExceList = bailExceList;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public BailMt4Bean getCmmbean() {
		return cmmbean;
	}
	public void setCmmbean(BailMt4Bean cmmbean) {
		this.cmmbean = cmmbean;
	}
	public String getCurDate() {
		return curDate;
	}
	public void setCurDate(String curDate) {
		this.curDate = curDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAcno() {
		return acno;
	}
	public void setAcno(String acno) {
		this.acno = acno;
	}
	public String getStrcuac() {
		return strcuac;
	}
	public void setStrcuac(String strcuac) {
		this.strcuac = strcuac;
	}
	public String getTrdtbegin() {
		return trdtbegin;
	}
	public void setTrdtbegin(String trdtbegin) {
		this.trdtbegin = trdtbegin;
	}
	public String getTrdtend() {
		return trdtend;
	}
	public void setTrdtend(String trdtend) {
		this.trdtend = trdtend;
	}
	public String getTrcd() {
		return trcd;
	}
	public void setTrcd(String trcd) {
		this.trcd = trcd;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public Cmmptparac getBean() {
		return bean;
	}
	public void setBean(Cmmptparac bean) {
		this.bean = bean;
	}
	
}

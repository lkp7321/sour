package com.ylxx.fx.core.domain;

import com.ylxx.fx.service.po.CkHandMxDetail;
import com.ylxx.fx.service.po.Ck_ppresultdetail;
import com.ylxx.fx.service.po.Register;

public class MoZhangVo {
	private String userKey;
	private String user;//用户名
	private String prod;//产品号
	private String dateApdt;//登记起始日期
	private String dateEndInput;//登记结束日期
	private String trdt;//交易日期
	private String jgdt;//交割日期
	private String sartDate;//交易起始日期
	private String endDate;//交易结束日期
	private String strExnm;//货币对名称
	private String strStat;//平盘状态
	private String seqn;//流水号
	private String ppno;//平盘流水号
	private String trfl;//自动平盘标志
	private int combox;
	private String bexnm;//买入币种
	private String sexnm;//卖出币种
	private Register register;
	private CkHandMxDetail mxdetail;
	private Ck_ppresultdetail ppresult;
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getProd() {
		return prod;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getDateApdt() {
		return dateApdt;
	}
	public void setDateApdt(String dateApdt) {
		this.dateApdt = dateApdt;
	}
	public String getDateEndInput() {
		return dateEndInput;
	}
	public void setDataEndInput(String dateEndInput) {
		this.dateEndInput = dateEndInput;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getJgdt() {
		return jgdt;
	}
	public void setJgdt(String jgdt) {
		this.jgdt = jgdt;
	}
	public int getCombox() {
		return combox;
	}
	public void setCombox(int combox) {
		this.combox = combox;
	}
	public String getSartDate() {
		return sartDate;
	}
	public void setSartDate(String sartDate) {
		this.sartDate = sartDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStrExnm() {
		return strExnm;
	}
	public void setStrExnm(String strExnm) {
		this.strExnm = strExnm;
	}
	public String getStrStat() {
		return strStat;
	}
	public void setStrStat(String strStat) {
		this.strStat = strStat;
	}
	public String getSeqn() {
		return seqn;
	}
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	public String getPpno() {
		return ppno;
	}
	public void setPpno(String ppno) {
		this.ppno = ppno;
	}
	public String getTrfl() {
		return trfl;
	}
	public void setTrfl(String trfl) {
		this.trfl = trfl;
	}
	public String getBexnm() {
		return bexnm;
	}
	public void setBexnm(String bexnm) {
		this.bexnm = bexnm;
	}
	public String getSexnm() {
		return sexnm;
	}
	public void setSexnm(String sexnm) {
		this.sexnm = sexnm;
	}
	public Register getRegister() {
		return register;
	}
	public void setRegister(Register register) {
		this.register = register;
	}
	public CkHandMxDetail getMxdetail() {
		return mxdetail;
	}
	public void setMxdetail(CkHandMxDetail mxdetail) {
		this.mxdetail = mxdetail;
	}
	public Ck_ppresultdetail getPpresult() {
		return ppresult;
	}
	public void setPpresult(Ck_ppresultdetail ppresult) {
		this.ppresult = ppresult;
	}
}

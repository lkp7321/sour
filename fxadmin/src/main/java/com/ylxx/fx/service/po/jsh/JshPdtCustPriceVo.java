package com.ylxx.fx.service.po.jsh;

public class JshPdtCustPriceVo {
	private String sequ;//序列号
	private String exnm;//货币对
	private String trfg;//可交易性
	private Integer pageNo;//页号
	private Integer pageSize;//页长
	private String userKey; //用户号
	public String getSequ() {
		return sequ;
	}
	public void setSequ(String sequ) {
		this.sequ = sequ;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getTrfg() {
		return trfg;
	}
	public void setTrfg(String trfg) {
		this.trfg = trfg;
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
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
}

package com.ylxx.fx.service.po.jsh;

import java.math.BigDecimal;

public class JshChkParaVo {
	private String exnm;//货币对
	private Integer pageNo;//页号
	private Integer pageSize;//页长
	private String userKey; //用户号
	private BigDecimal mxmd;//中间价上限
	private BigDecimal mdmd;//中间价
	private BigDecimal mimd;//中间价下限
	private BigDecimal point;//点差
	private BigDecimal offset;//偏移量
	private Integer vltime;//有效时间
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
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
	public BigDecimal getMxmd() {
		return mxmd;
	}
	public void setMxmd(BigDecimal mxmd) {
		this.mxmd = mxmd;
	}
	public BigDecimal getMdmd() {
		return mdmd;
	}
	public void setMdmd(BigDecimal mdmd) {
		this.mdmd = mdmd;
	}
	public BigDecimal getMimd() {
		return mimd;
	}
	public void setMimd(BigDecimal mimd) {
		this.mimd = mimd;
	}
	public Integer getVltime() {
		return vltime;
	}
	public void setVltime(Integer vltime) {
		this.vltime = vltime;
	}
	public BigDecimal getPoint() {
		return point;
	}
	public void setPoint(BigDecimal point) {
		this.point = point;
	}
	public BigDecimal getOffset() {
		return offset;
	}
	public void setOffset(BigDecimal offset) {
		this.offset = offset;
	}
}

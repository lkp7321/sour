package com.ylxx.fx.service.po;

import java.math.BigDecimal;

public class Trd_CapitalTransfer {
	private String shno;// 商户id
	private String fldt;// 文件日期
	private String flnm;// 文件名
	private String flph;// 文件路径
	private String pedt;// 计划执行日期
	private int total;// 总笔数
	private BigDecimal jrmb;// 净金额rmb
	private String dirc;// 转账方向
	private BigDecimal jkau;// 净申购kau
	private String mafl;// 业务手工中止标识
	private String madt;// 业务手工操作日期
	private String matm;// 业务手工操作时间
	private String trdt;// 实际执行日期
	private String trtm;// 实际执行时间
	private String stcd;// 状态
	private String ercd;// 错误码
	private String ermg;// 错误描述
	private String updt;// 最后更新日期
	private String uptm;// 最后更新时间
	private String shac;// 对手方账号
	public String getShno() {
		return shno;
	}
	public void setShno(String shno) {
		this.shno = shno;
	}
	public String getFldt() {
		return fldt;
	}
	public void setFldt(String fldt) {
		this.fldt = fldt;
	}
	public String getFlnm() {
		return flnm;
	}
	public void setFlnm(String flnm) {
		this.flnm = flnm;
	}
	public String getFlph() {
		return flph;
	}
	public void setFlph(String flph) {
		this.flph = flph;
	}
	public String getPedt() {
		return pedt;
	}
	public void setPedt(String pedt) {
		this.pedt = pedt;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public BigDecimal getJrmb() {
		return jrmb;
	}
	public void setJrmb(BigDecimal jrmb) {
		this.jrmb = jrmb;
	}
	public String getDirc() {
		return dirc;
	}
	public void setDirc(String dirc) {
		this.dirc = dirc;
	}
	public BigDecimal getJkau() {
		return jkau;
	}
	public void setJkau(BigDecimal jkau) {
		this.jkau = jkau;
	}
	public String getMafl() {
		return mafl;
	}
	public void setMafl(String mafl) {
		this.mafl = mafl;
	}
	public String getMadt() {
		return madt;
	}
	public void setMadt(String madt) {
		this.madt = madt;
	}
	public String getMatm() {
		return matm;
	}
	public void setMatm(String matm) {
		this.matm = matm;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	public String getErmg() {
		return ermg;
	}
	public void setErmg(String ermg) {
		this.ermg = ermg;
	}
	public String getUpdt() {
		return updt;
	}
	public void setUpdt(String updt) {
		this.updt = updt;
	}
	public String getUptm() {
		return uptm;
	}
	public void setUptm(String uptm) {
		this.uptm = uptm;
	}
	public String getShac() {
		return shac;
	}
	public void setShac(String shac) {
		this.shac = shac;
	}
	public String getLcno1() {
		return lcno1;
	}
	public void setLcno1(String lcno1) {
		this.lcno1 = lcno1;
	}
	public String getLcno2() {
		return lcno2;
	}
	public void setLcno2(String lcno2) {
		this.lcno2 = lcno2;
	}
	private String lcno1;// 关联流水号1
	private String lcno2;// 关联流水号2
}

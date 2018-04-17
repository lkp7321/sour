package com.ylxx.fx.service.po;

import java.io.Serializable;

public class TbCk_ppcontrol implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prcd; // 产品代码
	private String ckno; // 敞口编号
	private String exnm; // 币别对简称
	private String ppfs; // 平盘方式
	private String ckty; // 平盘敞口类型
	private String dxfs; // 达限方式
	private double dxje; // 达限金额
	private double ppye; // 平盘余额
	private double zybl; // 止盈点数
	private double zsbl; // 止损点数
	private double zyam; // 止盈金额
	private double zsam; // 止损金额
	private Integer rrdt; // 容忍点数
	private Integer ycsj; // 平盘延迟时间_单位秒
	private Integer cont; // 失败可重复平盘次数
	private String ckfg; // 敞口状态
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getPpfs() {
		return ppfs;
	}
	public void setPpfs(String ppfs) {
		this.ppfs = ppfs;
	}
	public String getCkty() {
		return ckty;
	}
	public void setCkty(String ckty) {
		this.ckty = ckty;
	}
	public String getDxfs() {
		return dxfs;
	}
	public void setDxfs(String dxfs) {
		this.dxfs = dxfs;
	}
	public double getDxje() {
		return dxje;
	}
	public void setDxje(double dxje) {
		this.dxje = dxje;
	}
	public double getPpye() {
		return ppye;
	}
	public void setPpye(double ppye) {
		this.ppye = ppye;
	}
	public double getZybl() {
		return zybl;
	}
	public void setZybl(double zybl) {
		this.zybl = zybl;
	}
	public double getZsbl() {
		return zsbl;
	}
	public void setZsbl(double zsbl) {
		this.zsbl = zsbl;
	}
	public double getZyam() {
		return zyam;
	}
	public void setZyam(double zyam) {
		this.zyam = zyam;
	}
	public double getZsam() {
		return zsam;
	}
	public void setZsam(double zsam) {
		this.zsam = zsam;
	}
	public Integer getRrdt() {
		return rrdt;
	}
	public void setRrdt(Integer rrdt) {
		this.rrdt = rrdt;
	}
	public Integer getYcsj() {
		return ycsj;
	}
	public void setYcsj(Integer ycsj) {
		this.ycsj = ycsj;
	}
	public Integer getCont() {
		return cont;
	}
	public void setCont(Integer cont) {
		this.cont = cont;
	}
	public String getCkfg() {
		return ckfg;
	}
	public void setCkfg(String ckfg) {
		this.ckfg = ckfg;
	}
	public TbCk_ppcontrol(String prcd, String ckno, String exnm, String ppfs,
			String ckty, String dxfs, double dxje, double ppye, double zybl,
			double zsbl, double zyam, double zsam, Integer rrdt, Integer ycsj,
			Integer cont, String ckfg) {
		super();
		this.prcd = prcd;
		this.ckno = ckno;
		this.exnm = exnm;
		this.ppfs = ppfs;
		this.ckty = ckty;
		this.dxfs = dxfs;
		this.dxje = dxje;
		this.ppye = ppye;
		this.zybl = zybl;
		this.zsbl = zsbl;
		this.zyam = zyam;
		this.zsam = zsam;
		this.rrdt = rrdt;
		this.ycsj = ycsj;
		this.cont = cont;
		this.ckfg = ckfg;
	}
	public TbCk_ppcontrol() {
		super();
	}
	
}

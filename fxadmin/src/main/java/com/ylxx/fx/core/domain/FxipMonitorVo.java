package com.ylxx.fx.core.domain;

import java.math.BigDecimal;
import java.util.List;

import com.ylxx.fx.utils.Table;

/**
 * 分行价格监控
 */
public class FxipMonitorVo {
	private String color;
	private String udfg;
	private String exnm;
	private BigDecimal nemd;
	private BigDecimal neby;
	private BigDecimal nesl;
	private String mdtm;
	private String stfg;
	private String trfg;
	private String cxfg;
	private String ercd;
	private String ercn;
	private String mknm;
	//
	private String btime;
	private String etime;
	private String userKey;
	private String ckno;
	private String tableName;
	private List<Table> tableList;
	
	public BigDecimal getNemd() {
		return nemd;
	}
	public void setNemd(BigDecimal nemd) {
		this.nemd = nemd;
	}
	public BigDecimal getNeby() {
		return neby;
	}
	public void setNeby(BigDecimal neby) {
		this.neby = neby;
	}
	public BigDecimal getNesl() {
		return nesl;
	}
	public void setNesl(BigDecimal nesl) {
		this.nesl = nesl;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<Table> getTableList() {
		return tableList;
	}
	public void setTableList(List<Table> tableList) {
		this.tableList = tableList;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getErcn() {
		return ercn;
	}
	public void setErcn(String ercn) {
		this.ercn = ercn;
	}
	public String getBtime() {
		return btime;
	}
	public void setBtime(String btime) {
		this.btime = btime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getUdfg() {
		return udfg;
	}
	public void setUdfg(String udfg) {
		this.udfg = udfg;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getMdtm() {
		return mdtm;
	}
	public void setMdtm(String mdtm) {
		this.mdtm = mdtm;
	}
	public String getStfg() {
		return stfg;
	}
	public void setStfg(String stfg) {
		this.stfg = stfg;
	}
	public String getTrfg() {
		return trfg;
	}
	public void setTrfg(String trfg) {
		this.trfg = trfg;
	}
	public String getCxfg() {
		return cxfg;
	}
	public void setCxfg(String cxfg) {
		this.cxfg = cxfg;
	}
	public String getErcd() {
		return ercd;
	}
	public void setErcd(String ercd) {
		this.ercd = ercd;
	}
	public String getMknm() {
		return mknm;
	}
	public void setMknm(String mknm) {
		this.mknm = mknm;
	}
	public FxipMonitorVo(String udfg, String exnm, BigDecimal nemd, BigDecimal neby,
			BigDecimal nesl, String mdtm, String stfg, String trfg, String cxfg,
			String ercd, String mknm) {
		super();
		this.udfg = udfg;
		this.exnm = exnm;
		this.nemd = nemd;
		this.neby = neby;
		this.nesl = nesl;
		this.mdtm = mdtm;
		this.stfg = stfg;
		this.trfg = trfg;
		this.cxfg = cxfg;
		this.ercd = ercd;
		this.mknm = mknm;
	}
	public FxipMonitorVo() {
		super();
	}
	
}

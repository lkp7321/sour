package com.ylxx.fx.core.domain;

import java.io.Serializable;
import java.util.List;

import com.ylxx.fx.service.po.ElecTellerManager;
import com.ylxx.fx.utils.Table;

public class ElecTellerManagerSelectVo implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String trtltxt;
	private String comdata1;
	private String bhidp;
	private  Integer pageNo;
	private  Integer pageSize;
	private  String userKey;
	private  String tellerid;
	private ElecTellerManager eTM;
	
	private String trtl;
	private String bhid;
	private String tableName;
	private List<Table> tableList;
	
	private String orgn;
	private String usnm;
	
	
	
	
	
	
	
	
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
	public String getOrgn() {
		return orgn;
	}
	public void setOrgn(String orgn) {
		this.orgn = orgn;
	}
	public String getUsnm() {
		return usnm;
	}
	public void setUsnm(String usnm) {
		this.usnm = usnm;
	}
	public String getTrtl() {
		return trtl;
	}
	public void setTrtl(String trtl) {
		this.trtl = trtl;
	}
	public String getBhid() {
		return bhid;
	}
	public void setBhid(String bhid) {
		this.bhid = bhid;
	}
	public ElecTellerManager geteTM() {
		return eTM;
	}
	public void seteTM(ElecTellerManager eTM) {
		this.eTM = eTM;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getTellerid() {
		return tellerid;
	}
	public void setTellerid(String tellerid) {
		this.tellerid = tellerid;
	}
	public String getTrtltxt() {
		return trtltxt;
	}
	public void setTrtltxt(String trtltxt) {
		this.trtltxt = trtltxt;
	}
	public String getComdata1() {
		return comdata1;
	}
	public void setComdata1(String comdata1) {
		this.comdata1 = comdata1;
	}
	public String getBhidp() {
		return bhidp;
	}
	public void setBhidp(String bhidp) {
		this.bhidp = bhidp;
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

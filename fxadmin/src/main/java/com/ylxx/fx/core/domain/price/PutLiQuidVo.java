package com.ylxx.fx.core.domain.price;
import java.util.*;
import com.ylxx.fx.service.po.Pdtinfo;
import com.ylxx.fx.service.po.Put_Config;
import com.ylxx.fx.service.po.Put_Liquid;

public class PutLiQuidVo {
	private String stat;
	private String ptid;
	private String inid;
	private String sqid;
	private String name;
	private String userKey;
	private Integer pageNo;
	private Integer pageSize;
	private Put_Liquid put_Liquid;
	private Put_Config put_Config;
	private Pdtinfo pdtin;
	private List<Put_Config> put_ConfigList;
	private List<Pdtinfo> pdtinList;
	
	
	public String getSqid() {
		return sqid;
	}
	public void setSqid(String sqid) {
		this.sqid = sqid;
	}
	public List<Put_Config> getPut_ConfigList() {
		return put_ConfigList;
	}
	public void setPut_ConfigList(List<Put_Config> put_ConfigList) {
		this.put_ConfigList = put_ConfigList;
	}
	public List<Pdtinfo> getPdtinList() {
		return pdtinList;
	}
	public void setPdtinList(List<Pdtinfo> pdtinList) {
		this.pdtinList = pdtinList;
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
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getPtid() {
		return ptid;
	}
	public void setPtid(String ptid) {
		this.ptid = ptid;
	}
	public String getInid() {
		return inid;
	}
	public void setInid(String inid) {
		this.inid = inid;
	}
	public Put_Liquid getPut_Liquid() {
		return put_Liquid;
	}
	public void setPut_Liquid(Put_Liquid put_Liquid) {
		this.put_Liquid = put_Liquid;
	}
	public Put_Config getPut_Config() {
		return put_Config;
	}
	public void setPut_Config(Put_Config put_Config) {
		this.put_Config = put_Config;
	}
	public Pdtinfo getPdtin() {
		return pdtin;
	}
	public void setPdtin(Pdtinfo pdtin) {
		this.pdtin = pdtin;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	
	
	
	
}

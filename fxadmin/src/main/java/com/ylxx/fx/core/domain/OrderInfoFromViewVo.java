package com.ylxx.fx.core.domain;

import java.util.List;

import com.ylxx.fx.utils.Table;

public class OrderInfoFromViewVo {
  private String strateDate;
  private String endDate;
  private String userKey;
  private String prod;
  private Integer pageNo;
  private Integer pageSize;
  private String tableName;
  private List<Table> tableList;
	  
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
	public String getStrateDate() {
		return strateDate;
	}
	public void setStrateDate(String strateDate) {
		this.strateDate = strateDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
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

}

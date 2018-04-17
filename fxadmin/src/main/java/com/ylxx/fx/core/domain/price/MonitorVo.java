package com.ylxx.fx.core.domain.price;

import java.util.List;

import com.ylxx.fx.service.po.QutCmmPrice;
import com.ylxx.fx.utils.Table;

public class MonitorVo {
	private String prcd;
	private String prcd1;
	private String apdt;
	private String jsdt;
	private String tableName;
	private List<Table> tableList;
	private String userKey;
	private QutCmmPrice price;
	
	
	public QutCmmPrice getPrice() {
		return price;
	}
	public void setPrice(QutCmmPrice price) {
		this.price = price;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
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
	public String getApdt() {
		return apdt;
	}
	public void setApdt(String apdt) {
		this.apdt = apdt;
	}
	public String getJsdt() {
		return jsdt;
	}
	public void setJsdt(String jsdt) {
		this.jsdt = jsdt;
	}
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getPrcd1() {
		return prcd1;
	}
	public void setPrcd1(String prcd1) {
		this.prcd1 = prcd1;
	}
	
}

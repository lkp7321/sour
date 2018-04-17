package com.ylxx.fx.core.domain;

import java.io.Serializable;
import java.util.List;

import com.ylxx.fx.service.po.BailBalanceBean;
import com.ylxx.fx.service.po.BailTouCunBean;
import com.ylxx.fx.service.po.BailTranlist;

public class KondorVo implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String userKey;
	private String user;//用户名
	private String prod;//产品
	private String startDate;//交易起始日期
	private String endDate;//交易结束日期
	private String tradeCode;//产品类型编号
	private String rpcNo;//交易请求流水号
	private Integer pageNo;//页数
	private Integer pageSize;//每一页的记录数
	private String clstate;//处理状态
	private String downloadkey;//请求顺序号
	private String retrytimes;//重试次数
	private String productcode;//产品编号
	private String status;//状态
	private BailBalanceBean bailBalanceBean;
	private List<BailTranlist> list;//批量出金核对
	private BailTouCunBean bailTouCunBean;
	private List<BailTouCunBean> bailTouCunBeans;
	public String getStartDate() {
		return startDate;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getRpcNo() {
		return rpcNo;
	}
	public void setRpcNo(String rpcNo) {
		this.rpcNo = rpcNo;
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
	public String getClstate() {
		return clstate;
	}
	public void setClstate(String clstate) {
		this.clstate = clstate;
	}
	public String getDownloadkey() {
		return downloadkey;
	}
	public void setDownloadkey(String downloadkey) {
		this.downloadkey = downloadkey;
	}
	public String getRetrytimes() {
		return retrytimes;
	}
	public void setRetrytimes(String retrytimes) {
		this.retrytimes = retrytimes;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<BailTranlist> getList() {
		return list;
	}
	public void setList(List<BailTranlist> list) {
		this.list = list;
	}
	public BailBalanceBean getBailBalanceBean() {
		return bailBalanceBean;
	}
	public void setBailBalanceBean(BailBalanceBean bailBalanceBean) {
		this.bailBalanceBean = bailBalanceBean;
	}
	public BailTouCunBean getBailTouCunBean() {
		return bailTouCunBean;
	}
	public void setBailTouCunBean(BailTouCunBean bailTouCunBean) {
		this.bailTouCunBean = bailTouCunBean;
	}
	public List<BailTouCunBean> getBailTouCunBeans() {
		return bailTouCunBeans;
	}
	public void setBailTouCunBeans(List<BailTouCunBean> bailTouCunBeans) {
		this.bailTouCunBeans = bailTouCunBeans;
	}
}

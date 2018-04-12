package com.ylxx.qt.service.po;

import java.io.Serializable;

public class RowDataBean implements Serializable {
	String date = null; // 日期
	Double preBalance = 0.0; // 上日结存
	Double accountRight = 0.0; // 客户权益
	Double deposit  = 0.0; // 当日入金
	Double Withdraw = 0.0; // 当日出金
	Double mortgage = 0.0; // 质押金
	Double closeProfit = 0.0; // 平仓盈亏
	Double currMargin  = 0.0; // 保证金占用
	Double commission = 0.0; // 手续费
	Double available = 0.0; // 可用资金
	Double pwmc = 0.0; // 本日结存
	Double cdp = 0.0; // 风险率
	Double pp = 0.0; // 浮动盈亏
	Double adc = 0.0; // 追加保证金
	Double dcp = 0.0; // 净值
	Double captical=0.0 ;//本金
	Integer countNum = 0;//页面条数
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Double getPreBalance() {
		return preBalance;
	}
	public void setPreBalance(Double preBalance) {
		this.preBalance = preBalance;
	}
	public Double getAccountRight() {
		return accountRight;
	}
	public void setAccountRight(Double accountRight) {
		this.accountRight = accountRight;
	}
	public Double getDeposit() {
		return deposit;
	}
	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}
	public Double getWithdraw() {
		return Withdraw;
	}
	public void setWithdraw(Double withdraw) {
		Withdraw = withdraw;
	}
	public Double getMortgage() {
		return mortgage;
	}
	public void setMortgage(Double mortgage) {
		this.mortgage = mortgage;
	}
	public Double getCloseProfit() {
		return closeProfit;
	}
	public void setCloseProfit(Double closeProfit) {
		this.closeProfit = closeProfit;
	}
	public Double getCurrMargin() {
		return currMargin;
	}
	public void setCurrMargin(Double currMargin) {
		this.currMargin = currMargin;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public Double getAvailable() {
		return available;
	}
	public void setAvailable(Double available) {
		this.available = available;
	}
	public Double getPwmc() {
		return pwmc;
	}
	public void setPwmc(Double pwmc) {
		this.pwmc = pwmc;
	}
	public Double getCdp() {
		return cdp;
	}
	public void setCdp(Double cdp) {
		this.cdp = cdp;
	}
	public Double getPp() {
		return pp;
	}
	public void setPp(Double pp) {
		this.pp = pp;
	}
	public Double getAdc() {
		return adc;
	}
	public void setAdc(Double adc) {
		this.adc = adc;
	}
	public Double getDcp() {
		return dcp;
	}
	public void setDcp(Double dcp) {
		this.dcp = dcp;
	}
	public Double getCaptical() {
		return captical;
	}
	public void setCaptical(Double captical) {
		this.captical = captical;
	}
	public Integer getCountNum() {
		return countNum;
	}
	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}
	
}

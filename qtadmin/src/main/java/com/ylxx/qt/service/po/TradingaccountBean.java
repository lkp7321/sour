package com.ylxx.qt.service.po;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class TradingaccountBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private double preBalanceByTrade;//逐笔上日结存
	private double preBalance;//逐日上日结存
	private double positionProfitByTrade;//逐笔持仓盈亏
	private double positionProfit;//逐日持仓盈亏
	private double closeProfitByTrade;//逐笔平仓盈亏
	private double closeProfit;//逐日平仓盈亏
	private double commission;//手续费
	private double currMargin;//当前保证金
	private double frozenCash;//冻结资金
	private double available;//可用资金
	private double withdrawQuota;//可取资金
	private double fund;//动态权益
	private double risk;//风险度
	private String tradeDay;//交易日
	private String cTPID;//CTP账号
	private double deposit;//入金
	private double withdraw;//出金
	public TradingaccountBean(double preBalanceByTrade, double preBalance,
			double positionProfitByTrade, double positionProfit,
			double closeProfitByTrade, double closeProfit, double commission,
			double currMargin, double frozenCash, double available,
			double withdrawQuota, double fund, double risk, String tradeDay,
			String cTPID, double deposit, double withdraw) {
		super();
		this.preBalanceByTrade = preBalanceByTrade;
		this.preBalance = preBalance;
		this.positionProfitByTrade = positionProfitByTrade;
		this.positionProfit = positionProfit;
		this.closeProfitByTrade = closeProfitByTrade;
		this.closeProfit = closeProfit;
		this.commission = commission;
		this.currMargin = currMargin;
		this.frozenCash = frozenCash;
		this.available = available;
		this.withdrawQuota = withdrawQuota;
		this.fund = fund;
		this.risk = risk;
		this.tradeDay = tradeDay;
		this.cTPID = cTPID;
		this.deposit = deposit;
		this.withdraw = withdraw;
	}
	public TradingaccountBean() {
		super();
	}
	public double getPreBalanceByTrade() {
		return preBalanceByTrade;
	}
	public void setPreBalanceByTrade(double preBalanceByTrade) {
		this.preBalanceByTrade = preBalanceByTrade;
	}
	public double getPreBalance() {
		return preBalance;
	}
	public void setPreBalance(double preBalance) {
		this.preBalance = preBalance;
	}
	public double getPositionProfitByTrade() {
		return positionProfitByTrade;
	}
	public void setPositionProfitByTrade(double positionProfitByTrade) {
		this.positionProfitByTrade = positionProfitByTrade;
	}
	public double getPositionProfit() {
		return positionProfit;
	}
	public void setPositionProfit(double positionProfit) {
		this.positionProfit = positionProfit;
	}
	public double getCloseProfitByTrade() {
		return closeProfitByTrade;
	}
	public void setCloseProfitByTrade(double closeProfitByTrade) {
		this.closeProfitByTrade = closeProfitByTrade;
	}
	public double getCloseProfit() {
		return closeProfit;
	}
	public void setCloseProfit(double closeProfit) {
		this.closeProfit = closeProfit;
	}
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public double getCurrMargin() {
		return currMargin;
	}
	public void setCurrMargin(double currMargin) {
		this.currMargin = currMargin;
	}
	public double getFrozenCash() {
		return frozenCash;
	}
	public void setFrozenCash(double frozenCash) {
		this.frozenCash = frozenCash;
	}
	public double getAvailable() {
		return available;
	}
	public void setAvailable(double available) {
		this.available = available;
	}
	public double getWithdrawQuota() {
		return withdrawQuota;
	}
	public void setWithdrawQuota(double withdrawQuota) {
		this.withdrawQuota = withdrawQuota;
	}
	public double getFund() {
		return fund;
	}
	public void setFund(double fund) {
		this.fund = fund;
	}
	public double getRisk() {
		return risk;
	}
	public void setRisk(double risk) {
		this.risk = risk;
	}
	public String getTradeDay() {
		return tradeDay;
	}
	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
	}
	public String getcTPID() {
		return cTPID;
	}
	public void setcTPID(String cTPID) {
		this.cTPID = cTPID;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public double getWithdraw() {
		return withdraw;
	}
	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}
	@Override
	public String toString() {
		return "TradingaccountBean [preBalanceByTrade=" + preBalanceByTrade
				+ ", preBalance=" + preBalance + ", positionProfitByTrade="
				+ positionProfitByTrade + ", positionProfit=" + positionProfit
				+ ", closeProfitByTrade=" + closeProfitByTrade
				+ ", closeProfit=" + closeProfit + ", commission=" + commission
				+ ", currMargin=" + currMargin + ", frozenCash=" + frozenCash
				+ ", available=" + available + ", withdrawQuota="
				+ withdrawQuota + ", fund=" + fund + ", risk=" + risk
				+ ", tradeDay=" + tradeDay + ", cTPID=" + cTPID + ", deposit="
				+ deposit + ", withdraw=" + withdraw + "]";
	}
	
}

package com.ylxx.qt.service.po;

import java.io.Serializable;

public class TradingAccountFiledBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ctp_id;                              // CTPID
	private String account_id;                        	// 投资者帐号
	private double pre_Balance;                       	// 上次结算准备金
	private double deposit;                           	// 入金金额
	private double withdraw;                         	// 出金金额
	private double curr_margin;                      	// 当前保证金总额
	private double commission;                        	// 手续费
	private double close_profit;                      	// 平仓盈亏
	private double position_profit;                   	// 持仓盈亏
	private double available;                         	// 可用资金
	private String trading_day;                         // 交易日
	private double mortgate;                          	// 质押金额
	private double pdw;
	private double capital;   							// 账户初始金额
	private double cumulative;  						// 累计净值
	private double totalamount;
	private double cprofit;
	private double space;
	private double fund;                                // 动态权益
	private String username;						  	// 交易者
	
	public double getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(double totalamount) {
		this.totalamount = totalamount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
    public double getFund() {
		return fund;
	}


	public void setFund(double fund) {
		this.fund = fund;
	}


	public TradingAccountFiledBean() {
		
	}
    
	
	public String getCtp_id() {
		return ctp_id;
	}


	public void setCtp_id(String ctp_id) {
		this.ctp_id = ctp_id;
	}


	public String getAccount_id() {
		return account_id;
	}


	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}


	public double getPre_Balance() {
		return pre_Balance;
	}


	public void setPre_Balance(double pre_Balance) {
		this.pre_Balance = pre_Balance;
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


	public double getCurr_margin() {
		return curr_margin;
	}


	public void setCurr_margin(double curr_margin) {
		this.curr_margin = curr_margin;
	}


	public double getCommission() {
		return commission;
	}


	public void setCommission(double commission) {
		this.commission = commission;
	}


	public double getClose_profit() {
		return close_profit;
	}


	public void setClose_profit(double close_profit) {
		this.close_profit = close_profit;
	}


	public double getPosition_profit() {
		return position_profit;
	}


	public void setPosition_profit(double position_profit) {
		this.position_profit = position_profit;
	}


	public double getAvailable() {
		return available;
	}


	public void setAvailable(double available) {
		this.available = available;
	}


	public String getTrading_day() {
		return trading_day;
	}


	public void setTrading_day(String trading_day) {
		this.trading_day = trading_day;
	}


	public double getMortgate() {
		return mortgate;
	}


	public void setMortgate(double mortgate) {
		this.mortgate = mortgate;
	}


	public double getPdw() {
		return pdw;
	}


	public void setPdw(double pdw) {
		this.pdw = pdw;
	}


	public double getCapital() {
		return capital;
	}


	public void setCapital(double capital) {
		this.capital = capital;
	}


	public double getCumulative() {
		return cumulative;
	}


	public void setCumulative(double cumulative) {
		this.cumulative = cumulative;
	}


	public double getCprofit() {
		return cprofit;
	}


	public void setCprofit(double cprofit) {
		this.cprofit = cprofit;
	}

	public double getSpace() {
		return space;
	}

	public void setSpace(double space) {
		this.space = space;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String toString() {
		return "TradingAccountFiledBean [ctp_id=" + ctp_id + ", account_id="
				+ account_id + ", pre_Balance=" + pre_Balance + ", deposit="
				+ deposit + ", withdraw=" + withdraw + ", curr_margin="
				+ curr_margin + ", commission=" + commission
				+ ", close_profit=" + close_profit + ", position_profit="
				+ position_profit + ", available=" + available
				+ ", trading_day=" + trading_day + ", mortgate=" + mortgate
				+ ", pdw=" + pdw + ", capital=" + capital + ", cumulative="
				+ cumulative + ", cprofit=" + cprofit + ", space=" + space
				+ "]";
	}
	
}

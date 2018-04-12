package com.ylxx.qt.service.po;

import java.io.Serializable;

public class TradeFieldBean implements Serializable {          
	
	private static final long serialVersionUID = 1L;
	
	private String ctp_id;             // CTPID 
	private String investor_id;        // 投资者代码
	private String instrument_id;      // 合约代码
	private String direction;          // 买卖方向
	private String offSet_flag;        // 开平标志
	private double price;              // 价格
	private int volumn;                // 数量
	private String trade_date;         // 成交时期
	private double profit;             // 成交盈亏	
	private String td;
	private double sum;	
	private String product_id;
	private double sumc;	
	private String week;            //每周
	private double week_account;    //每周盈亏
	
	private String trade_id;         //交易编号
	private String exchange_id;     //交易所编码
	private String hedge;           //投保
	private String tradeTime;       //交易时间
	private String tradingDay;      //交易日期
	private String order_id;        //委托订单编号
	private String sys_id;          //系统编号
	private String tradeDateTime;   //交易详细时间
	private String updateTime;      //数据更新时间
	private String openTride_id;    //开仓成交号
	private double closeProfit;		//盈亏
	private double closeProfitBydate; //逐日盯市平仓盈亏
	
	public double getCloseProfitBydate() {
		return closeProfitBydate;
	}

	public void setCloseProfitBydate(double closeProfitBydate) {
		this.closeProfitBydate = closeProfitBydate;
	}

	public double getCloseProfit() {
		return closeProfit;
	}

	public void setCloseProfit(double closeProfit) {
		this.closeProfit = closeProfit;
	}

	public String getTrade_id() {
		return trade_id;
	}

	public void setTrade_id(String trade_id) {
		this.trade_id = trade_id;
	}

	public String getExchange_id() {
		return exchange_id;
	}

	public void setExchange_id(String exchange_id) {
		this.exchange_id = exchange_id;
	}

	public String getHedge() {
		return hedge;
	}

	public void setHedge(String hedge) {
		this.hedge = hedge;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSys_id() {
		return sys_id;
	}

	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getOpenTride_id() {
		return openTride_id;
	}

	public void setOpenTride_id(String openTride_id) {
		this.openTride_id = openTride_id;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}

	public String getTradingDay() {
		return tradingDay;
	}

	public void setTradingDay(String tradingDay) {
		this.tradingDay = tradingDay;
	}

	public String getTradeDateTime() {
		return tradeDateTime;
	}

	public void setTradeDateTime(String tradeDateTime) {
		this.tradeDateTime = tradeDateTime;
	}

	public TradeFieldBean(){
		
	}
	
	public String getCtp_id() {
		return ctp_id;
	}

	public void setCtp_id(String ctp_id) {
		this.ctp_id = ctp_id;
	}

	public String getInvestor_id() {
		return investor_id;
	}

	public void setInvestor_id(String investor_id) {
		this.investor_id = investor_id;
	}

	public String getInstrument_id() {
		return instrument_id;
	}

	public void setInstrument_id(String instrument_id) {
		this.instrument_id = instrument_id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getOffSet_flag() {
		return offSet_flag;
	}

	public void setOffSet_flag(String offSet_flag) {
		this.offSet_flag = offSet_flag;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolumn() {
		return volumn;
	}

	public void setVolumn(int volumn) {
		this.volumn = volumn;
	}

	public String getTrade_date() {
		return trade_date;
	}

	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getTd() {
		return td;
	}

	public void setTd(String td) {
		this.td = td;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public double getSumc() {
		return sumc;
	}

	public void setSumc(double sumc) {
		this.sumc = sumc;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public double getWeek_account() {
		return week_account;
	}

	public void setWeek_account(double week_account) {
		this.week_account = week_account;
	}

	@Override
	public String toString() {
		return "TradeFieldBean [ctp_id=" + ctp_id + ", investor_id="
				+ investor_id + ", instrument_id=" + instrument_id
				+ ", direction=" + direction + ", offSet_flag=" + offSet_flag
				+ ", price=" + price + ", volumn=" + volumn + ", trade_date="
				+ trade_date + ", profit=" + profit + ", td=" + td + ", sum="
				+ sum + ", product_id=" + product_id + ", sumc=" + sumc
				+ ", week=" + week + ", week_account=" + week_account + "]";
	}

}

package com.ylxx.qt.service.po;

import java.io.Serializable;

public class TradeBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tradeID;//交易编号
	private String cTPID;//CTPID
	private String instrumentID;//合约编号
	private String exchangeID;//交易所编码
	private String direction;//买卖方向
	private double price;//价格
	private String offset;//开平标识
	private String hedge;//投保
	private String tradeTime;//交易时间
	private String tradingDay;//交易日期
	private String orderID;//委托订单编号
	private String sysID;//系统编号
	private int volume;//手数
	private String openTradeID;//开仓成交号
	private double closeProfit;//逐笔平仓盈亏
	private double closeProfitByDate;//逐日盯市平仓盈亏
	public TradeBean(String tradeID, String cTPID, String instrumentID,
			String exchangeID, String direction, double price, String offset,
			String hedge, String tradeTime, String tradingDay, String orderID,
			String sysID, int volume, String openTradeID, double closeProfit,
			double closeProfitByDate) {
		super();
		this.tradeID = tradeID;
		this.cTPID = cTPID;
		this.instrumentID = instrumentID;
		this.exchangeID = exchangeID;
		this.direction = direction;
		this.price = price;
		this.offset = offset;
		this.hedge = hedge;
		this.tradeTime = tradeTime;
		this.tradingDay = tradingDay;
		this.orderID = orderID;
		this.sysID = sysID;
		this.volume = volume;
		this.openTradeID = openTradeID;
		this.closeProfit = closeProfit;
		this.closeProfitByDate = closeProfitByDate;
	}
	public TradeBean() {
		super();
	}
	public String getTradeID() {
		return tradeID;
	}
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
	}
	public String getcTPID() {
		return cTPID;
	}
	public void setcTPID(String cTPID) {
		this.cTPID = cTPID;
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
	}
	public String getExchangeID() {
		return exchangeID;
	}
	public void setExchangeID(String exchangeID) {
		this.exchangeID = exchangeID;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public String getHedge() {
		return hedge;
	}
	public void setHedge(String hedge) {
		this.hedge = hedge;
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
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getSysID() {
		return sysID;
	}
	public void setSysID(String sysID) {
		this.sysID = sysID;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	public String getOpenTradeID() {
		return openTradeID;
	}
	public void setOpenTradeID(String openTradeID) {
		this.openTradeID = openTradeID;
	}
	public double getCloseProfit() {
		return closeProfit;
	}
	public void setCloseProfit(double closeProfit) {
		this.closeProfit = closeProfit;
	}
	public double getCloseProfitByDate() {
		return closeProfitByDate;
	}
	public void setCloseProfitByDate(double closeProfitByDate) {
		this.closeProfitByDate = closeProfitByDate;
	}
	@Override
	public String toString() {
		return "TradeBean [tradeID=" + tradeID + ", cTPID=" + cTPID
				+ ", instrumentID=" + instrumentID + ", exchangeID="
				+ exchangeID + ", direction=" + direction + ", price=" + price
				+ ", offset=" + offset + ", hedge=" + hedge + ", tradeTime="
				+ tradeTime + ", tradingDay=" + tradingDay + ", orderID="
				+ orderID + ", sysID=" + sysID + ", volume=" + volume
				+ ", openTradeID=" + openTradeID + ", closeProfit="
				+ closeProfit + ", closeProfitByDate=" + closeProfitByDate
				+ "]";
	}
	
}

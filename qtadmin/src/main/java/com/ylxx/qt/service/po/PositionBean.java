package com.ylxx.qt.service.po;

import java.io.Serializable;

public class PositionBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String instrumentID;//合约Id
	private String direction;//买卖
	private double price;//成交价格
	private int position;//持仓量
	private int ydPosition;//昨仓
	private int tdPosition;//今仓
	private int noFrozen;//可平量
	private double positionCost;//持仓均价
	private double openCost;//开仓均价
	private double avgCost;//全部均价
	private int volumeMultiple;//合约乘数
	private String hedge;//投保
	private int closeVolumn;//平仓量
	private double closeProfit;//平仓盈亏
	private double openProfit;//开仓盈亏
	private double positionProfitByTrade;//逐笔持仓盈亏
	private double positionProfit;//逐日持仓盈亏
	private double commission;//手续费
	private double margin;//保证金
	private String cTPID;//交易账号
	private String tradeDay;//交易日期
	private double closeProfitByTrade;//逐笔平仓盈亏
	private double lastSettlementPrice;//昨日结算价
	private double closeProfitByDate;//逐日盯市平仓盈亏
	private double settlementPrice;//本日结算价
	public PositionBean(String instrumentID, String direction, double price,
			int position, int ydPosition, int tdPosition, int noFrozen,
			double positionCost, double openCost, double avgCost,
			int volumeMultiple, String hedge, int closeVolumn,
			double closeProfit, double openProfit,
			double positionProfitByTrade, double positionProfit,
			double commission, double margin, String cTPID, String tradeDay,
			double closeProfitByTrade, double lastSettlementPrice,
			double closeProfitByDate, double settlementPrice) {
		super();
		this.instrumentID = instrumentID;
		this.direction = direction;
		this.price = price;
		this.position = position;
		this.ydPosition = ydPosition;
		this.tdPosition = tdPosition;
		this.noFrozen = noFrozen;
		this.positionCost = positionCost;
		this.openCost = openCost;
		this.avgCost = avgCost;
		this.volumeMultiple = volumeMultiple;
		this.hedge = hedge;
		this.closeVolumn = closeVolumn;
		this.closeProfit = closeProfit;
		this.openProfit = openProfit;
		this.positionProfitByTrade = positionProfitByTrade;
		this.positionProfit = positionProfit;
		this.commission = commission;
		this.margin = margin;
		this.cTPID = cTPID;
		this.tradeDay = tradeDay;
		this.closeProfitByTrade = closeProfitByTrade;
		this.lastSettlementPrice = lastSettlementPrice;
		this.closeProfitByDate = closeProfitByDate;
		this.settlementPrice = settlementPrice;
	}
	public PositionBean() {
		super();
	}
	public String getInstrumentID() {
		return instrumentID;
	}
	public void setInstrumentID(String instrumentID) {
		this.instrumentID = instrumentID;
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
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getYdPosition() {
		return ydPosition;
	}
	public void setYdPosition(int ydPosition) {
		this.ydPosition = ydPosition;
	}
	public int getTdPosition() {
		return tdPosition;
	}
	public void setTdPosition(int tdPosition) {
		this.tdPosition = tdPosition;
	}
	public int getNoFrozen() {
		return noFrozen;
	}
	public void setNoFrozen(int noFrozen) {
		this.noFrozen = noFrozen;
	}
	public double getPositionCost() {
		return positionCost;
	}
	public void setPositionCost(double positionCost) {
		this.positionCost = positionCost;
	}
	public double getOpenCost() {
		return openCost;
	}
	public void setOpenCost(double openCost) {
		this.openCost = openCost;
	}
	public double getAvgCost() {
		return avgCost;
	}
	public void setAvgCost(double avgCost) {
		this.avgCost = avgCost;
	}
	public int getVolumeMultiple() {
		return volumeMultiple;
	}
	public void setVolumeMultiple(int volumeMultiple) {
		this.volumeMultiple = volumeMultiple;
	}
	public String getHedge() {
		return hedge;
	}
	public void setHedge(String hedge) {
		this.hedge = hedge;
	}
	public int getCloseVolumn() {
		return closeVolumn;
	}
	public void setCloseVolumn(int closeVolumn) {
		this.closeVolumn = closeVolumn;
	}
	public double getCloseProfit() {
		return closeProfit;
	}
	public void setCloseProfit(double closeProfit) {
		this.closeProfit = closeProfit;
	}
	public double getOpenProfit() {
		return openProfit;
	}
	public void setOpenProfit(double openProfit) {
		this.openProfit = openProfit;
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
	public double getCommission() {
		return commission;
	}
	public void setCommission(double commission) {
		this.commission = commission;
	}
	public double getMargin() {
		return margin;
	}
	public void setMargin(double margin) {
		this.margin = margin;
	}
	public String getcTPID() {
		return cTPID;
	}
	public void setcTPID(String cTPID) {
		this.cTPID = cTPID;
	}
	public String getTradeDay() {
		return tradeDay;
	}
	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
	}
	public double getCloseProfitByTrade() {
		return closeProfitByTrade;
	}
	public void setCloseProfitByTrade(double closeProfitByTrade) {
		this.closeProfitByTrade = closeProfitByTrade;
	}
	public double getLastSettlementPrice() {
		return lastSettlementPrice;
	}
	public void setLastSettlementPrice(double lastSettlementPrice) {
		this.lastSettlementPrice = lastSettlementPrice;
	}
	public double getCloseProfitByDate() {
		return closeProfitByDate;
	}
	public void setCloseProfitByDate(double closeProfitByDate) {
		this.closeProfitByDate = closeProfitByDate;
	}
	public double getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(double settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	@Override
	public String toString() {
		return "PositionBean [instrumentID=" + instrumentID + ", direction="
				+ direction + ", price=" + price + ", position=" + position
				+ ", ydPosition=" + ydPosition + ", tdPosition=" + tdPosition
				+ ", noFrozen=" + noFrozen + ", positionCost=" + positionCost
				+ ", openCost=" + openCost + ", avgCost=" + avgCost
				+ ", volumeMultiple=" + volumeMultiple + ", hedge=" + hedge
				+ ", closeVolumn=" + closeVolumn + ", closeProfit="
				+ closeProfit + ", openProfit=" + openProfit
				+ ", positionProfitByTrade=" + positionProfitByTrade
				+ ", positionProfit=" + positionProfit + ", commission="
				+ commission + ", margin=" + margin + ", cTPID=" + cTPID
				+ ", tradeDay=" + tradeDay + ", closeProfitByTrade="
				+ closeProfitByTrade + ", lastSettlementPrice="
				+ lastSettlementPrice + ", closeProfitByDate="
				+ closeProfitByDate + ", settlementPrice=" + settlementPrice
				+ "]";
	}
	
}

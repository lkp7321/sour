package com.ylxx.qt.service.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class PositionDetailBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String productID;             //品种编号
	private String ctp_id;                // 交易账号
	private String instrument_id;         // 合约代码
	private String investor_id;           // 投资者代码
	private String direction;             // 买卖
	private int volume;                   // 数量
	private String trading_day;           // 交易日
	private double margin;                // 保证金
	private double last_settlementprice;  // 昨结算价
	private int close_volume;             // 平仓量
	private double price;                 // 持仓均价
	private int position;                 // 持仓量
	private int yd_position;              // 昨仓
	private int td_position;              // 今仓
	private int no_frozen;                // 可平量
	private double position_cost;         // 持仓均价
	private double open_cost;             // 开仓均价
	private double avg_cost;              // 全部均价
	private String hedge;                 // 投保
	private double close_profit;          // 平仓盈亏
	private double open_profit;           // 开仓盈亏
	private double position_profit;       // 持仓盈亏
	private double commission;            // 手续费
	private String trade_day;            
	private Timestamp updatetime;              
	private Timestamp inserttime;
	private double closeprofit_bytrade;   // 逐笔平仓盈亏
	
    private TradeFieldBean tfb;
	
	private double sum_margin; //持仓总和
	
	private double tradeSum;
	
	private int volumemultiple;  //合约乘数
	
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

	public int getYd_position() {
		return yd_position;
	}

	public void setYd_position(int yd_position) {
		this.yd_position = yd_position;
	}

	public int getTd_position() {
		return td_position;
	}

	public void setTd_position(int td_position) {
		this.td_position = td_position;
	}

	public int getNo_frozen() {
		return no_frozen;
	}

	public void setNo_frozen(int no_frozen) {
		this.no_frozen = no_frozen;
	}

	public double getPosition_cost() {
		return position_cost;
	}

	public void setPosition_cost(double position_cost) {
		this.position_cost = position_cost;
	}

	public double getOpen_cost() {
		return open_cost;
	}

	public void setOpen_cost(double open_cost) {
		this.open_cost = open_cost;
	}

	public double getAvg_cost() {
		return avg_cost;
	}

	public void setAvg_cost(double avg_cost) {
		this.avg_cost = avg_cost;
	}

	public String getHedge() {
		return hedge;
	}

	public void setHedge(String hedge) {
		this.hedge = hedge;
	}

	public double getClose_profit() {
		return close_profit;
	}

	public void setClose_profit(double close_profit) {
		this.close_profit = close_profit;
	}

	public double getOpen_profit() {
		return open_profit;
	}

	public void setOpen_profit(double open_profit) {
		this.open_profit = open_profit;
	}

	public double getPosition_profit() {
		return position_profit;
	}

	public void setPosition_profit(double position_profit) {
		this.position_profit = position_profit;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getTrade_day() {
		return trade_day;
	}

	public void setTrade_day(String trade_day) {
		this.trade_day = trade_day;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public Timestamp getInserttime() {
		return inserttime;
	}

	public void setInserttime(Timestamp inserttime) {
		this.inserttime = inserttime;
	}

	public double getCloseprofit_bytrade() {
		return closeprofit_bytrade;
	}

	public void setCloseprofit_bytrade(double closeprofit_bytrade) {
		this.closeprofit_bytrade = closeprofit_bytrade;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public TradeFieldBean getTfb() {
		return tfb;
	}

	public void setTfb(TradeFieldBean tfb) {
		this.tfb = tfb;
	}

	public double getSum_margin() {
		return sum_margin;
	}

	public void setSum_margin(double sum_margin) {
		this.sum_margin = sum_margin;
	}

	public PositionDetailBean(){
		
	}

	public String getCtp_id() {
		return ctp_id;
	}

	public void setCtp_id(String ctp_id) {
		this.ctp_id = ctp_id;
	}

	public String getInstrument_id() {
		return instrument_id;
	}

	public void setInstrument_id(String instrument_id) {
		this.instrument_id = instrument_id;
	}

	public String getInvestor_id() {
		return investor_id;
	}

	public void setInvestor_id(String investor_id) {
		this.investor_id = investor_id;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}


	public String getTrading_day() {
		return trading_day;
	}

	public void setTrading_day(String trading_day) {
		this.trading_day = trading_day;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}


	public double getLast_settlementprice() {
		return last_settlementprice;
	}

	public void setLast_settlementprice(double last_settlementprice) {
		this.last_settlementprice = last_settlementprice;
	}

	public int getClose_volume() {
		return close_volume;
	}

	public void setClose_volume(int close_volume) {
		this.close_volume = close_volume;
	}

	public double getTradeSum() {
		return tradeSum;
	}

	public void setTradeSum(double tradeSum) {
		this.tradeSum = tradeSum;
	}

	@Override
	public String toString() {
		return "PositionDetailBean [productID=" + productID + ", ctp_id="
				+ ctp_id + ", instrument_id=" + instrument_id
				+ ", investor_id=" + investor_id + ", direction=" + direction
				+ ", volume=" + volume + ", trading_day=" + trading_day
				+ ", margin=" + margin + ", last_settlementprice="
				+ last_settlementprice + ", close_volume=" + close_volume
				+ ", tfb=" + tfb + ", sum_margin=" + sum_margin + ", tradeSum="
				+ tradeSum + "]";
	}

	public int getVolumemultiple() {
		return volumemultiple;
	}

	public void setVolumemultiple(int volumemultiple) {
		this.volumemultiple = volumemultiple;
	}
	
}

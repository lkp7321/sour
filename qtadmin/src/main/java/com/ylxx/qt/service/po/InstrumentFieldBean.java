package com.ylxx.qt.service.po;

import java.io.Serializable;
import java.util.List;

public class InstrumentFieldBean implements Serializable{   //合约表

	private static final long serialVersionUID = 1L;
	
	private List<TradeFieldBean> tfb;
	
	private String ctp_id;                // CTPID
	private String productID;             //产品编号
	private String instrument_id;          //合约编号
	private String Instrumentname;        //合约名称
	private String exchange_id;			  //交易所id
	private double p;                //price * volume
	private double tradeSum;              //某品种成交额
	private double allSum;                //所有品种总成交额
	private ProductBean productbean;
	
	private double volumemultiple; // 合约乘数
	
	public List<TradeFieldBean> getTfb() {
		return tfb;
	}
	public void setTfb(List<TradeFieldBean> tfb) {
		this.tfb = tfb;
	}
	public String getCtp_id() {
		return ctp_id;
	}
	public void setCtp_id(String ctp_id) {
		this.ctp_id = ctp_id;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getInstrumentname() {
		return Instrumentname;
	}
	public void setInstrumentname(String instrumentname) {
		Instrumentname = instrumentname;
	}
	public double getP() {
		return p;
	}
	public void setP(double p) {
		this.p = p;
	}
	public double getTradeSum() {
		return tradeSum;
	}
	public void setTradeSum(double tradeSum) {
		this.tradeSum = tradeSum;
	}
	public double getAllSum() {
		return allSum;
	}
	public void setAllSum(double allSum) {
		this.allSum = allSum;
	}
	public String getInstrument_id() {
		return instrument_id;
	}
	public void setInstrument_id(String instrument_id) {
		this.instrument_id = instrument_id;
	}
	
	public String getExchange_id() {
		return exchange_id;
	}
	public void setExchange_id(String exchange_id) {
		this.exchange_id = exchange_id;
	}
	public ProductBean getProductbean() {
		return productbean;
	}
	public void setProductbean(ProductBean productbean) {
		this.productbean = productbean;
	}
	@Override
	public String toString() {
		return "InstrumentFieldBean [tfb=" + tfb + ", ctp_id="
				+ ctp_id + ", productID=" + productID + ", instrument_id="
				+ instrument_id + ", Instrumentname=" + Instrumentname + ", p="
				+ p + ", tradeSum=" + tradeSum + ", allSum=" + allSum
				+ ", getTfb()="
				+ getTfb() + ", getCtp_id()=" + getCtp_id()
				+ ", getProductID()=" + getProductID()
				+ ", getInstrumentname()=" + getInstrumentname() + ", getP()="
				+ getP() + ", getTradeSum()=" + getTradeSum()
				+ ", getAllSum()=" + getAllSum() + ", getInstrument_id()="
				+ getInstrument_id() + "]";
	}
	public InstrumentFieldBean(List<TradeFieldBean> tfb, String ctp_id,
			String productID, String instrument_id, String instrumentname,
			double p, double tradeSum, double allSum) {
		super();
		this.tfb = tfb;
		this.ctp_id = ctp_id;
		this.productID = productID;
		this.instrument_id = instrument_id;
		Instrumentname = instrumentname;
		this.p = p;
		this.tradeSum = tradeSum;
		this.allSum = allSum;
	}
	
	public InstrumentFieldBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public double getVolumemultiple() {
		return volumemultiple;
	}
	public void setVolumemultiple(double volumemultiple) {
		this.volumemultiple = volumemultiple;
	}

}
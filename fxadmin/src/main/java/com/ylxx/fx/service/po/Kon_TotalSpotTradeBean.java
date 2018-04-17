package com.ylxx.fx.service.po;

import java.math.BigDecimal;

public class Kon_TotalSpotTradeBean {
	private String productcode;
	private String kondornumber;
	private String currencypairs;
	private String tradedate;
	private BigDecimal amount1;
	private BigDecimal amount2;
	private BigDecimal spotrate;
	private String states;
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getKondornumber() {
		return kondornumber;
	}
	public void setKondornumber(String kondornumber) {
		this.kondornumber = kondornumber;
	}
	public String getCurrencypairs() {
		return currencypairs;
	}
	public void setCurrencypairs(String currencypairs) {
		this.currencypairs = currencypairs;
	}
	public String getTradedate() {
		return tradedate;
	}
	public void setTradedate(String tradedate) {
		this.tradedate = tradedate;
	}
	public BigDecimal getAmount1() {
		return amount1;
	}
	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}
	public BigDecimal getAmount2() {
		return amount2;
	}
	public void setAmount2(BigDecimal amount2) {
		this.amount2 = amount2;
	}
	public BigDecimal getSpotrate() {
		return spotrate;
	}
	public void setSpotrate(BigDecimal spotrate) {
		this.spotrate = spotrate;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}	
}

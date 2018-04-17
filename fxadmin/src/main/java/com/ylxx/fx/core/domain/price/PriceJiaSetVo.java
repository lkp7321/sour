package com.ylxx.fx.core.domain.price;

public class PriceJiaSetVo {
	private String userKey;
	private String prod;
	private String time;
	private PriceJiaVo priceJiaBean;
	
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public PriceJiaVo getPriceJiaBean() {
		return priceJiaBean;
	}
	public void setPriceJiaBean(PriceJiaVo priceJiaBean) {
		this.priceJiaBean = priceJiaBean;
	}
	
}

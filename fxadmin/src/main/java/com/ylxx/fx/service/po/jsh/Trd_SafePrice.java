package com.ylxx.fx.service.po.jsh;

/**
 * 利率
 * @author lz130
 *
 */
public class Trd_SafePrice {
	private String trdt;	//交易日期
	private String cyen;	//币种
	private String price;	//汇率
	private String cout;	//种类
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getCyen() {
		return cyen;
	}
	public void setCyen(String cyen) {
		this.cyen = cyen;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCout() {
		return cout;
	}
	public void setCout(String cout) {
		this.cout = cout;
	}
	
}

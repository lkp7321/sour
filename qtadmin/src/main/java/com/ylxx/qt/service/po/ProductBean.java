package com.ylxx.qt.service.po;

public class ProductBean {
	private String productName;//ProductName
	private String productId;
	private String exchangeNo;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getExchangeNo() {
		return exchangeNo;
	}
	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}
	
	/*@Override
	public String toString() {
		return "ProductBean [productName=" + productName + ", productId="
				+ productId + "]";
	}*/
	
}

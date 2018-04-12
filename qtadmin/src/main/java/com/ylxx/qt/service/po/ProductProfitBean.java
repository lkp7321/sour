package com.ylxx.qt.service.po;

import java.io.Serializable;


public class ProductProfitBean implements Serializable {
	private double positionProfit;
	private String productid;

	public double getPositionProfit() {
		return positionProfit;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public void setPositionProfit(double positionProfit) {
		this.positionProfit = positionProfit;
	}
}

package com.ylxx.qt.service.po;

import java.util.List;

public class PriceSheetList {
	private List<PriceSheet> list;
	private String ProId;
	private String pricetick;
	
	public List<PriceSheet> getList() {
		return list;
	}
	public void setList(List<PriceSheet> list) {
		this.list = list;
	}
	public String getProId() {
		return ProId;
	}
	public void setProId(String proId) {
		ProId = proId;
	}
	public String getPricetick() {
		return pricetick;
	}
	public void setPricetick(String pricetick) {
		this.pricetick = pricetick;
	}
	
}

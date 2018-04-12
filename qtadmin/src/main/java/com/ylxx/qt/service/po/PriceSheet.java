package com.ylxx.qt.service.po;

import java.io.Serializable;

public class PriceSheet implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer priceid;
	private String	productid;//品种id
	private String	productname;//品种名称
	private Integer	year;
	private	Integer pricetype;
	private	Double	histortyprice;//历史价
	private	Double	highestprice;
	private	Double	lowestprice;
	private Double	pricetick;
	public PriceSheet(){
		
	}
	public PriceSheet(Integer priceid, String productid, String productname,
			Integer year, Integer pricetype, Double histortyprice,
			Double highestprice, Double lowestprice, Double pricetick) {
		super();
		this.priceid = priceid;
		this.productid = productid;
		this.productname = productname;
		this.year = year;
		this.pricetype = pricetype;
		this.histortyprice = histortyprice;
		this.highestprice = highestprice;
		this.lowestprice = lowestprice;
		this.pricetick = pricetick;
	}
	public Integer getPriceid() {
		return priceid;
	}
	public void setPriceid(Integer priceid) {
		this.priceid = priceid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getPricetype() {
		return pricetype;
	}
	public void setPricetype(Integer pricetype) {
		this.pricetype = pricetype;
	}
	public Double getHistortyprice() {
		return histortyprice;
	}
	public void setHistortyprice(Double histortyprice) {
		this.histortyprice = histortyprice;
	}
	public Double getHighestprice() {
		return highestprice;
	}
	public void setHighestprice(Double highestprice) {
		this.highestprice = highestprice;
	}
	public Double getLowestprice() {
		return lowestprice;
	}
	public void setLowestprice(Double lowestprice) {
		this.lowestprice = lowestprice;
	}
	public Double getPricetick() {
		return pricetick;
	}
	public void setPricetick(Double pricetick) {
		this.pricetick = pricetick;
	}
	
}

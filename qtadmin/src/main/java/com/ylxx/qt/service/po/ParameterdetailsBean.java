package com.ylxx.qt.service.po;

import java.io.Serializable;
/**
 * @author LiangYongJian
 * @Date 2017 2017年12月27日 上午11:27:13
 */
public class ParameterdetailsBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer parametid;//参数id
	private String  userid;//用户id
	private String  productid;//品种id
	private String	productname;//品种名称
	private Integer bullandbearratio;//多空比
	private Integer	segmenttype;//分段类型
	private Integer longsegment;//多分段
	private Integer	shortsegment;//空分段
	private Double	segmentprice;//分段价
	private String	weightset;//权重集合
	private String	contractset;//合约id集合
	
	public ParameterdetailsBean(){
		
	}
	
	public ParameterdetailsBean(Integer parametid, String userid,
			String productid, String productname, Integer bullandbearratio,
			Integer segmenttype, Integer longsegment, Integer shortsegment,
			Double segmentprice, String weightset, String contractset) {
		super();
		this.parametid = parametid;
		this.userid = userid;
		this.productid = productid;
		this.productname = productname;
		this.bullandbearratio = bullandbearratio;
		this.segmenttype = segmenttype;
		this.longsegment = longsegment;
		this.shortsegment = shortsegment;
		this.segmentprice = segmentprice;
		this.weightset = weightset;
		this.contractset = contractset;
	}

	public Integer getParametid() {
		return parametid;
	}
	public void setParametid(Integer parametid) {
		this.parametid = parametid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public Integer getBullandbearratio() {
		return bullandbearratio;
	}
	public void setBullandbearratio(Integer bullandbearratio) {
		this.bullandbearratio = bullandbearratio;
	}
	public Integer getSegmenttype() {
		return segmenttype;
	}
	public void setSegmenttype(Integer segmenttype) {
		this.segmenttype = segmenttype;
	}
	public Integer getLongsegment() {
		return longsegment;
	}
	public void setLongsegment(Integer longsegment) {
		this.longsegment = longsegment;
	}
	public Integer getShortsegment() {
		return shortsegment;
	}
	public void setShortsegment(Integer shortsegment) {
		this.shortsegment = shortsegment;
	}
	public Double getSegmentprice() {
		return segmentprice;
	}
	public void setSegmentprice(Double segmentprice) {
		this.segmentprice = segmentprice;
	}
	public String getWeightset() {
		return weightset;
	}
	public void setWeightset(String weightset) {
		this.weightset = weightset;
	}
	public String getContractset() {
		return contractset;
	}
	public void setContractset(String contractset) {
		this.contractset = contractset;
	}
	
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}


	
	
	
}

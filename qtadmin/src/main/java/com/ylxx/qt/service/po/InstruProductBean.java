package com.ylxx.qt.service.po;
import java.util.List;

public class InstruProductBean {
	
	private List<InstruProductBean> list;
	private String instrumentid;	//合约代码
	private String productname;	    //品种名称


	public InstruProductBean() {

	}

	public InstruProductBean(String instrumentid, String productname) {
		super();
		this.instrumentid = instrumentid;
		this.productname = productname;
	}

	public List<InstruProductBean> getList() {
		return list;
	}

	public void setList(List<InstruProductBean> list) {
		this.list = list;
	}
	
	public String getInstrumentid() {
		return instrumentid;
	}

	public void setInstrumentid(String instrumentid) {
		this.instrumentid = instrumentid;
	}

	public String getProductname() {
		return productname;
	}
}

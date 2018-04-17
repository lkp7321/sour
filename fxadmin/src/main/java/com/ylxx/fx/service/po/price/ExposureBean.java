package com.ylxx.fx.service.po.price;

public class ExposureBean {
	
	private String prcd;// 产品代码
	private String exnm;// 币别对简称
	private String excd;// 币别对代码
	private String lamt = "0.00";// 轧差左头寸
	private String ramt = "0.00";// 轧差右头寸
	private String nesl = "0.00";// 卖出价
	private String neby = "0.00";// 买入价
	private String fdsy = "0.00";// 浮动损益
	private String updt;// 更新日期
	private String uptm;// 更新时间
	private String udfg;// 浮动标志
	private String cbhl = "0.00";// 成本汇率
	private String ppyk = "0.00";//自动平盘交易盈亏
	private String zcyk = "0.00";//头寸轧差盈亏
	private String sgyk = "0.00";// 
	private String tryk = "0.00";//
	private String toyk = "0.00";//
	public String getPrcd() {
		return prcd;
	}
	public void setPrcd(String prcd) {
		this.prcd = prcd;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getExcd() {
		return excd;
	}
	public void setExcd(String excd) {
		this.excd = excd;
	}
	public String getLamt() {
		return lamt;
	}
	public void setLamt(String lamt) {
		this.lamt = lamt;
	}
	public String getRamt() {
		return ramt;
	}
	public void setRamt(String ramt) {
		this.ramt = ramt;
	}
	public String getNesl() {
		return nesl;
	}
	public void setNesl(String nesl) {
		this.nesl = nesl;
	}
	public String getNeby() {
		return neby;
	}
	public void setNeby(String neby) {
		this.neby = neby;
	}
	public String getFdsy() {
		return fdsy;
	}
	public void setFdsy(String fdsy) {
		this.fdsy = fdsy;
	}
	public String getUpdt() {
		return updt;
	}
	public void setUpdt(String updt) {
		this.updt = updt;
	}
	public String getUptm() {
		return uptm;
	}
	public void setUptm(String uptm) {
		this.uptm = uptm;
	}
	public String getUdfg() {
		return udfg;
	}
	public void setUdfg(String udfg) {
		this.udfg = udfg;
	}
	public String getCbhl() {
		return cbhl;
	}
	public void setCbhl(String cbhl) {
		this.cbhl = cbhl;
	}
	public String getPpyk() {
		return ppyk;
	}
	public void setPpyk(String ppyk) {
		this.ppyk = ppyk;
	}
	public String getZcyk() {
		return zcyk;
	}
	public void setZcyk(String zcyk) {
		this.zcyk = zcyk;
	}
	public String getSgyk() {
		return sgyk;
	}
	public void setSgyk(String sgyk) {
		this.sgyk = sgyk;
	}
	public String getTryk() {
		return tryk;
	}
	public void setTryk(String tryk) {
		this.tryk = tryk;
	}
	public String getToyk() {
		return toyk;
	}
	public void setToyk(String toyk) {
		this.toyk = toyk;
	}
	
}

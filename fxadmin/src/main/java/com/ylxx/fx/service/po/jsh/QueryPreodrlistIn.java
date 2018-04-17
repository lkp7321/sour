package com.ylxx.fx.service.po.jsh;

public class QueryPreodrlistIn {
	private String cuno;//客户号
	private String productCode;//产品编号
	private String rqno;//挂单合同号
	private String stdt;//起始日期
	private String eddt;//截止日期
	private int qutp;//状态
	public String getCuno() {
		return cuno;
	}
	public void setCuno(String cuno) {
		this.cuno = cuno;
	}
	public String getRqno() {
		return rqno;
	}
	public void setRqno(String rqno) {
		this.rqno = rqno;
	}
	public String getStdt() {
		return stdt;
	}
	public void setStdt(String stdt) {
		this.stdt = stdt;
	}
	public String getEddt() {
		return eddt;
	}
	public void setEddt(String eddt) {
		this.eddt = eddt;
	}
	public int getQutp() {
		return qutp;
	}
	public void setQutp(int qutp) {
		this.qutp = qutp;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}

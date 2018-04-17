package com.ylxx.fx.service.po;

import java.io.Serializable;

public class PpsyBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String lkno;
	private String lcno;
	private String seqn;
	private String trdt;
	private String trtm;
	private String trty;
	private String ckno;
	private String exnm;
	private String autopp="0";
	private String handpp="0";
	private Double totapp=0.0;
	private Double autoppsy=0.0;
	private Double handppsy=0.0;
	private Double total_lamt=0.0;
	private Double total_ramt=0.0;
	private Double cbpc=0.0;
	private Double cusy=0.0;
	private String cksn;
	private String opno;
	private String stcd;
	public String getLkno() {
		return lkno;
	}
	public void setLkno(String lkno) {
		this.lkno = lkno;
	}
	public String getLcno() {
		return lcno;
	}
	public void setLcno(String lcno) {
		this.lcno = lcno;
	}
	public String getSeqn() {
		return seqn;
	}
	public void setSeqn(String seqn) {
		this.seqn = seqn;
	}
	public String getTrdt() {
		return trdt;
	}
	public void setTrdt(String trdt) {
		this.trdt = trdt;
	}
	public String getTrtm() {
		return trtm;
	}
	public void setTrtm(String trtm) {
		this.trtm = trtm;
	}
	public String getTrty() {
		return trty;
	}
	public void setTrty(String trty) {
		this.trty = trty;
	}
	public String getCkno() {
		return ckno;
	}
	public void setCkno(String ckno) {
		this.ckno = ckno;
	}
	public String getExnm() {
		return exnm;
	}
	public void setExnm(String exnm) {
		this.exnm = exnm;
	}
	public String getAutopp() {
		return autopp;
	}
	public void setAutopp(String autopp) {
		this.autopp = autopp;
	}
	public String getHandpp() {
		return handpp;
	}
	public void setHandpp(String handpp) {
		this.handpp = handpp;
	}
	public Double getTotapp() {
		return totapp;
	}
	public void setTotapp(Double totapp) {
		this.totapp = totapp;
	}
	public Double getAutoppsy() {
		return autoppsy;
	}
	public void setAutoppsy(Double autoppsy) {
		this.autoppsy = autoppsy;
	}
	public Double getHandppsy() {
		return handppsy;
	}
	public void setHandppsy(Double handppsy) {
		this.handppsy = handppsy;
	}
	public Double getTotal_lamt() {
		return total_lamt;
	}
	public void setTotal_lamt(Double total_lamt) {
		this.total_lamt = total_lamt;
	}
	public Double getTotal_ramt() {
		return total_ramt;
	}
	public void setTotal_ramt(Double total_ramt) {
		this.total_ramt = total_ramt;
	}
	public Double getCbpc() {
		return cbpc;
	}
	public void setCbpc(Double cbpc) {
		this.cbpc = cbpc;
	}
	public Double getCusy() {
		return cusy;
	}
	public void setCusy(Double cusy) {
		this.cusy = cusy;
	}
	public String getCksn() {
		return cksn;
	}
	public void setCksn(String cksn) {
		this.cksn = cksn;
	}
	public String getOpno() {
		return opno;
	}
	public void setOpno(String opno) {
		this.opno = opno;
	}
	public String getStcd() {
		return stcd;
	}
	public void setStcd(String stcd) {
		this.stcd = stcd;
	}
	public PpsyBean() {
		super();
	}
	public PpsyBean(String lkno, String lcno, String seqn, String trdt,
			String trtm, String trty, String ckno, String exnm, String autopp,
			String handpp, Double totapp, Double autoppsy, Double handppsy,
			Double total_lamt, Double total_ramt, Double cbpc, Double cusy,
			String cksn, String opno, String stcd) {
		super();
		this.lkno = lkno;
		this.lcno = lcno;
		this.seqn = seqn;
		this.trdt = trdt;
		this.trtm = trtm;
		this.trty = trty;
		this.ckno = ckno;
		this.exnm = exnm;
		this.autopp = autopp;
		this.handpp = handpp;
		this.totapp = totapp;
		this.autoppsy = autoppsy;
		this.handppsy = handppsy;
		this.total_lamt = total_lamt;
		this.total_ramt = total_ramt;
		this.cbpc = cbpc;
		this.cusy = cusy;
		this.cksn = cksn;
		this.opno = opno;
		this.stcd = stcd;
	}
	
}

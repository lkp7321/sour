package com.ylxx.qt.service.po;

import java.io.Serializable;

public class CapitalBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String ctp_id;
	private String account_id;
	private double capital;
	
	public CapitalBean(){
		
	}
	
	public CapitalBean(String ctp_id, String account_id, double capital) {
		super();
		this.ctp_id = ctp_id;
		this.account_id = account_id;
		this.capital = capital;
	}



	public String getCtp_id() {
		return ctp_id;
	}

	public void setCtp_id(String ctp_id) {
		this.ctp_id = ctp_id;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}

	@Override
	public String toString() {
		return "CapitalBean [ctp_id=" + ctp_id + ", account_id=" + account_id
				+ ", capital=" + capital + "]";
	}
	
}

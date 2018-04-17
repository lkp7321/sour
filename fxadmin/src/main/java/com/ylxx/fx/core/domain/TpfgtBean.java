package com.ylxx.fx.core.domain;

import java.io.Serializable;

public class TpfgtBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String tpfg;//价格类型SPT即期FWD远期SWP掉期OPT期权
	private String tpnm;//价格类型名称
	public String getTpfg() {
		return tpfg;
	}
	public void setTpfg(String tpfg) {
		this.tpfg = tpfg;
	}
	public String getTpnm() {
		return tpnm;
	}
	public void setTpnm(String tpnm) {
		this.tpnm = tpnm;
	}
}

package com.ylxx.fx.service.po;

import java.io.Serializable;

public class LogfileBean implements Serializable{
	private static final long serialVersionUID = -2682305557890221059L;
	private String rzdt;
	private String rzsj;
	private String usem;
	private String tymo;
	private String remk;
	private String vold;
	private String vnew;
	private String prod;
	public String getRzdt() {
		return rzdt;
	}
	public void setRzdt(String rzdt) {
		this.rzdt = rzdt;
	}
	public String getRzsj() {
		return rzsj;
	}
	public void setRzsj(String rzsj) {
		this.rzsj = rzsj;
	}
	public String getUsem() {
		return usem;
	}
	public void setUsem(String usem) {
		this.usem = usem;
	}
	public String getTymo() {
		return tymo;
	}
	public void setTymo(String tymo) {
		this.tymo = tymo;
	}
	public String getRemk() {
		return remk;
	}
	public void setRemk(String remk) {
		this.remk = remk;
	}
	public String getVold() {
		return vold;
	}
	public void setVold(String vold) {
		this.vold = vold;
	}
	public String getVnew() {
		return vnew;
	}
	public void setVnew(String vnew) {
		this.vnew = vnew;
	}
	public String getProd() {
		return prod;
	}
	public void setProd(String prod) {
		this.prod = prod;
	}
}

package com.ylxx.fx.service.po;

import java.io.Serializable;

public class Testtrac implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String trac;
	public String usfg;
	public String cuac;
	public String getTrac() {
		return trac;
	}
	public void setTrac(String trac) {
		this.trac = trac;
	}
	public String getUsfg() {
		return usfg;
	}
	public void setUsfg(String usfg) {
		this.usfg = usfg;
	}
	public String getCuac() {
		return cuac;
	}
	public void setCuac(String cuac) {
		this.cuac = cuac;
	}
	public Testtrac(String trac, String usfg, String cuac) {
		super();
		this.trac = trac;
		this.usfg = usfg;
		this.cuac = cuac;
	}
	public Testtrac() {
		super();
	}
	
	
}

package com.ylxx.fx.utils;

public enum ErrorCodeSystem {
	E_0("NONE"),
	E_00("SYSCLSUCCESS"),
	E_01("SYSCLADD"),
	E_02("SYSCLUP"),
	E_03("SYSCLDE"),
	E_04("PPSYS"),
	E_05("CONSYS"),
	
	E_06("CHANGEPP"),
	
	E_11("ORGANADD"),
	E_12("ORGANUP"),
	E_13("ORGANADDS"),
	E_14("ORGANUPS"),
	E_15("ORGANHES"),
	E_16("ORGANHE1"),
	E_17("ORGANHE2"),
	E_18("ORGANHE3"),
	E_19("ORGANDE"),
	E_20("ORGANDES"),
	
	E_22("REFSHPRICE"),
	E_23("REFSHPRICEPT"),
	;
	
	
	private String code;
	ErrorCodeSystem(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}


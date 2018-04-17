package com.ylxx.fx.utils;

public enum ErrorCodeCk {
	E_0("NONE"),
	
	E_00("CKLIUSHUI"),
	
	E_01("CKRULETSUCCESS"),
	E_02("CKRULETAD"),
	E_03("CKRULETADD"),
	E_04("CKRULETADS"),
	E_05("CKRULETUP"),
	E_06("CKRULETLEVE"),
	E_07("CKRULETYANZHENG"),
	E_08("CKRULETUPYANZHENG"),
	
	E_10("PPSY"),
	
	E_11("SPCUTSUCCESS"),
	E_12("SPCUTDEl"),
	E_13("SPCUTADD"),
	E_14("SPCUTUP"),
	E_15("SPCUTAD"),
	
	E_16("TYNMSUCCESS"),
	E_17("TYNMDEL"),
	E_18("TYNMADD"),
	E_19("TYNMUP"),
	E_20("TYNMAD"),
	
	E_21("PPRULETSUCCESS"),
	E_22("PPRULETUPLIST"),
	E_23("PPRULETUP"),
	;
	
	
	private String code;
	ErrorCodeCk(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}


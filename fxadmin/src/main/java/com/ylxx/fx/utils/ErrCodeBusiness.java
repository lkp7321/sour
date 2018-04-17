package com.ylxx.fx.utils;

public enum ErrCodeBusiness {
	busines_0("BUSNONE"),
	busines_00("BUSINESUC"),
	
	busines_01("ORANGNO1"),
	busines_02("ORANGNO2"),
	busines_03("TRANSCODE"),
	busines_04("EXNMCODE"),
	busines_05("FAVOPEN"),
	busines_06("FAVSTOP"),
	busines_07("FAVUPDATE"),
	busines_08("PTPARA"),
	busines_09("TRADECALDE"),
	
	busines_11("TRADECALIN"),
	busines_12("TRADECALUP"),
	busines_13("TRADEPRODE"),
	busines_14("TRADEPROIN"),
	busines_15("TRADEPROUP"),
	busines_16("CALSHOWCODE"),
	busines_17("CALSHOWSS"),
	busines_18("CALlEVE"),
	busines_19("PROTRADE"),
	busines_21("TRADEPROINs"),
	busines_22("INITFAV"),
	;
	
	private String code;
	ErrCodeBusiness(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}


package com.ylxx.fx.utils;

public enum ErrorCode {
	E_00("SUCCESS"),
	E_01("ERROR"),
	
	E_000("LOGINSUCCESS"),
	E_001("PRODUCTS"),
	E_002("PRODUCT"),
	E_0011("LOGINECOUNT"),
	E_0012("LOGINROLE"),
	E_0013("LOGINTIME"),
	E_0014("LOGINERROR"),
	E_0015("LOGINLOG"),
	E_0016("LOGINDAYS"),
	E_0017("LOGINFIRST"),
	E_0010("LOGINNONE"),
	
	E_0020("USERSUCCEESS"),
	E_0021("USERNONE"),
	E_0022("USERADD"),
	E_0023("USERUP"),
	E_0024("USERDE"), 
	E_0025("ISUSER"), 
	E_0026("COMITPS"), 
	E_0027("USERFG"), 
	E_0028("OLDPS"),
	E_0029("UPPS"),
	E_0030("DEERROR"),
	E_9990("NODEADMIN"),
	E_9991("NOFGADMIN"),
	E_9992("COMITADMIN"),
	E_9993("INITROLE"),
	E_9994("INITOG"),
	E_9995("USERUP1"),
	
	E_0031("ROLESUCCESS"),
	E_0032("ROLEADD"),
	E_0033("ROLEUP"),
	E_0034("ROLEDE"), 
	E_0035("HASROLE"),
	E_0036("HASROLES"),
	
	E_0040("NRLRSUCCESS"),
	E_0041("NRLRADD"),
	E_0042("NRLRDE"),
	E_0043("NRLRFG1"),
	E_0044("NRLRFG2"),
	E_0045("NRLRNONE"), 
	;
	
	
	private String code;
	ErrorCode(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	

}


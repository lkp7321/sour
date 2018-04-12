package com.ylxx.qt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ResultDomain {
static final Logger logger = LoggerFactory.getLogger(ResultDomain.class);
	
	private static String CODE="01";
	private static String MSG="交易异常";
	
	private String 	code;
	private String 	codeMessage;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}
	/**
	 * 返回错误码
	 * @param code 错误码配置文件中的唯一标识
	 * @return String json格式错误信息
	 */
	public static String getRtnMsg(String code){
		ResultDomain result = new ResultDomain();
		String resultCode = PropertiesUtil.getValue("FXADMIN_" +code+ "_CODE");
		
		String resultMsg = PropertiesUtil.getValue("FXADMIN_"+code+"_MESSAGE"); 

		result.setCode(null==resultCode||"".equals(resultCode)?CODE:resultCode);
		result.setCodeMessage(null==resultMsg||"".equals(resultCode)?MSG:resultMsg);
//		System.out.println(JSONObject.toJSONString(result));
		logger.error("接口返回信息：" + JSONObject.toJSONString(result));
		return JSONObject.toJSONString(result);
	}
}

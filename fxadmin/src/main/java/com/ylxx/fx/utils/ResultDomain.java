package com.ylxx.fx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class ResultDomain {
static final Logger logger = LoggerFactory.getLogger(ResultDomain.class);
	
	private static String CODE="01";
	private static String MSG="交易异常";
	
	private String 	code;
	private Object 	codeMessage;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public Object getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(Object codeMessage) {
		this.codeMessage = codeMessage;
	}
	/**
	 * 返回错误码
	 * @param code 错误码配置文件中的唯一标识
	 * @return String json格式错误信息
	 */
	public static String getRtnMsg(String code,Object codeMessage){
		logger.info("获取状态码："+"code:"+code+",codeMessage："+codeMessage);
		ResultDomain result = new ResultDomain();
		String resultCode = PropertiesUtil.getValue("FXADMIN_" +code+ "_CODE");
		
		String resultMsg = PropertiesUtil.getValue("FXADMIN_"+code+"_MESSAGE"); 
		
		result.setCode(null==resultCode||"".equals(resultCode)?CODE:resultCode);
		result.setCodeMessage(null==codeMessage||"".equals(codeMessage)?resultMsg:codeMessage);
//		System.out.println(JSONObject.toJSONString(result));
		logger.info("接口返回信息：" + JSONObject.toJSONString(result));
		return JSONObject.toJSONString(result);
	}
}

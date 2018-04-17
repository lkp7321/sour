package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;
import java.util.Map;

public interface HandRegisterMapper{
	
	//在总敞口中查找币别对
	public List<Map<String, String>> selectCurrencyPair(String prod) throws Exception;
	//获取敞口列表
	public List<Map<String, String>> selectCkno(String prod) throws Exception;
	//获取平盘对手列表
	public List<Map<String, String>> selectPPDS() throws Exception;
	//获取平盘对手列表:账户交易>>手工平盘
	public List<Map<String, String>> selPPDS() throws Exception;
	
}
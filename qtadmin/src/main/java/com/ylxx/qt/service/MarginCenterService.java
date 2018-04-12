package com.ylxx.qt.service;

import java.util.List;
import java.util.Map;

public interface MarginCenterService {
	//登录成功返回登录成功页面
	public Map<String,String> LoginSuccess(String date)throws Exception;
	
	//更新表
	public String UpdateTradingaccount(Map<String,String> map)throws Exception;
	
	
	//首次插入表
	public String InsertTradingaccount(Map<String,String> map)throws Exception;
	
	//首次注册登录
	public List<Map<String, String>> RegistLogin(List<String> dates)throws Exception;
	
	//测试公式对不对
			/*逐日平仓盈亏=(成交价-昨日结算价）*合约乘数*成交数量
			逐笔平仓盈亏=（成交价-开仓价）*合约乘数*成交数量*/
	public Map<String,String> TestGG(String date)throws Exception;
	
}

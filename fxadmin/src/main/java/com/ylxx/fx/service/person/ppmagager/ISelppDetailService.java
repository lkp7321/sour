package com.ylxx.fx.service.person.ppmagager;

public interface ISelppDetailService {

	//查询货币对列表
	public String selUSDEXNM(String userKey) throws Exception;
	//(条件)查询页面数据列表
	public String selAllList(String prod,String sartDate, String endDate,
			String strExnm, String strStat) throws Exception;
}


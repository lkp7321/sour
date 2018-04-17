package com.ylxx.fx.service.kondor.kondorrv;

public interface IKonCashTradeService {
	
	//查询页面数据
	public String selAllCashList(String startDate,String endDate, String rpcNo,
			Integer pageNo,Integer pageSize) throws Exception;

}


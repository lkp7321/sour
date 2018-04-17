package com.ylxx.fx.service.kondor.kondorspot;

public interface IKonTotalSpotTradeService {

	//查询页面数据
	public String selTotalSpotList(String startDate,String endDate, String tradeCode, 
			String rpcNo,Integer pageNo,Integer pageSize) throws Exception;

}


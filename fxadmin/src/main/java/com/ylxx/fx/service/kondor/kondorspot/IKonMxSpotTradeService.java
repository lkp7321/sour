package com.ylxx.fx.service.kondor.kondorspot;

public interface IKonMxSpotTradeService {

	//查询页面数据
	public String selMxSpotList(String startDate,String endDate, String tradeCode, 
			String rpcNo,Integer pageNo,Integer pageSize) throws Exception;

}


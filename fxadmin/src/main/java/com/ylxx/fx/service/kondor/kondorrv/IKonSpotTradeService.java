package com.ylxx.fx.service.kondor.kondorrv;

public interface IKonSpotTradeService {

	//查询页面数据
	public String selAllSpotList(String startDate,String endDate, String tradeCode,
			String rpcNo,Integer pageNo,Integer pageSize) throws Exception;

}


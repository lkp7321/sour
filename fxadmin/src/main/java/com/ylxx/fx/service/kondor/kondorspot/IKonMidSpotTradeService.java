package com.ylxx.fx.service.kondor.kondorspot;

public interface IKonMidSpotTradeService {

	//查询页面数据
	public String selMidSpotList(String startDate,String endDate, String rpcNo,
			Integer pageNo,Integer pageSize) throws Exception;

}


package com.ylxx.fx.service.kondor.kondorspot;

public interface IKonSpotExceptionService {

	//查询
	public String selExceptionList(String startDate, String endDate,
			Integer pageNo,Integer pageSize) throws Exception;
	//重新发送
	public String sendException(String prcd,String rpcNo) throws Exception;

}


package com.ylxx.fx.service.kondor.kondorrv;

public interface IKonBondTradeService {

	/**
	 * 查询页面数据
	 * @param startDate
	 * @param endDate
	 * @param rpcNo
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	String selAllBondList(String startDate,String endDate, String rpcNo,
				Integer pageNo,Integer pageSize) throws Exception;

}


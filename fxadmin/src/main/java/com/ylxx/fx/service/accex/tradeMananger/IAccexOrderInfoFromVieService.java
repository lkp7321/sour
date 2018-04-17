package com.ylxx.fx.service.accex.tradeMananger;

import java.util.List;
import java.util.Map;


public interface IAccexOrderInfoFromVieService {
	/**
	 * 获取持仓表视图数据   
	 * @param pageNo
	 * @param pageSize
	 * @param endDate
	 * @param prod
	 * @param strateDate
	 * @param userKey
	 * @return
	 */
	public String getOrderListFromVie(
			Integer pageNo, Integer pageSize, 
			String endDate, String prod, 
			String strateDate, String userKey);
	public List<Map<String, Object>> getAllOrderListFromVie(
			String endDate, String prod, 
			String strateDate, String userKey);

}


package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.TradingAccountFiledBean;

/**
 * 收益排名
 * @author suimanman
 *
 */
public interface RevenueRankingService {
	
	/**
	 * 获取收益排名信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TradingAccountFiledBean> getAllRevenue() throws Exception;
	
}

package com.ylxx.qt.core.mapper;

import java.util.List;

import com.ylxx.qt.service.po.TradingAccountFiledBean;

public interface RevenueRankingMapper {
	/**
	 * 获取收益排名信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TradingAccountFiledBean> getAllRevenue() throws Exception;

}

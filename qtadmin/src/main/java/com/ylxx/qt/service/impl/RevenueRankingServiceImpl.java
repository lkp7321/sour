package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.RevenueRankingMapper;
import com.ylxx.qt.service.RevenueRankingService;
import com.ylxx.qt.service.po.TradingAccountFiledBean;

/**
 * 收益排名
 * 
 * @author suimanman
 * 
 */
@Service("revenueRankingService")
public class RevenueRankingServiceImpl implements RevenueRankingService {
	@Resource
	private RevenueRankingMapper rrm;

	@Override
	public List<TradingAccountFiledBean> getAllRevenue()
			throws Exception {
		// 获取收益排名信息
		List<TradingAccountFiledBean> list = null;
		try {
			list = rrm.getAllRevenue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}

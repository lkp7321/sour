package com.ylxx.qt.service;

import java.util.List;

import com.ylxx.qt.service.po.TradingAccountFiledBean;


public interface ITradingAccountFieldService {

	/**
	 * 累积盈亏
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> listSum(List<String> item);
	
	/**
	 * 每天仓位
	 * @param accountid
	 * @return
	 */
	public List<TradingAccountFiledBean> listSpace(List<String> item);
}

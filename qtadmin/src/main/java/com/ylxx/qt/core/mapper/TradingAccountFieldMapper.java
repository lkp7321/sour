package com.ylxx.qt.core.mapper;

import java.util.List;

import com.ylxx.qt.service.po.TradingAccountFiledBean;

public interface TradingAccountFieldMapper {
	
	//累积盈亏
	public List<TradingAccountFiledBean> listSum(List<String> AList);
	
	//每天仓位
	public List<TradingAccountFiledBean> listSpace(List<String> AList);
	
}

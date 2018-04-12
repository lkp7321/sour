package com.ylxx.qt.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.TradingCalendarBean;

public interface TradingCalendarService {
	public void insertTradCalendar(List<TradingCalendarBean> list)throws Exception;
	
	public void deleteTradCalendar(String year) throws Exception;

	public List<TradingCalendarBean> selectTradCalendar(String year) throws Exception;
}

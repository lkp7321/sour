package com.ylxx.qt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ylxx.qt.core.mapper.TradingCalendarMapper;
import com.ylxx.qt.service.TradingCalendarService;
import com.ylxx.qt.service.po.TradingCalendarBean;

@Service("tradingcalendarservice")
public class TradingCalendarServiceImpl implements TradingCalendarService{

	@Resource
	private TradingCalendarMapper tcm;
	@Override
	public void insertTradCalendar(List<TradingCalendarBean> list)
			throws Exception {

		tcm.insertTradCalendar(list);
	}
	@Override
	public void deleteTradCalendar(String year) throws Exception {
		tcm.deleteTradCalendar(year);
		
	}
	@Override
	public List<TradingCalendarBean> selectTradCalendar(String year)
			throws Exception {
		return tcm.selectTradCalendar(year);
	}

}

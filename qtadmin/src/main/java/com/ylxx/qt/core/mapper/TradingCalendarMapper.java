package com.ylxx.qt.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.TradingCalendarBean;

public interface TradingCalendarMapper {

	public void insertTradCalendar(@Param("list") List<TradingCalendarBean> list) throws Exception;

	public void deleteTradCalendar(@Param("year") String year) throws Exception;

	public List<TradingCalendarBean> selectTradCalendar(@Param("year") String year) throws Exception;

}

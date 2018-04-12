package com.ylxx.qt.core.mapper;

import org.apache.ibatis.annotations.Param;

import com.ylxx.qt.service.po.PositionBean;
import com.ylxx.qt.service.po.TradeBean;
import com.ylxx.qt.service.po.TradingaccountBean;

public interface SupervisionCenterDataMapper {
	public Integer selectTradeFild(@Param("tb") TradeBean tb) throws Exception;
	public Integer selectPositionFild(@Param("pb") PositionBean pb) throws Exception;
	public Integer selectTradAccount(@Param("tab") TradingaccountBean tab) throws Exception;
	
	public void updateTradeFild(@Param("tb") TradeBean tb) throws Exception;
	public void updatePositionFild(@Param("pb") PositionBean pb) throws Exception;
	public void updateTradAccount(@Param("tab") TradingaccountBean tab) throws Exception;
	
	public void insertTradeFild(@Param("tb") TradeBean tb) throws Exception;
	public void insertPositionFild(@Param("pb") PositionBean pb) throws Exception;
	public void insertTradAccount(@Param("tab") TradingaccountBean tab) throws Exception;
}

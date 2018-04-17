package com.ylxx.fx.core.mapper.kondor.kondorrv;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_CashTradeBean;

public interface KonCashTradeMapper{
	
	//查询页面数据
	public List<Kon_CashTradeBean> selCashTrade(@Param("startDate")String startDate, 
			@Param("endDate")String endDate,@Param("rpcNo")String rpcNo) throws Exception;
	
}
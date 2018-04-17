package com.ylxx.fx.core.mapper.kondor.kondorrv;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_SpotTradeBean;

public interface KonSpotTradeMapper{
	
	//查询页面数据
	public List<Kon_SpotTradeBean> selSpotTrade(@Param("startDate")String startDate,
			@Param("endDate")String endDate,@Param("tradeCode")String tradeCode,
			@Param("rpcNo")String rpcNo) throws Exception;
	
}
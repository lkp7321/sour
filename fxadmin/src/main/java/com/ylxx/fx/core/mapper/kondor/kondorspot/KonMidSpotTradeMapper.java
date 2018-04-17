package com.ylxx.fx.core.mapper.kondor.kondorspot;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_MidSpotTradeBean;

public interface KonMidSpotTradeMapper{
	
	//查询页面数据
	public List<Kon_MidSpotTradeBean> selMidSpotList(@Param("startDate")String startDate,
			@Param("endDate")String endDate,@Param("rpcNo")String rpcNo) throws Exception;
	
}
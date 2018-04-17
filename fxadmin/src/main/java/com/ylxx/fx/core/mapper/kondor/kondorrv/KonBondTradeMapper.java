package com.ylxx.fx.core.mapper.kondor.kondorrv;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_BondTradeBean;

public interface KonBondTradeMapper{
	
	/**
	 * 查询页面数据
	 * @param startDate
	 * @param endDate
	 * @param rpcNo
	 * @return
	 * @throws Exception
	 */
	List<Kon_BondTradeBean> selBondTrade(@Param("startDate")String startDate, 
			@Param("endDate")String endDate,@Param("rpcNo")String rpcNo) throws Exception;
	
}
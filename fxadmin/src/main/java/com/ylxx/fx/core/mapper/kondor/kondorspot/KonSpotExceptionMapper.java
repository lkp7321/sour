package com.ylxx.fx.core.mapper.kondor.kondorspot;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Kon_SpotExceptionBean;

public interface KonSpotExceptionMapper{
	
	//查询
	public List<Kon_SpotExceptionBean> selExceptionList(@Param("startDate")String startDate, 
			@Param("endDate")String endDate) throws Exception;
	
}
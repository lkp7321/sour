package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ck_PpDetail;

public interface SelppDetailMapper{
	
	//查询货币对列表
	public List<String> selUSDExnm(@Param("prod")String prod) throws Exception;
	//(条件)查询页面数据列表
	public List<Ck_PpDetail> selCondition(@Param("prod")String prod,@Param("sartDate")String sartDate,
			@Param("endDate")String endDate,@Param("strExnm")String strExnm,
			@Param("strStat")String strStat) throws Exception;
	
}
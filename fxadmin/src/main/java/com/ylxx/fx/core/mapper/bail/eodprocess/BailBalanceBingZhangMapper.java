package com.ylxx.fx.core.mapper.bail.eodprocess;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailBalanceBean;

public interface BailBalanceBingZhangMapper{
	
	//选择保证金余额并账/保证金利息并账的所有数据
	public List<BailBalanceBean> selectBalanceBingZhangList(@Param("hddate")String hddate,
			@Param("tradeid")String tradeid) throws Exception;
	//检查并账日期前是否有未并账的记录
	public int checkInput(@Param("fhdate")String fhdate,@Param("tradeid")String tradeid) throws Exception;
	
}
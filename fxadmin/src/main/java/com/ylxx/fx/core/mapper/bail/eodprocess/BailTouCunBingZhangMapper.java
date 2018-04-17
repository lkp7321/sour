package com.ylxx.fx.core.mapper.bail.eodprocess;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailTouCunBean;

public interface BailTouCunBingZhangMapper{
	
	//选择保证金头寸并账的所有数据 
	public List<BailTouCunBean> selectTouCunBingZhangList(@Param("hddate")String hddate) throws Exception;
	//检查并账日期前是否有未并账的记录
	public int checkInput(@Param("fhdate")String fhdate,@Param("tradeid")String tradeid) throws Exception;
	
}
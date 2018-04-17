package com.ylxx.fx.core.mapper.bail.eodprocess;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.BailTouCunBean;

public interface BailTouCunInputMapper{
	
	//选择保证金头寸录入的所有数据 
	public List<BailTouCunBean> selectTouCunList(@Param("hddate")String hddate) throws Exception;
	
}
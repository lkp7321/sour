package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Totalccyam;

public interface QingSuanMapper{
	
	//交易日期查询
	public List<Totalccyam> selQQGCHZ(@Param("trdt")String trdt) throws Exception;
	//交割日期查询
	public List<Totalccyam> selqs(@Param("trdt")String trdt) throws Exception;
}
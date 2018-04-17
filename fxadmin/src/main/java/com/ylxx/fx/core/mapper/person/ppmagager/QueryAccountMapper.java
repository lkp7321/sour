package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Ck_ppresultdetail;

public interface QueryAccountMapper{
	
	//获取页面数据列表
	public List<Ck_ppresultdetail> selectList(@Param("jgdt") String jgdt,@Param("trdt")String trdt) throws Exception;
	
}
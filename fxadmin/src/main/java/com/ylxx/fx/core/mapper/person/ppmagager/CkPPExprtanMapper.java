package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CkPPExprtan;

public interface CkPPExprtanMapper{
	
	//获得全部数据
	public List<CkPPExprtan> selectExprtan(@Param("trac")String trac) throws Exception;
	//处理
	public boolean updateExp(@Param("ppno")String ppno) throws Exception;
	
}
package com.ylxx.fx.core.mapper.jsh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.jsh.Trd_Preodrlist;

public interface QueryPreodrlistMapper{
	
	public List<String> getColumns() throws Exception;
	
	public List<Trd_Preodrlist> getPreodrlist(@Param("cols")String cols,@Param("ptid")String ptid,@Param("qutp")int qutp,
			@Param("cuno")String cuno,@Param("stdt")String stdt,@Param("eddt")String eddt,
			@Param("rqno")String rqno) throws Exception;
	
}
package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Favrule;
import com.ylxx.fx.service.po.Tranlist;
import com.ylxx.fx.service.po.Trd_Favrule;

public interface PereFavruleMapper {
	public List<Favrule> selPereFenfavrule(@Param("ogcd")String ogcd);
	/**
	 * P002,P004 启用，停用
	 * @param prcd
	 * @param ppch
	 * @return
	 */
	public int Begin(@Param("ppch")Trd_Favrule ppch);
	public int End(@Param("ppch")Trd_Favrule ppch);
	public List<Map<String, Object>> selectExnm();
	public String SelClient(@Param("id")String id);
	public List<Map<String, Object>> SelClient1();
	public int onsave(@Param("ogcd")String ogcd, @Param("rule")String rule, @Param("fvid")String fvid);
	public List<Tranlist> selectTranlist(@Param("start")String start, @Param("end")String end, @Param("ogcd")String ogcd);
	
	
}

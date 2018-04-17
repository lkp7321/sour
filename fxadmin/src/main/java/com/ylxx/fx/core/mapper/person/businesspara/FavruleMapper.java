package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.FavourRule;
import com.ylxx.fx.service.po.Favrule;

public interface FavruleMapper {
	
	public List<Favrule> SelectFavrule(@Param("prod")String prod,
			@Param("ogcd")String ogcd);
	
	public int Begin(@Param("prod")String prod,
			@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	public int End(@Param("prod")String prod,
			@Param("ogcd")String ogcd,@Param("fvid")String fvid);
	
	public List<FavourRule> selectPrice(@Param("prod")String prod);
	
	public String SelChann(@Param("qdno")String qdno, @Param("prod")String prod);
	public String SelClient(@Param("cuty")String cuty, @Param("prod")String prod);
	
	public int onsave(@Param("prod")String prod, @Param("ogcd")String ogcd, 
			@Param("rule")String rule, @Param("fvid")String fvid);
	
	public int inssRule(@Param("ogcd")String ogcd,@Param("xl")String xl);
	//查询客户优惠
	public List<Map<String,String>> select(@Param("prod")String prod);
	//查询渠道优惠
	public List<Map<String,String>> SelctChannel(@Param("prod")String prod);
}

package com.ylxx.fx.core.mapper.person.ppmagager;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.CmmCymsgBean;

public interface GoldHandRegisterMapper{
	
	//查询结售汇交易币种
	public List<CmmCymsgBean> selJSHTabUs(@Param("prod")String prod) throws Exception;
	//查询境外平盘币种
	public List<CmmCymsgBean> selJWTabUs(@Param("prod")String prod) throws Exception;
	//查询金交所币种
	public List<CmmCymsgBean> selJJSTabUs(@Param("prod")String prod) throws Exception;
	//根据产品筛选标准币别对
	public List<String> selCurrencyPair(@Param("prod")String prod) throws Exception;
	//查询货币对
	public String selExnm(@Param("exnm1")String exnm1,@Param("exnm2")String exnm2,
			@Param("prod")String prod) throws Exception;
	
}
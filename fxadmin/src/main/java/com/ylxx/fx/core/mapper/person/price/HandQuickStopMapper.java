package com.ylxx.fx.core.mapper.person.price;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.CmmStoper;

public interface HandQuickStopMapper{
	
	//查询价格源市场
	public List<HashMap<String, String>> selectPdtinfo() throws Exception;
	//加载币别对
	public List<HashMap<String, String>> selectEXNM() throws Exception;
	//获得停牌器的所有数据
	public List<CmmStoper> selecthqsCmmStopers() throws Exception;
	//条件获得对应的数据
	public List<CmmStoper> selectAllCmmStoper(@Param("mkid")String mkid, @Param("excd")String excd) throws Exception;
	//批量修改未停牌
	public Boolean updatehqsYesCtrlusfg(@Param("cmms")CmmStoper cmms) throws Exception;	
	
}
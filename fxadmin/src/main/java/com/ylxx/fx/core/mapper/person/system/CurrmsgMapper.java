package com.ylxx.fx.core.mapper.person.system;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.core.domain.price.CurrmsgForAcc;
import com.ylxx.fx.service.po.Currmsg;
import com.ylxx.fx.service.po.Cytp;

public interface CurrmsgMapper {
	public List<Currmsg> selectPrice(@Param("prod")String prod);
	public List<Currmsg> selectPriceP009();
	public List<CurrmsgForAcc> selectPriceP7();
	public List<Map<String,Object>> selectPriceP7init();
	
	public int updatePriceP7(@Param("mult")String mult,@Param("divd")String divd,@Param("exnm")String exnm);
	public List<Cytp> selExnmList(@Param("prod")String prod);
	public int selCurrPrice(@Param("prod")String prod,@Param("exnm")String exnm);
	public int insertCurrPrice(@Param("prod")String prod, @Param("currmsg")Currmsg currmsg);
	public int updateCurrPrice(@Param("prod")String prod, 
			@Param("currmsg")Currmsg currmsg);
	public int deleteCurrPrice(@Param("prod")String prod, @Param("exnm")String exnm);
}

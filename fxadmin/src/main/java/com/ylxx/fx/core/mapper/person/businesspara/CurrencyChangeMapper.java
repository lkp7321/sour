package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.TRD_EXCHTRANLIST;

public interface CurrencyChangeMapper {
	public List<TRD_EXCHTRANLIST> selectTranlist(
			@Param("curLcno")String curLcno, @Param("strcuno")String strcuno, 
			@Param("strsoac")String strsoac, @Param("vurData")String vurData, 
			@Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd);
}

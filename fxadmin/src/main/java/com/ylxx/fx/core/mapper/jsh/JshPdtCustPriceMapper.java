package com.ylxx.fx.core.mapper.jsh;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.jsh.JshPdtCustPriceVo;

public interface JshPdtCustPriceMapper {
	List<HashMap<String, String>> selJshPdtCustPrice(@Param("exnm")String exnm, @Param("trfg")String trfg);

	void insJshPdtCustPrice(@Param("pdtCustPrice")JshPdtCustPriceVo pdtCustPrice);

	void updJshPdtCustPrice(@Param("pdtCustPrice")JshPdtCustPriceVo pdtCustPrice);

	int selCustExnmExist(@Param("pdtCustPrice")JshPdtCustPriceVo pdtCustPrice);

	List<HashMap<String, String>> selJshCustExnm();

	void delJshPdtCustPrice(@Param("pdtCustPrice")JshPdtCustPriceVo pdtCustPrice);

}

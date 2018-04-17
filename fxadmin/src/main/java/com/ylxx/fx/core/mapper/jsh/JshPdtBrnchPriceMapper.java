package com.ylxx.fx.core.mapper.jsh;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.jsh.JshPdtBrnchPriceVo;

public interface JshPdtBrnchPriceMapper {
	List<HashMap<String, String>> selJshPdtBrnchPrice(@Param("exnm")String exnm, @Param("trfg")String trfg);

	void insJshPdtBrnchPrice(@Param("pdtBrnchPrice")JshPdtBrnchPriceVo pdtBrnchPrice);

	void updJshPdtBrnchPrice(@Param("pdtBrnchPrice")JshPdtBrnchPriceVo pdtBrnchPrice);

	int selBrnchExnmExist(@Param("pdtBrnchPrice")JshPdtBrnchPriceVo pdtBrnchPrice);

	List<HashMap<String, String>> selJshBrnchExnm();

	void delJshPdtBrnchPrice(@Param("pdtBrnchPrice")JshPdtBrnchPriceVo pdtBrnchPrice);

}

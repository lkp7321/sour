package com.ylxx.fx.service.jsh;

import com.ylxx.fx.service.po.jsh.JshPdtBrnchPriceVo;

public interface IJshPdtBrnchPriceService {

	String selJshPdtBrnchPrice(String exnm, String trfg, Integer pageNo, Integer pageSize);
	String insJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice);
	String updJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice);
	String delJshPdtBrnchPrice(JshPdtBrnchPriceVo pdtBrnchPrice);
	String selBrnchExnmExist(JshPdtBrnchPriceVo pdtBrnchPrice);
	String selJshBrnchExnm();
}

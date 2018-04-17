package com.ylxx.fx.service.jsh;

import com.ylxx.fx.service.po.jsh.JshPdtCustPriceVo;

public interface IJshPdtCustPriceService {

	String selJshPdtCustPrice(String exnm, String trfg, Integer pageNo, Integer pageSize);
	String insJshPdtCustPrice(JshPdtCustPriceVo pdtCustPrice);
	String updJshPdtCustPrice(JshPdtCustPriceVo pdtCustPrice);
	String delJshPdtCustPrice(JshPdtCustPriceVo pdtCustPrice);
	String selCustExnmExist(JshPdtCustPriceVo pdtCustPrice);
	String selJshCustExnm();
}

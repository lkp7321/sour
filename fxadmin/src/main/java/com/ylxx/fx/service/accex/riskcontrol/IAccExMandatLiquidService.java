package com.ylxx.fx.service.accex.riskcontrol;

import java.util.ArrayList;

import com.ylxx.fx.core.domain.AdditionalMarginVo;

public interface IAccExMandatLiquidService {

	String mandatLiquid(String userKey, String prod, Integer pageSize, Integer pageNo);

	String mandatLiquidService(String userKey, ArrayList<AdditionalMarginVo> admlist);

	String upAutoForceStat(String prod, String stat);

	String getAutoForceSta(String userKey, String prod);


}

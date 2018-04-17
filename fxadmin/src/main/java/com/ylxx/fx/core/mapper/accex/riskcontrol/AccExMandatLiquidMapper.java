package com.ylxx.fx.core.mapper.accex.riskcontrol;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.AccExMandatLiquid;


public interface AccExMandatLiquidMapper {

	List<AccExMandatLiquid> mandatLiquid(@Param("prod")String prod);

	String generateLCNO(@Param("ptid")String ptid);

	boolean upAutoForceStat(@Param("prod")String prod,@Param("stat")String stat);

	HashMap<String, String> getAutoForceStat(@Param("prod")String prod);


}

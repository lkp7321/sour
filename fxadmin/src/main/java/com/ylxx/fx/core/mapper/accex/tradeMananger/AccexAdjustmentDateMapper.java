package com.ylxx.fx.core.mapper.accex.tradeMananger;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AccexAdjustmentDateMapper{
	//页面加载的交易品种
	public List<Map<String, Object>> selCurrencyPair(@Param("prod")String prod);
	//查询货币对和时间是否存在
	public int selectAdjustByKey(@Param("exnm")String exnm,@Param("femd")String femd);
	//保存
	public int addAdjust(@Param("ostp")String ostp,@Param("osbs")String osbs,@Param("nstp")String nstp,@Param("nsbs")String nsbs,@Param("orbs")String orbs,@Param("nrbs")String nrbs,@Param("osas")String osas,@Param("nsas")String nsas,@Param("oras")String oras,@Param("nras")String nras,@Param("exnm")String exnm,@Param("femd")String femd,@Param("stat")String stat);
    //查询调整日配置信息 
	public List<Map<String, Object>> selOilAdjustList(@Param("beginDate")String beginDate,@Param("endDate")String endDate,@Param("exnm")String exnm);
	//修改
	public int updateAdjust(
			@Param("ostp")String ostp,@Param("osbs")String osbs,
			@Param("nstp")String nstp,@Param("nsbs")String nsbs,
			@Param("orbs")String orbs,@Param("nrbs")String nrbs,
			@Param("osas")String osas,@Param("nsas")String nsas,
			@Param("oras")String oras,@Param("nras")String nras,
			@Param("exnm")String exnm,@Param("femd")String femd);
	//删除
	public int deleteAdjustByKey(@Param("exnm")String exnm,@Param("femd")String femd);
}
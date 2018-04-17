package com.ylxx.fx.core.mapper.accex.tradeMananger;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface AccexPromotionListMapper{
	
	public List<Map<String, Object>> selectPromotion(@Param("strateDate")String strateDate, @Param("endDate")String endDate,@Param("prod")String prod);
	
}
package com.ylxx.fx.core.mapper.accex.tradeMananger;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
public interface AccexOrderInfoFromVieMapper{
	//获取持仓表视图数据   
	public List<Map<String, Object>> getOrderListFromVie(
			@Param("strateDate")String strateDate, 
			@Param("endDate")String endDate,
			@Param("prod")String prod);
	
}
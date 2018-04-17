package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
/**
 * 定期申购历史价格
 * @author lz130
 *
 */
public interface AccumuQHisPriceTradeMapper{
	/**
	 * 定期申购历史价格查询 返回发布日期、时间、交易币种、定期申购价格,价格序列号
	 * @param trdtbegin
	 * @param trdtend
	 * @return
	 */
	List<Map<String, Object>> selectAccumuHisPrice(@Param("trdtbegin")String trdtbegin,@Param("trdtend")String trdtend);
	
	
}
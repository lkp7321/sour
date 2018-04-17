package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;
import java.util.Map;
/**
 * 定期申购历史价格查询
 * @author lz130
 *
 */
public interface IAccumuQHisPriceTradeService {
	/**
	 * 定期申购历史价格查询
	 * @param trdtbegin
	 * @param trdtend
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	String selectAccumuHisPrice(String trdtbegin,String trdtend,Integer pageNo, Integer pageSize) throws Exception;
	/**
	 * 查所有
	 * @param trdtbegin
	 * @param trdtend
	 * @return
	 */
	List<Map<String, Object>> selectAllAccumuHisPrice(String trdtbegin, String trdtend);
}


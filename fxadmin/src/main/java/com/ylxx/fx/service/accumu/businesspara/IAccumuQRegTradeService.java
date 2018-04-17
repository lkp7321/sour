package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;
import java.util.Map;
/**
 * 签约流水查询
 * @author lz130
 *
 */
public interface  IAccumuQRegTradeService {
	/**
	 * 条件获得对应的数据
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param strcuno
	 * @param comaogcd
	 * @param combogcd
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selectAccumuTranlist(
		String comdata1, String comdata3, 
		String strcuac, String trdtbegin,
		String trdtend, String strcuno, 
		String comaogcd, String combogcd, 
		Integer pageNo, Integer pageSize);
	List<Map<String, Object>> selectAllAccumuTranlist(
		String comdata1, String comdata3, 
		String strcuac, String trdtbegin,
		String trdtend, String strcuno, 
		String comaogcd, String combogcd);
}

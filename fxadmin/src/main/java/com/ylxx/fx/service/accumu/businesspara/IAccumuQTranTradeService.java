package com.ylxx.fx.service.accumu.businesspara;

import java.util.List;

import com.ylxx.fx.service.po.Tranlist;

/**
 * 定期申购签约解约
 * @author lz130
 *
 */
public interface IAccumuQTranTradeService {
	/**
	 * 分页查询
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	String selectAccumuTranTrade(
			String comdata1,String comdata3, 
			String strcuac, String trdtbegin, 
			String trdtend,Integer pageNo,Integer pageSize);
	/**
	 * 导出查询所有
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @return
	 */
	List<Tranlist> selAccumuTranTrade(
			String comdata1, String comdata3,
			String strcuac, String trdtbegin,
			String trdtend);
}

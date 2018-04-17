package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Tranlist;
/**
 * 定期申购签约解约
 * @author lz130
 *
 */
public interface AccumuQTranTradeMapper {
	/**
	 * 定期申购签约解约查询
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @return
	 */
	List<Tranlist> selectAccumuTranTrade(@Param("comdata1")String comdata1,@Param("comdata3")String comdata3, @Param("strcuac")String strcuac, @Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend);
}

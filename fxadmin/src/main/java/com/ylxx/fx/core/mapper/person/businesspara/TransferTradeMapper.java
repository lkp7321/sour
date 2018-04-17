package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.TRD_EXCHTRANLIST;
import com.ylxx.fx.service.po.Tranlist;

public interface TransferTradeMapper {
	/*
	 * p001的出入金数据查询
	 */
	public List<Tranlist> selectTranlist(
			@Param("lcno")String lcno, @Param("strcuac")String strcuac,
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd
			);
	/*
	 * P002的出入金数据查询
	 */
	public List<Tranlist> selectTranlist2(
			@Param("lcno")String lcno, @Param("strcuac")String strcuac,
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd
			);
	/**
	 * P003的出入金数据查询
	 * @param lcno
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	public List<Tranlist> selectTranlist3(
			@Param("lcno")String lcno, @Param("strcuac")String strcuac,
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd
			);
}

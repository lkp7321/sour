package com.ylxx.fx.core.mapper.accumu.businesspara;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
/**
 * 签约流水查询
 * @author lz130
 *
 */
public interface  AccumuQRegTradeMapper {
	/**
	 * 条件获得对应数据
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param strcuno
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> selectAccumuRegTrade(@Param("comdata1")String comdata1,@Param("comdata3")String comdata3,@Param("strcuac")String strcuac,@Param("trdtbegin")String trdtbegin,@Param("trdtend")String trdtend,@Param("strcuno")String strcuno,@Param("comaogcd")String comaogcd,@Param("combogcd")String combogcd) throws Exception;
	/**
	 * 条件获得数量
	 * @param comdata1
	 * @param comdata3
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param strcuno
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getCount(@Param("comdata1")Integer comdata1,@Param("comdata3")String comdata3,@Param("strcuac")String strcuac,@Param("trdtbegin")String trdtbegin,@Param("trdtend")String trdtend,@Param("strcuno")String strcuno,@Param("comaogcd")String comaogcd,@Param("combogcd")String combogcd) throws Exception;
}

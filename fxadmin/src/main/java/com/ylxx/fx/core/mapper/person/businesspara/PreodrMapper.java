package com.ylxx.fx.core.mapper.person.businesspara;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ylxx.fx.service.po.Preodrlist;
import com.ylxx.fx.service.po.Trd_ogcd;

public interface PreodrMapper {
	/**
	 * 查询挂单流水
	 * @param prod
	 * @param strcuac
	 * @param trdtbegin
	 * @param trdtend
	 * @param comaogcd
	 * @param combogcd
	 * @return
	 */
	List<Preodrlist> selectPreodr(@Param("prod")String prod,@Param("strcuac")String strcuac,
			@Param("trdtbegin")String trdtbegin, @Param("trdtend")String trdtend, 
			@Param("comaogcd")String comaogcd, @Param("combogcd")String combogcd);
	
	/**
	 * 查询第一个下拉框
	 * @return
	 */
	List<Trd_ogcd> queryOneOrgan();
	/**
	 * 查询第二个下拉框
	 * @param comaogcd
	 * @return
	 */
	List<Trd_ogcd> queryTwoOrgan(@Param("comaogcd")String comaogcd);
}
